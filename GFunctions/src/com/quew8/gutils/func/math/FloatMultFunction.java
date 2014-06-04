package com.quew8.gutils.func.math;

import com.quew8.gutils.func.AbstractDualOperandFunction;
import com.quew8.gutils.func.Function;

/**
 *
 * @author Quew8
 */
public class FloatMultFunction<S> extends AbstractDualOperandFunction<Float, Float, S> {
    
    public FloatMultFunction(Function<Float, S> function1, Function<Float, S> function2) {
        super(function1, function2);
    }
    
    @Override
    public Float f(S s) {
        return function1.f(s) * function2.f(s);
    }
    
}
