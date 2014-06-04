package com.quew8.codegen;

/**
 *
 * @author Quew8
 * @param <T>
 * @param <S>
 */
public interface Evaluator<T extends Element, S extends Evaluator<T, S>> {
    public void prepare(T element);
    public FetchMethod<S>[] getFetchMethods();
}
