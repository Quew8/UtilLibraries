package com.quew8.gutils.func.converters;

import com.quew8.gutils.func.AbstractSingleOperandFunction;
import com.quew8.gutils.func.Function;

/**
 *
 * @author Quew8
 */
public class IntToByteConverterFunction<T> extends AbstractSingleOperandFunction<Byte, Integer, T> {
    
    public IntToByteConverterFunction(Function<Integer, T> function1) {
        super(function1);
    }
    
    @Override
    public Byte f(T s) {
        return (byte) (int)function1.f(s);
    }
    
}
