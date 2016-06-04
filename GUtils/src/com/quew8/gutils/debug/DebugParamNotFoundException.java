package com.quew8.gutils.debug;

/**
 *
 * @author Quew8
 */
public class DebugParamNotFoundException extends DebugLeafNotFoundException {

    public DebugParamNotFoundException(DebugInterfaceParser.DebugObjectStruct parent, String leaf) {
        super(parent, leaf);
    }
    
}
