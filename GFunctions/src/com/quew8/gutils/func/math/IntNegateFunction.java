package com.quew8.gutils.func.math;

import com.quew8.gutils.func.AbstractSingleOperandFunction;
import com.quew8.gutils.func.Function;

/**
 *
 * @author Quew8
 */
public class IntNegateFunction<T> extends AbstractSingleOperandFunction<Integer, Integer, T> {
    
    public IntNegateFunction(Function<Integer, T> function1) {
        super(function1);
    }
    
    @Override
    public Integer f(T s) {
        return -function1.f(s);
    }
    
}
