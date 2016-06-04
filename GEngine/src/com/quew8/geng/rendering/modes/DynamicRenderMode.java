package com.quew8.geng.rendering.modes;

import com.quew8.geng.geometry.Texture;
import com.quew8.gutils.BufferUtils;
import java.nio.FloatBuffer;

/**
 *
 * @param <T> 
 * @author Quew8
 */
public abstract class DynamicRenderMode<T> extends StaticRenderMode {
    private static final FloatBuffer ID_MATRIX;
    static {
        ID_MATRIX = BufferUtils.createFloatBuffer(16);
        ID_MATRIX.put(new float[]{
            1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1
        });
        ID_MATRIX.flip();
    }
    
    public DynamicRenderMode(int nAttribs, int stride) {
        super(nAttribs, stride);
    }

    @Override
    public void onMadeCurrentStatic() {
        onMadeCurrent();
        updateModelMatrix(ID_MATRIX);
    }
    
    public void onMadeCurrent() {};
    
    public void onPreDraw(T data) {};

    public void onPostDraw(T data) {};
    
    public void bindGeneralTexture(Texture image) {
        image.bind();
    }
    
    public abstract void updateModelMatrix(FloatBuffer matrix);
}
