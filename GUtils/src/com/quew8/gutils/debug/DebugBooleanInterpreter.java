package com.quew8.gutils.debug;

/**
 *
 * @author Quew8
 */
public class DebugBooleanInterpreter extends DebugInterpreter {

    @Override
    public Object interpret(String s) throws DebugException {
        switch(s) {
            case "true":
                return Boolean.TRUE;
            case "false":
                return Boolean.FALSE;
            default:
                throw new DebugException("Cannot interpret \"" + s + "\" as boolean");
        }
    }

    @Override
    public String toString(Object obj) throws DebugException {
        return ((Boolean) obj) ? "true" : "false";
    }
    
}
