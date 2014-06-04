package com.quew8.gutils.debug;

import java.util.HashMap;

/**
 * 
 * @author Quew8
 */
public class DebugLogger {
    private static final HashMap<String, Log> logs = new HashMap<String, Log>();
    private static LogLevel streamFilter = LogLevel.VERBOSE;
    private static LogLevel fileFilter = LogLevel.VERBOSE;
    static {
        registerLog("VERBOSE", LogLevel.INFO);
        registerLog("INFO", LogLevel.INFO);
        registerLog("DEBUG", LogLevel.DEBUG);
        registerLog("WARN", LogLevel.WARN);
        registerLog("ERROR", LogLevel.ERROR);
    }
    
    private DebugLogger() {
        
    }
    
    public static void v(String log, String s) {
        log(LogLevel.VERBOSE, log, s);
    }
    
    public static void v(String log, Object o) {
        v(log, toObjString(o));
    }
    
    public static void v(String s) {
        log("VERBOSE", s);
    }
    
    public static void v(Object o) {
        v(toObjString(o));
    }
    
    public static void i(String log, String s) {
        log(LogLevel.INFO, log, s);
    }
    
    public static void i(String log, Object o) {
        i(log, toObjString(o));
    }
    
    public static void i(String s) {
        log("INFO", s);
    }
    
    public static void i(Object o) {
        i(toObjString(o));
    }
    
    public static void d(String log, String s) {
        log(LogLevel.DEBUG, log, s);
    }
    
    public static void d(String log, Object o) {
        d(log, toObjString(o));
    }
    
    public static void d(String s) {
        log("DEBUG", s);
    }
    
    public static void d(Object o) {
        d(toObjString(o));
    }
    
    public static void w(String log, String s) {
        log(LogLevel.WARN, log, s);
    }
    
    public static void w(String log, Object o) {
        w(log, toObjString(o));
    }
    
    public static void w(String s) {
        log("WARN", s);
    }
    
    public static void w(Object o) {
        w(toObjString(o));
    }
    
    public static void e(String log, String s) {
        log(LogLevel.ERROR, log, s);
    }
    
    public static void e(String log, Object o) {
        e(log, toObjString(o));
    }
    
    public static void e(String s) {
        log("ERROR", s);
    }
    
    public static void e(Object o) {
        e(toObjString(o));
    }
    
    public static void log(String log, Exception ex) {
        getLog(LogLevel.ERROR, log).println(LogLevel.ERROR, ex.getMessage() + "\n");
    }
    
    public static void log(Exception ex) {
        getLog(null, "ERROR").println(ex.getMessage() + "\n");
        throw new RuntimeException(ex);
    }
    
    public static void log(LogLevel level, String log, String s) {
        getLog(level, log).println(level, s);
    }
    
    public static void log(LogLevel level, String log, Object o) {
        log(level, log, toObjString(o));
    }
    
    public static void log(String log, String s) {
        getLog(null, log).println(s);
    }
    
    public static void log(String log, Object o) {
        log(log, toObjString(o));
    }
    
    public static void registerLog(String log, LogLevel level, LogOutput output) {
        logs.put(log, new Log(log, level, output));
    }
    
    public static void registerLog(String log, LogLevel level) {
        registerLog(log, level, LogOutput.STREAM_AND_FILE);
    }
    
    public static void setStreamFilter(LogLevel filter) {
        streamFilter = filter;
    }
    
    public static void setFileFilter(LogLevel filter) {
        fileFilter = filter;
    }
    
    public static void setLogLevel(String log, LogOutput output) {
        getLog(null, log).setOutput(output);
    }
    
    public static void setLogLevel(LogOutput output) {
        setLogLevel("DEFAULT", output);
    }
    
    private static Log getLog(LogLevel level, String log) {
        if(!logs.containsKey(log)) {
            logs.put(log, level == null ? new Log(log) : new Log(log, level));
        }
        return logs.get(log);
    }
    
    protected static boolean filterStream(LogLevel message) {
    	return LogLevel.filter(streamFilter, message);
    }
    
    protected static boolean filterFile(LogLevel message) {
    	return LogLevel.filter(fileFilter, message);
    }
    
    private static String toObjString(Object obj) {
        return obj == null ? "null" : obj.toString();
    } 
}
