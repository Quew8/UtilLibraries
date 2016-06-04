package com.quew8.gutils.debug;

import com.quew8.gutils.debug.DebugInterfaceParser.DebugParamStruct;

/**
 *
 * @author Quew8
 */
public class DebugAmbiguousAnnotationException extends DebugException {
    
    public DebugAmbiguousAnnotationException(DebugParamStruct param) {
        super("Supplementary annotations on \"" + param.name + "\" in \"" + param.parent.name + "\" are ambiguously targeted");
    }
}
