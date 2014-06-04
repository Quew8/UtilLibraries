package com.quew8.gutils.func;

/**
 * 
 * @author Quew8
 * @param <T>
 * @param <S>
 * @param <U> 
 */
public abstract class AbstractSingleOperandFunction<T, S, U> implements Function<T, U> {
    protected final Function<S, U> function1;
    
    public AbstractSingleOperandFunction(Function<S, U> function1) {
        this.function1 = function1;
    }
}
