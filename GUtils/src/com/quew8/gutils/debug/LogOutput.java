package com.quew8.gutils.debug;

public enum LogOutput {
    STREAM(true, false), 
    FILE(false, true), 
    STREAM_AND_FILE(true, true), 
    NONE(false, false);
    
    private LogOutput(boolean stream, boolean file) {
    	this.stream = stream;
    	this.file = file;
    }
    
    private final boolean stream, file;
    
    public boolean stream() {
    	return stream;
    }
    
    public boolean file() {
    	return file;
    }
}