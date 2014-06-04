package com.quew8.gutils.func.converters;

import com.quew8.gutils.func.AbstractSingleOperandFunction;
import com.quew8.gutils.func.Function;

/**
 *
 * @author Quew8
 */
public class FloatToIntConverterFunction<T> extends AbstractSingleOperandFunction<Integer, Float, T> {
    
    public FloatToIntConverterFunction(Function<Float, T> function1) {
        super(function1);
    }
    
    @Override
    public Integer f(T s) {
        return (int) ((float)function1.f(s));
    }
    
}
