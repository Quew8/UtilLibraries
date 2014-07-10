package com.quew8.geng.rendering.modes;

import com.quew8.gutils.opengl.VertexData;
import java.nio.FloatBuffer;

/**
 *
 * @author Quew8
 */
public abstract class StaticRenderMode {

    public void onMadeCurrent() {}
    
    public void onMadeNonCurrent() {}
    
    public void onPreRendering(VertexData vd) {}

    public void onPostRendering() {}

    public abstract void updateProjectionMatrix(FloatBuffer matrix);
}
