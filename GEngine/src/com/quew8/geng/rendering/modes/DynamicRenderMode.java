package com.quew8.geng.rendering.modes;

import com.quew8.geng.geometry.Image;
import java.nio.FloatBuffer;

/**
 *
 * @param <T> 
 * @author Quew8
 */
public abstract class DynamicRenderMode<T> extends StaticRenderMode {
    
    public void onPreDraw(T data) {};

    public void onPostDraw(T data) {};
    
    public void bindGeneralTexture(Image image) {
        image.bind();
    }
    
    public abstract void updateModelMatrix(FloatBuffer matrix);
}
