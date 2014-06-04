package com.quew8.gutils.func.math;

import com.quew8.gutils.func.AbstractDualOperandFunction;
import com.quew8.gutils.func.Function;

/**
 *
 * @author Quew8
 */
public class ByteMultFunction<S> extends AbstractDualOperandFunction<Byte, Byte, S> {
    
    public ByteMultFunction(Function<Byte, S> function1, Function<Byte, S> function2) {
        super(function1, function2);
    }
    
    @Override
    public Byte f(S s) {
        return (byte) (function1.f(s) * function2.f(s));
    }
    
}
