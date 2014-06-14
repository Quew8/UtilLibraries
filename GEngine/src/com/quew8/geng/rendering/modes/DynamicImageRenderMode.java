package com.quew8.geng.rendering.modes;

import com.quew8.geng.geometry.Image;
import com.quew8.gutils.opengl.VertexData;
import java.nio.FloatBuffer;

/**
 *
 * @param <T> 
 * @author Quew8
 */
public class DynamicImageRenderMode<T extends ImageFetchable> extends DynamicRenderMode<T> {
    private final DynamicRenderMode<T> superRenderMode;
    
    public DynamicImageRenderMode(DynamicRenderMode<T> superRenderMode) {
        this.superRenderMode = superRenderMode;
    }
    
    @Override
    public void bindGeneralTexture(Image image) {
        
    }
    
    @Override
    public void onPreDraw(T data) {
        data.getImage().bind();
        superRenderMode.onPreDraw(data);
    }
    
    @Override
    public void onPostDraw(T data) {
        superRenderMode.onPostDraw(data);
    }
    
    @Override
    public void onPreRendering(VertexData vd) {
        superRenderMode.onPreRendering(vd);
    }
    
    @Override
    public void onPostRendering() {
        superRenderMode.onPostRendering();
    }
    
    @Override
    public void updateModelMatrix(FloatBuffer matrix) {
        superRenderMode.updateModelMatrix(matrix);
    }

    @Override
    public void updateProjectionMatrix(FloatBuffer matrix) {
        superRenderMode.updateProjectionMatrix(matrix);
    }
    
}
