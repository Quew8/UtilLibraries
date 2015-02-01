package com.quew8.geng.rendering.modes;

import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 * @param <T>
 * @param <S>
 */
public class FixedSizeDataInterpreterImpl<T, S> extends FixedSizeDataInterpreter<T, S> {
    private final GeometricDataInterpreter<T, S> dataInterpreter;
    private final int nVertices;
    private final int nBytes;
    private final int[] indices;
    private final int mode;
    
    public FixedSizeDataInterpreterImpl(GeometricDataInterpreter<T, S> dataInterpreter, T example) {
        this.dataInterpreter = dataInterpreter;
        this.nVertices = dataInterpreter.getNVertices(example);
        this.nBytes = dataInterpreter.getNBytes(example);
        this.indices = dataInterpreter.getIndices(example);
        this.mode = dataInterpreter.getMode(example);
    }
    
    @Override
    public S[] toPositions(T t) {
        return dataInterpreter.toPositions(t);
    }

    @Override
    public void addVertexData(ByteBuffer to, T t, S[] positions) {
        dataInterpreter.addVertexData(to, t, positions);
    }

    @Override
    public void addVertexData(ByteBuffer to, T t) {
        dataInterpreter.addVertexData(to, t);
    }

    @Override
    public int getNVertices() {
        return nVertices;
    }

    @Override
    public int getNBytes() {
        return nBytes;
    }

    @Override
    public int getMode() {
        return mode;
    }

    @Override
    public int[] getIndices() {
        return indices;
    }

    @Override
    public int getNIndices() {
        return indices.length;
    }
    
}
