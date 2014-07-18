package com.quew8.gutils.debug;

import com.quew8.gutils.Clock;
import com.quew8.gutils.PlatformUtils;
import com.quew8.gutils.collections.ExpandingStack;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Log {
    private File logFile = null;
    private final String name;
    private ExpandingStack<String> queuedLines = new ExpandingStack<String>(String.class, 10);
    private ExpandingStack<LogLevel> queuedLvls = new ExpandingStack<LogLevel>(LogLevel.class, 10);
    private LogOutput output;
    private final LogLevel defaultLevel;
    
    public Log(String name, LogLevel level, LogOutput output) {
        this.name = name;
        this.defaultLevel = level;
        this.output = output;
    }

    public Log(String name, LogLevel level) {
        this(name, level, LogOutput.STREAM_AND_FILE);
    }
    
    public Log(String name) {
        this(name, LogLevel.VERBOSE);
    }
    
    public void println(LogLevel level, String s) {
        String l = level.getIdChar() + " | " + getTimeString() + " | " + s;
        if(PlatformUtils.isInitialized()) {
            handleLine(level, l);
        } else {
            queuedLines.push(l);
            queuedLvls.push(level);
        }
    }
    
    public void println(String s) {
    	println(defaultLevel, s);
    }
    
    public void flush() {
        queuedLines.reverse();
        queuedLvls.reverse();
        while(!queuedLines.isEmpty()) {
            handleLine(queuedLvls.pop(), queuedLines.pop());
        }
        queuedLines = null;
        queuedLvls = null;
    }
    
    public void clearFile() {
        File f = getLogFile();
        if(f.exists()) {
            if(!f.delete()) {
                println("Could Not Clear Previous Log File");
            }
        }
    }
    
    private void handleLine(LogLevel level, String line) {
        if(DebugLogger.filterStream(level) && output.stream()) {
            LogLevel.getStream(level).log(name, line);
        }
        if(DebugLogger.filterFile(level) && output.file()) {
            appendToFile(line + System.lineSeparator());
        }
    }
    
    public void setOutput(LogOutput output) {
        this.output = output;
    }
    
    public void appendToFile(File file, String s) {
        try(FileWriter writer = new FileWriter(file, true)) {
        	writer.write(s);
        } catch(IOException ex) {
        	throw new RuntimeException(ex);
        }
    }
    
    public void appendToFile(String s) {
    	appendToFile(getLogFile(), s);
    }
    
    private File getLogFile() {
        if(logFile == null) {
            logFile = PlatformUtils.getLogFile(name + "_LOG.txt");
            logFile.getParentFile().mkdirs();
        }
        return logFile;
    }
    
    private static String getTimeString() {
        return PlatformUtils.isInitialized() ? Long.toString(Clock.getTime()) : "PRE_INIT";
    }
}