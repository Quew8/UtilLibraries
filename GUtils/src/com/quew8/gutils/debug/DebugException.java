package com.quew8.gutils.debug;

/**
 *
 * @author Quew8
 */
public class DebugException extends Exception {

    public DebugException(String message, Throwable cause) {
        super(message, cause);
    }

    public DebugException(String message) {
        super(message);
    }
    
}
