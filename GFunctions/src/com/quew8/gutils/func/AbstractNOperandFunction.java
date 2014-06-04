package com.quew8.gutils.func;

/**
 *
 * @param <T> 
 * @param <S> 
 * @param <U> 
 * @author Quew8
 */
public abstract class AbstractNOperandFunction<T, S, U> implements Function<T, U> {
    protected final Function<S, U>[] functions;
    
    public AbstractNOperandFunction(Function<S, U>[] functions) {
        this.functions = functions;
    }
}
