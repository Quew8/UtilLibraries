package com.quew8.gutils.func.math;

import com.quew8.gutils.func.AbstractDualOperandFunction;
import com.quew8.gutils.func.Function;

/**
 *
 * @author Quew8
 */
public class IntMultFunction<S> extends AbstractDualOperandFunction<Integer, Integer, S> {
    
    public IntMultFunction(Function<Integer, S> function1, Function<Integer, S> function2) {
        super(function1, function2);
    }
    
    @Override
    public Integer f(S s) {
        return function1.f(s) * function2.f(s);
    }
    
}
