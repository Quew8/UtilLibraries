package com.quew8.gutils.debug;

/**
 *
 * @author Quew8
 */
public class DebugIntInterpreter extends DebugInterpreter {

    @Override
    public Object interpret(String s) throws DebugException {
        try {
            return Integer.parseInt(s);
        } catch(NumberFormatException ex) {
            throw new DebugException("Couldn't parse \"" + s + "\" as float");
        }
    }

    @Override
    public String toString(Object obj) throws DebugException {
        return Integer.toString((Integer)obj);
    }
    
}
