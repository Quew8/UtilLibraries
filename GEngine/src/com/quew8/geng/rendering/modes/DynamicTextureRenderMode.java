package com.quew8.geng.rendering.modes;

import com.quew8.geng.geometry.Texture;
import com.quew8.gutils.opengl.VertexBufferObject;
import java.nio.FloatBuffer;

/**
 *
 * @param <T> 
 * @author Quew8
 */
public class DynamicTextureRenderMode<T extends TextureFetchable> extends DynamicRenderMode<T> {
    private final DynamicRenderMode<T> superRenderMode;
    
    public DynamicTextureRenderMode(DynamicRenderMode<T> superRenderMode) {
        super(superRenderMode.getNAttribs(), superRenderMode.getStride());
        this.superRenderMode = superRenderMode;
    }
    
    @Override
    public void bindGeneralTexture(Texture image) {
        
    }
    
    @Override
    public void onPreDraw(T data) {
        data.getTexture().bind();
        superRenderMode.onPreDraw(data);
    }
    
    @Override
    public void onPostDraw(T data) {
        superRenderMode.onPostDraw(data);
    }
    
    @Override
    public void onPreRendering(VertexBufferObject vd) {
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
