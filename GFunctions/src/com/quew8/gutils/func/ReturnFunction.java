package com.quew8.gutils.func;

/**
 *
 * @author Quew8
 */
public class ReturnFunction<T> implements Function<T, T> {
    @Override
    public T f(T s) {
        return s;
    }
}
