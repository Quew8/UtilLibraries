package com.quew8.geng.rendering;

import com.quew8.geng.geometry.Image;
import com.quew8.gmath.Matrix;

/**
 *
 * @author Quew8
 */
public interface IDynamicHandle<T> extends IHandle {
    public void draw(Matrix matrix);
    
    Matrix getMatrixForDraw();
    
    T getDataForDraw();
    
    Image getImageForDraw(Image img);
    
    boolean shouldDraw();
}
