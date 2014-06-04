package com.quew8.gutils.func;

/**
 * 
 * @author Quew8
 * @param <T>
 * @param <S>
 * @param <U> 
 */
public class JoinFunction<T, S, U> implements Function<T, U> {
    private final Function<T, S> outerFunction;
    private final Function<S, U> innerFunction;

    public JoinFunction(Function<T, S> outerFunction, Function<S, U> innerFunction) {
        this.outerFunction = outerFunction;
        this.innerFunction = innerFunction;
    }
    
    @Override
    public T f(U s) {
        return outerFunction.f(innerFunction.f(s));
    }
}
