package com.quew8.geng.rendering.modes;

import com.quew8.geng.geometry.GeometricObject;
import com.quew8.geng.geometry.IVertex;
import com.quew8.geng.geometry.Param;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 * @param <T>
 * @param <S>
 */
public class GeometricObjectInterpreter<T extends GeometricObject<T, S>, S extends IVertex<S>> implements GeometricDataInterpreter<T, S> {
    private final Param[] params;

    public GeometricObjectInterpreter(Param... params) {
        this.params = params;
    }
    
    @Override
    public S[] toPositions(T t) {
        return t.getVertices();
    }

    @Override
    public void addVertexData(ByteBuffer to, T t, S[] positions) {
        addVertexData(to, t);
    }
    
    @Override
    public void addVertexData(ByteBuffer to, T t) {
        for(int i = 0; i < t.getNVertices(); i++) {
            for(int j = 0; j < params.length; j++) {
                t.getVertices()[i].addData(to, params[j]);
            }
        }
    }

    @Override
    public int getNVertices(T t) {
        return t.getNVertices();
    }

    @Override
    public int getNBytes(T t) {
        return getNVertices(t) * params.length * 4;
    }

    @Override
    public int[] getIndices(T t) {
        return t.getIndices();
    }

    @Override
    public int getMode(T t) {
        return t.getMode();
    }
}
