package com.quew8.geng.debug;

import com.quew8.gmath.Vector;
import com.quew8.gutils.debug.DebugFloatInterpreter;
import com.quew8.gutils.debug.DebugFloatSliderField;
import com.quew8.gutils.debug.DebugObject;
import com.quew8.gutils.debug.DebugObjectWrapper;
import com.quew8.gutils.debug.DebugParam;

/**
 *
 * @author Quew8
 */
@DebugObject(name = "vector")
public class DebugVectorWrapper implements DebugObjectWrapper {
    @DebugFloatSliderField(target = "x", min = 0, max = 10, step = 1)
    @DebugFloatSliderField(target = "y", min = 0, max = 20, step = 2)
    @DebugFloatSliderField(target = "z", min = 0, max = 30, step = 3)
    @DebugParam(name = "x", interpreter = DebugFloatInterpreter.class, pointer = "x")
    @DebugParam(name = "y", interpreter = DebugFloatInterpreter.class, pointer = "y")
    @DebugParam(name = "z", interpreter = DebugFloatInterpreter.class, pointer = "z")
    private Vector vector;

    @Override
    public void wrap(Object obj) {
        this.vector = (Vector) obj;
    }
}
