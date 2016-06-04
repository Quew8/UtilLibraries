package com.quew8.geng.debug;

import com.quew8.gmath.GMath;
import com.quew8.gutils.debug.DebugException;
import com.quew8.gutils.debug.DebugInterpreter;

/**
 *
 * @author Quew8
 */
public class DebugDegreesInterpreter extends DebugInterpreter {

    @Override
    public Object interpret(String s) throws DebugException {
        try {
            return GMath.toRadians(Float.parseFloat(s));
        } catch(NumberFormatException ex) {
            throw new DebugException("Couldn't parse \"" + s + "\" as float");
        }
    }

    @Override
    public String toString(Object obj) throws DebugException {
        return Float.toString(GMath.toDegrees((Float)obj));
    }
}
