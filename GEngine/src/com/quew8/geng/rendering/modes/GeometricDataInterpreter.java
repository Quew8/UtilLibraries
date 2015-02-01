package com.quew8.geng.rendering.modes;

import java.nio.ByteBuffer;

/**
 *
 * @param <T>
 * @param <S>
 * @author Quew8
 */
public interface GeometricDataInterpreter<T, S> {
    public S[] toPositions(T t); 
    public void addVertexData(ByteBuffer to, T t, S[] positions);
    public void addVertexData(ByteBuffer to, T t);
    public int getNVertices(T t);
    public int getNBytes(T t);
    public int getMode(T t);
    public int[] getIndices(T t);
}
