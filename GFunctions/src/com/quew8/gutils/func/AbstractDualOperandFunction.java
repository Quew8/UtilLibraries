package com.quew8.gutils.func;

/**
 *
 * @author Quew8
 */
public abstract class AbstractDualOperandFunction<T, S, U> extends AbstractSingleOperandFunction<T, S, U> {
    protected final Function<S, U> function2;

    public AbstractDualOperandFunction(Function<S, U> function1, Function<S, U> function2) {
        super(function1);
        this.function2 = function2;
    }
    
}
