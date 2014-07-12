package com.quew8.geng.geometry;

import com.quew8.gmath.Matrix;
import java.util.Arrays;

/**
 *
 * @author Quew8
 * @param <T>
 * @param <S>
 */
public abstract class GeometricObject<T extends GeometricObject<T, S>, S extends AbstractVertex<S>> {
    private final S[] vertices;
    private final int[] indices;
    
    public GeometricObject(S[] vertices, int[] indices, Image texture) {
        for(int i = 0; i < vertices.length; i++) {
            vertices[i].transformTextureCoords(texture);
        }
        this.vertices = vertices;
        this.indices = indices;
    }
    
    protected GeometricObject(S[] vertices, int[] indices) {
        this(vertices, indices, new Image(0, 0, 1, 1));
    }

    public S[] getVertices() {
        return vertices;
    }

    public int getVerticesLength() {
        return vertices.length;
    }
    
    public int[] getIndices() {
        return indices;
    }

    public int getIndicesLength() {
        return indices.length;
    }
    
    public int getMaxIndex() {
        return vertices.length;
    }

    public T transform(Matrix transform, boolean flip) {
        Matrix normalTransform = transform.getRotation();
        int[] cpyIndices = Arrays.copyOf(indices, indices.length);
        S[] newVertices = Arrays.copyOf(vertices, vertices.length);
        for(int i = 0; i < newVertices.length; i++) {
            newVertices[i] = newVertices[i].transform(transform, normalTransform);
        }
        if(flip) {
            for(int i = 0; i < cpyIndices.length; i++) {
                i++;
                int j = cpyIndices[i];
                cpyIndices[i] = cpyIndices[i+1];
                cpyIndices[i+1] = j;
                i++;
            }
        }
        return construct(self(), newVertices, cpyIndices);
    }
    
    @SuppressWarnings("unchecked")
    private T self() {
        return (T) this;
    }
    
    protected abstract T construct(T old, S[] vertices, int[] indices);
}
