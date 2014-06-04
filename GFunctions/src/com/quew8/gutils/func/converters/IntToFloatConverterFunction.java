package com.quew8.gutils.func.converters;

import com.quew8.gutils.func.AbstractSingleOperandFunction;
import com.quew8.gutils.func.Function;

/**
 *
 * @author Quew8
 */
public class IntToFloatConverterFunction<T> extends AbstractSingleOperandFunction<Float, Integer, T> {
    
    public IntToFloatConverterFunction(Function<Integer, T> function1) {
        super(function1);
    }
    
    @Override
    public Float f(T s) {
        return (float) function1.f(s);
    }
    
}
