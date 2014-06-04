package com.quew8.gutils.func.math;

import com.quew8.gutils.func.AbstractSingleOperandFunction;
import com.quew8.gutils.func.Function;

/**
 *
 * @author Quew8
 */
public class FloatRecFunction<T> extends AbstractSingleOperandFunction<Float, Float, T> {
    
    public FloatRecFunction(Function<Float, T> function1) {
        super(function1);
    }
    
    @Override
    public Float f(T s) {
        return 1 / function1.f(s);
    }
    
}
