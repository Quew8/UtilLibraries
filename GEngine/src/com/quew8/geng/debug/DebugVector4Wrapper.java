package com.quew8.geng.debug;

import com.quew8.gmath.Vector4;
import com.quew8.gutils.debug.DebugFloatInterpreter;
import com.quew8.gutils.debug.DebugObject;
import com.quew8.gutils.debug.DebugObjectWrapper;
import com.quew8.gutils.debug.DebugParam;

/**
 *
 * @author Quew8
 */
@DebugObject(name = "vector")
public class DebugVector4Wrapper implements DebugObjectWrapper {
    @DebugParam(name = "x", interpreter = DebugFloatInterpreter.class, pointer = "x")
    @DebugParam(name = "y", interpreter = DebugFloatInterpreter.class, pointer = "y")
    @DebugParam(name = "z", interpreter = DebugFloatInterpreter.class, pointer = "z")
    @DebugParam(name = "w", interpreter = DebugFloatInterpreter.class, pointer = "w")
    private Vector4 vector;

    @Override
    public void wrap(Object obj) {
        this.vector = (Vector4) obj;
    }
}
