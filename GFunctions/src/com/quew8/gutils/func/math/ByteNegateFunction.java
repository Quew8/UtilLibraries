package com.quew8.gutils.func.math;

import com.quew8.gutils.func.AbstractSingleOperandFunction;
import com.quew8.gutils.func.Function;

/**
 *
 * @author Quew8
 */
public class ByteNegateFunction<T> extends AbstractSingleOperandFunction<Byte, Byte, T> {

    public ByteNegateFunction(Function<Byte, T> function1) {
        super(function1);
    }
    
    @Override
    public Byte f(T s) {
        return (byte) -function1.f(s);
    }
    
}
