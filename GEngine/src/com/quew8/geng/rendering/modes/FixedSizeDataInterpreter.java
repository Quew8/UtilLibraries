package com.quew8.geng.rendering.modes;

/**
 *
 * @author Quew8
 * @param <T>
 * @param <S>
 */
public abstract class FixedSizeDataInterpreter<T, S> implements GeometricDataInterpreter<T, S> {
    public abstract int getNVertices();
    public abstract int getNBytes();
    public abstract int[] getIndices();
    public abstract int getNIndices();
    public abstract int getMode();
    
    @Override
    public final int getNVertices(T t) {
        return getNVertices();
    }

    @Override
    public final int getNBytes(T t) {
        return getNBytes();
    }

    @Override
    public final int[] getIndices(T t) {
        return getIndices();
    }

    @Override
    public final int getMode(T t) {
        return getMode();
    }
}
