package com.quew8.geng.rendering.modes;

import com.quew8.gutils.opengl.VertexBufferObject;
import java.nio.FloatBuffer;

/**
 *
 * @author Quew8
 */
public abstract class StaticRenderMode {
    private final int nAttribs;
    private final int stride;
    
    public StaticRenderMode(int nAttribs, int stride) {
        this.nAttribs = nAttribs;
        this.stride = stride;
    }
    
    public void onMadeCurrentStatic() {}
    
    public void onMadeNonCurrent() {}
    
    public void onPreRendering(VertexBufferObject vd) {}

    public void onPostRendering() {}

    public abstract void updateProjectionMatrix(FloatBuffer matrix);
    
    public int getNAttribs() {
        return nAttribs;
    }

    public int getStride() {
        return stride;
    }
}
