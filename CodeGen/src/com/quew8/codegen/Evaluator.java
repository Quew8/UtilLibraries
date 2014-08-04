package com.quew8.codegen;

/**
 *
 * @author Quew8
 * @param <T>
 * @param <S>
 * @param <U>
 */
public interface Evaluator<T, S extends Element<T, ?>, U extends Evaluator<T, S, U>> {
    public void prepare(S element);
    public FetchMethod<U>[] getFetchMethods();
}
