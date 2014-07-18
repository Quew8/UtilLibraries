package com.quew8.gutils.debug;

import com.quew8.gutils.PlatformUtils;

public enum LogLevel {
	ERROR('e'), WARN('w'), INFO('i'), DEBUG('d'), VERBOSE('v');
	
	private LogLevel(char idChar) {
		this.idChar = idChar;
	}
	
	private final char idChar;
	
	private static LogStream 
		errorStream,
		warnStream,
		infoStream,
		debugStream,
		verboseStream;
	
	public char getIdChar() {
		return idChar;
	}
	
	public static boolean filter(LogLevel filter, LogLevel message) {
		return message.ordinal() <= filter.ordinal();
	}
	
	public static LogStream getStream(LogLevel level) {
		if(errorStream == null) {
			errorStream = PlatformUtils.getLogStream(ERROR);
			warnStream = PlatformUtils.getLogStream(WARN);
			infoStream = PlatformUtils.getLogStream(INFO);
			debugStream = PlatformUtils.getLogStream(DEBUG);
			verboseStream = PlatformUtils.getLogStream(VERBOSE);
		}
		switch(level) {
		case ERROR: return errorStream;
		case WARN: return warnStream;
		case INFO: return infoStream;
		case DEBUG: return debugStream;
		case VERBOSE: return verboseStream;
		default: return null;
		}
	}
}