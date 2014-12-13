package com.quew8.geng.rendering.modes;

import com.quew8.gutils.opengl.VertexData;
import java.nio.FloatBuffer;

/**
 *
 * @author Quew8
 */
public abstract class StaticRenderMode {
    private final int nAttribs;
    
    public StaticRenderMode(int nAttribs) {
        this.nAttribs = nAttribs;
    }
    
    public void onMadeCurrent() {}
    
    public void onMadeNonCurrent() {}
    
    public void onPreRendering(VertexData vd) {}

    public void onPostRendering() {}

    public abstract void updateProjectionMatrix(FloatBuffer matrix);
    
    public int getNAttribs() {
        return nAttribs;
    }
}
