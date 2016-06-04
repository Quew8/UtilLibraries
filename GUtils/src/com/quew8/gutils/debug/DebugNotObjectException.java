package com.quew8.gutils.debug;

/**
 *
 * @author Quew8
 */
public class DebugNotObjectException extends DebugException {

    public DebugNotObjectException(Object obj) {
        super("\"" + obj.getClass().getName() + "\" is not a DebugObject");
    }
    
}
