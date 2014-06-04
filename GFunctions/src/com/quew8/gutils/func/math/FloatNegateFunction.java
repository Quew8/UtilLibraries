package com.quew8.gutils.func.math;

import com.quew8.gutils.func.AbstractSingleOperandFunction;
import com.quew8.gutils.func.Function;

/**
 *
 * @author Quew8
 */
public class FloatNegateFunction<T> extends AbstractSingleOperandFunction<Float, Float, T> {
    
    public FloatNegateFunction(Function<Float, T> function1) {
        super(function1);
    }
    
    @Override
    public Float f(T s) {
        return -function1.f(s);
    }
    
}
