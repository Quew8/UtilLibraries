package com.quew8.gutils.func;

/**
 *
 * @param <T> 
 * @param <U> 
 * @author Quew8
 */
public class WrapFunction<T, U> implements Function<T, U> {
    private final T t;
    
    public WrapFunction(T t) {
        this.t = t;
    }
    
    @Override
    public T f(U s) {
       return t;
    }
    
}
