package com.quew8.gutils.debug;

import com.quew8.gutils.debug.DebugInterfaceParser.DebugObjectStruct;
import java.lang.reflect.Field;

/**
 *
 * @author Quew8
 */
public class DebugPointedParamNotFoundException extends RuntimeException {

    public DebugPointedParamNotFoundException(DebugObjectStruct parent, Field field, DebugParam param) {
        super("Pointed field, \"" + param.pointer() + "\", not found in \"" + field.getType().getSimpleName() + "\"");
    }
    
}
