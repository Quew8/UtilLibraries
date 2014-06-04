package com.quew8.gutils.debug;

import com.quew8.gutils.PlatformBackend;

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
			errorStream = PlatformBackend.backend.getLogStream_P(ERROR);
			warnStream = PlatformBackend.backend.getLogStream_P(WARN);
			infoStream = PlatformBackend.backend.getLogStream_P(INFO);
			debugStream = PlatformBackend.backend.getLogStream_P(DEBUG);
			verboseStream = PlatformBackend.backend.getLogStream_P(VERBOSE);
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