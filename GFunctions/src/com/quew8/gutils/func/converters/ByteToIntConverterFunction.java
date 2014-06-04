package com.quew8.gutils.func.converters;

import com.quew8.gutils.func.AbstractSingleOperandFunction;
import com.quew8.gutils.func.Function;

/**
 *
 * @author Quew8
 */
public class ByteToIntConverterFunction<T> extends AbstractSingleOperandFunction<Integer, Byte, T> {
    
    public ByteToIntConverterFunction(Function<Byte, T> function1) {
        super(function1);
    }
    
    @Override
    public Integer f(T s) {
        return (int) function1.f(s);
    }
    
}
