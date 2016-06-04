package com.quew8.gutils.debug;

/**
 *
 * @author Quew8
 */
public class DebugFloatInterpreter extends DebugInterpreter {

    @Override
    public Object interpret(String s) throws DebugException {
        try {
            return Float.parseFloat(s);
        } catch(NumberFormatException ex) {
            throw new DebugException("Couldn't parse \"" + s + "\" as float");
        }
    }

    @Override
    public String toString(Object obj) throws DebugException {
        return Float.toString((Float)obj);
    }
    
}
