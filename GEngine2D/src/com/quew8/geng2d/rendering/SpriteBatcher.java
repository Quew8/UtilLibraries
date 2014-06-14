package com.quew8.geng2d.rendering;

import com.quew8.geng.geometry.Image;
import com.quew8.geng.rendering.RenderState;
import com.quew8.geng.rendering.modes.StaticRenderMode;
import com.quew8.gutils.BufferUtils;
import static com.quew8.gutils.opengl.OpenGL.*;
import com.quew8.gutils.opengl.VertexArray;
import com.quew8.gutils.opengl.VertexBuffer;

/**
 *
 * @author Quew8
 */
public class SpriteBatcher {
    private final Image texture;
    private final StaticRenderMode renderMode;
    private VertexArray vertexArray;
    private int n = 0;
    private static final VertexBuffer ibo = new VertexBuffer(
            BufferUtils.createByteBuffer(new int[]{0, 1, 2, 0, 2, 3}), 
            GL_ELEMENT_ARRAY_BUFFER, 
            GL_STATIC_DRAW
    );
    
    public SpriteBatcher(Image texture, StaticRenderMode renderMode, int size) {
        this.texture = texture;
        this.renderMode = renderMode;
        vertexArray = new VertexArray(BufferUtils.createByteBuffer(size));
    }
    
    public void draw(float x, float y, float width, float height) {
        
    }
    
    public void flush() {
        vertexArray.getBuffer().flip();
        RenderState.setRenderMode(renderMode);
        renderMode.onPreRendering(vertexArray);
        glDrawElements(GL_TRIANGLES, n, GL_UNSIGNED_INT, 0);
        renderMode.onPostRendering();
        n = 0;
    }
}
