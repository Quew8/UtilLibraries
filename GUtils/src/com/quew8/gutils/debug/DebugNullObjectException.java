package com.quew8.gutils.debug;

import com.quew8.gutils.debug.DebugInterfaceParser.DebugObjectParamStruct;
import com.quew8.gutils.debug.DebugInterfaceParser.DebugParamStruct;

/**
 *
 * @author Quew8
 */
public class DebugNullObjectException extends DebugException {
    
    public DebugNullObjectException(DebugObjectParamStruct param) {
        super("\"" + param.name + "\" is null in " + param.parent.name);
    }
    
    public DebugNullObjectException(DebugParamStruct param) {
        super("\"" + param.name + "\" in \"" + param.parent.name + "\", is null and points to a non-static field, \"" + param.pointedField + "\"");
    }
    
}
