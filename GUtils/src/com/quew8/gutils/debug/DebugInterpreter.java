package com.quew8.gutils.debug;

/**
 *
 * @author Quew8
 */
public class DebugInterpreter {
    public String toString(Object obj) throws DebugException {
        return (String) obj;
    }
    
    public Object interpret(String s) throws DebugException {
        return s;
    }
}
