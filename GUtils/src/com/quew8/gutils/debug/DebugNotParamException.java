package com.quew8.gutils.debug;

import java.lang.reflect.Field;

/**
 *
 * @author Quew8
 */
public class DebugNotParamException extends DebugException {
    
    public DebugNotParamException(Field f) {
        super("\"" + f.getName() + "\" is not a DebugParam");
    }
}
