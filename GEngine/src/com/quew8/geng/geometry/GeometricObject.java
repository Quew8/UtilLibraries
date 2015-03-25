package com.quew8.geng.geometry;

import com.quew8.gmath.Matrix;
import java.util.Arrays;

/**
 *
 * @author Quew8
 * @param <T>
 * @param <S>
 */
public abstract class GeometricObject<T extends GeometricObject<T, S>, S extends IVertex<S>> {
    private final S[] vertices;
    private final int[] indices;
    private final int mode;
    
    protected GeometricObject(S[] vertices, int[] indices, int mode) {
        this.vertices = vertices;
        this.indices = indices;
        this.mode = mode;
    }

    public S[] getVertices() {
        return vertices;
    }

    public int getNVertices() {
        return vertices.length;
    }
    
    public int[] getIndices() {
        return indices;
    }

    public int getNIndices() {
        return indices.length;
    }
    
    public int getMode() {
        return mode;
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
        return construct(self(), newVertices, cpyIndices, mode);
    }
    
    public T transform(Image img) {
        int[] cpyIndices = Arrays.copyOf(indices, indices.length);
        S[] newVertices = Arrays.copyOf(vertices, vertices.length);
        for(int i = 0; i < newVertices.length; i++) {
            newVertices[i] = newVertices[i].transformTextureCoords(img);
        }
        return construct(self(), newVertices, cpyIndices, mode);
    }
    
    @SuppressWarnings("unchecked")
    private T self() {
        return (T) this;
    }
    
    protected abstract T construct(T old, S[] vertices, int[] indices, int mode);
    
    @Override
    public String toString() {
        String s = getClass().getSimpleName() + "\nVertices:\n";
        for(int i = 0; i < vertices.length; i++) {
            s += Integer.toString(i) + " - " + vertices[i] + "\n";
        }
        s += "Indices:\n";
        for(int i = 0; i < indices.length; i++) {
            s += Integer.toString(i) + " - " + Integer.toString(indices[i]) + "\n";
        }
        return s;
    }
}
