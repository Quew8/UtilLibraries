package com.quew8.gutils.debug;

import com.quew8.gutils.debug.DebugInterfaceParser.DebugParamStruct;

/**
 *
 * @author Quew8
 */
public class DebugSettingFinalParamException extends DebugException {
    private final DebugParamStruct param;
    
    public DebugSettingFinalParamException(DebugParamStruct param) {
        super("Attempting to set value of final param \"" + param.name + "\"");
        this.param = param;
    }

    public DebugParamStruct getParam() {
        return param;
    }
}
