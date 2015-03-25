package com.quew8.gutils.debug;

/**
 *
 * @author Quew8
 */
public class DebugSettingFinalParamException extends DebugException {

    public DebugSettingFinalParamException(String param) {
        super("Attempting to set value of final param \"" + param + "\"");
    }
    
}
