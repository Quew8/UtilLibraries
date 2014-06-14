package com.quew8.geng.rendering;

import com.quew8.geng.geometry.Image;
import com.quew8.gmath.Matrix;
import java.util.ArrayList;

/**
 * 
 * @param <T> 
 * @author Quew8
 */
public class DynamicHandleList<T> {
    private int nHandles = 0;
    private final ArrayList<DynamicHandleInstance<T>> instances = new ArrayList<>();
    private int pos = -1;
    
    public DynamicHandleInstance<T> getInstance(T data) {
        return addInstance(new DynamicHandleInstance<T>(data, getNHandles()));
    }

    public boolean hasNext() {
        pos++;
        if (pos >= instances.size()) {
            pos = -1;
            return false;
        } else {
            return true;
        }
    }

    public boolean shouldDrawNext() {
        return getCurrent().shouldDraw();
    }

    public Matrix getNextMatrixForDraw() {
        return getCurrent().getMatrixForDraw();
    }
    
    public T getNextData() {
        return getCurrent().getDataForDraw();
    }
    
    public Image getNextImage(Image defaultImage) {
        return getCurrent().getImageForDraw(defaultImage);
    }

    protected DynamicHandleInstance<T> getCurrent() {
        return instances.get(pos);
    }

    protected int getNHandles() {
        return nHandles;
    }

    protected DynamicHandleInstance<T> addInstance(DynamicHandleInstance<T> t) {
        instances.add(t);
        return t;
    }

    public class DynamicHandle extends Handle {

        public DynamicHandle(int start, int end) {
            super(start, end);
            nHandles++;
        }

        public boolean hasNext() {
            return DynamicHandleList.this.hasNext();
        }

        public boolean shouldDrawNext() {
            return DynamicHandleList.this.shouldDrawNext();
        }

        public Matrix getNextMatrixForDraw() {
            return DynamicHandleList.this.getNextMatrixForDraw();
        }
    
        public T getNextData() {
            return DynamicHandleList.this.getNextData();
        }
    }
}
