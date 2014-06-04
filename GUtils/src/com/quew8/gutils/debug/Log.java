package com.quew8.gutils.debug;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.quew8.gutils.Clock;
import com.quew8.gutils.PlatformBackend;
import java.util.ArrayList;

public class Log {
    private final String name;
    private final ArrayList<String> queuedLines = new ArrayList<String>();
    private final ArrayList<LogLevel> queuedLvls = new ArrayList<LogLevel>();
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
        if(PlatformBackend.isInitialized()) {
            if(!queuedLines.isEmpty()) {
                flush();
            }
            handleLine(level, l);
        } else {
            queuedLines.add(l);
            queuedLvls.add(level);
        }
    }
    
    public void println(String s) {
    	println(defaultLevel, s);
    }
    
    public void flush() {
        for(int i = 0; i < queuedLines.size(); i++) {
            handleLine(queuedLvls.get(i), queuedLines.get(i));
        }
        queuedLines.clear();
        queuedLvls.clear();
    }
    
    private void handleLine(LogLevel level, String line) {
        if(DebugLogger.filterStream(defaultLevel) && output.stream()) {
            LogLevel.getStream(level).log(name, line);
        }
        if(DebugLogger.filterFile(defaultLevel) && output.file()) {
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
    	appendToFile(PlatformBackend.backend.getLogFile_P(name + "_LOG.txt"), s);
    }
    
    private static String getTimeString() {
        return PlatformBackend.isInitialized() ? Long.toString(Clock.getTime()) : "PRE_INIT";
    }
}