package com.quew8.gutils.debug;

/**
 *
 * @author Quew8
 */
public class DebugObjectParamNotFoundException extends DebugLeafNotFoundException {

    public DebugObjectParamNotFoundException(DebugInterfaceParser.DebugObjectStruct parent, String leaf) {
        super(parent, leaf);
    }
    
}
