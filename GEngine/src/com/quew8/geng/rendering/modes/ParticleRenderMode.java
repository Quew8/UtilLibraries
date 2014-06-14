package com.quew8.geng.rendering.modes;

import com.quew8.geng.rendering.ParticleEmitter;
import com.quew8.gutils.opengl.GBuffer;
import static com.quew8.gutils.opengl.OpenGL.*;
import com.quew8.gutils.opengl.VertexData;

/**
 *
 * @author Quew8
 */
public abstract class ParticleRenderMode extends StaticRenderMode {
    
    @Override
    public void onPreRendering(VertexData data) {
        GBuffer.unbind(GL_ARRAY_BUFFER);
        useShader();
        ParticleEmitter.vertexBuffer.position(0);
        glVertexAttribPointer(0, 3, false, 28, ParticleEmitter.vertexBuffer.asFloatBuffer());
        ParticleEmitter.vertexBuffer.position(12);
        glVertexAttribPointer(1, 3, false, 28, ParticleEmitter.vertexBuffer.asFloatBuffer());
        ParticleEmitter.vertexBuffer.position(24);
        glVertexAttribPointer(2, 1, false, false, 28, ParticleEmitter.vertexBuffer.asIntBuffer());
        ParticleEmitter.vertexBuffer.position(0);
    }
    
    public abstract void useShader();
    
}
