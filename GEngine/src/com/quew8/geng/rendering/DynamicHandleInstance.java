package com.quew8.geng.rendering;

import com.quew8.geng.geometry.Texture;
import com.quew8.gmath.Matrix;

/**
 *
 * @author Quew8
 * @param <T>
 */
public class DynamicHandleInstance<T> implements IDynamicHandle<T> {
    private final int nHandles;
    private Matrix matrix;
    private final T data;
    private int shouldDraw = 0;
    
    protected DynamicHandleInstance(T data, int nHandles) {
        this.nHandles = nHandles;
        this.data = data;
    }
    
    @Override
    public void draw(Matrix matrix) {
        this.shouldDraw = nHandles;
        this.matrix = matrix;
    }

    @Override
    public Matrix getMatrixForDraw() {
        this.shouldDraw--;
        return matrix;
    }
    
    @Override
    public T getDataForDraw() {
        return data;
    }
    
    @Override
    public Texture getImageForDraw(Texture img) {
        return img;
    }
    
    @Override
    public boolean shouldDraw() {
        return shouldDraw > 0;
    }
}
