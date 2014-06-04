package com.quew8.gutils.func.converters;

import com.quew8.gutils.func.AbstractSingleOperandFunction;
import com.quew8.gutils.func.Function;

/**
 *
 * @author Quew8
 */
public class FloatToByteConverterFunction<T> extends AbstractSingleOperandFunction<Byte, Float, T> {
    
    public FloatToByteConverterFunction(Function<Float, T> function1) {
        super(function1);
    }
    
    @Override
    public Byte f(T s) {
        return (byte) (float)function1.f(s);
    }
    
}
