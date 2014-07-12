package com.quew8.geng2d.rendering;

import com.quew8.geng.geometry.Texture;
import com.quew8.geng.geometry.Image;
import com.quew8.geng.rendering.RenderState;
import com.quew8.geng.rendering.modes.StaticRenderMode;
import com.quew8.geng2d.rendering.modes.GeneralSpriteDataFactory;
import com.quew8.geng2d.rendering.modes.SpriteDataFactory;
import com.quew8.gutils.BufferUtils;
import com.quew8.gutils.opengl.OpenGL;
import static com.quew8.gutils.opengl.OpenGL.*;
import com.quew8.gutils.opengl.VertexArray;
import com.quew8.gutils.opengl.VertexBuffer;

/**
 *
 * @author Quew8
 */
public class SpriteBatcher {
    private final int mode;
    private final Texture tex;
    private final StaticRenderMode renderMode;
    private final SpriteDataFactory dataFactory;
    private final VertexArray vertexArray;
    private final VertexBuffer ibo;
    private int n = 0;
    
    public SpriteBatcher(int mode, Texture tex, StaticRenderMode renderMode, SpriteDataFactory dataFactory, int size) {
        this.mode = mode;
        this.tex = tex;
        this.renderMode = renderMode;
        this.dataFactory = dataFactory;
        this.vertexArray = new VertexArray(BufferUtils.createByteBuffer(size));
        int[] indices = new int[(size / dataFactory.getBytesPerSprite()) * 6];
        for(int i = 0, j = 0; i < indices.length; j += 4) {
            indices[i++] = j + 0;
            indices[i++] = j + 1;
            indices[i++] = j + 2;
            indices[i++] = j + 0;
            indices[i++] = j + 2;
            indices[i++] = j + 3;
        }
        this.ibo = new VertexBuffer(
                BufferUtils.createByteBuffer(indices), 
                GL_ELEMENT_ARRAY_BUFFER, 
                GL_STATIC_DRAW
        );
    }
    
    public SpriteBatcher(Texture image, StaticRenderMode renderMode, SpriteDataFactory dataFactory, int size) {
        this(OpenGL.GL_TRIANGLES, image, renderMode, dataFactory, size);
    }
    
    public SpriteBatcher(Texture image, StaticRenderMode renderMode, int size) {
        this(image, renderMode, GeneralSpriteDataFactory.INSTANCE, size);
    }
    
    public void begin() {
        tex.bind();
        ibo.bind();
        RenderState.setRenderMode(renderMode);
    }
    
    public void draw(Image texture, float x, float y, float width, float height) {
        if(vertexArray.getBuffer().limit() - dataFactory.getBytesPerSprite() < vertexArray.getBuffer().position()) {
            flush();
        }
        n++;
        dataFactory.addData(vertexArray.getBuffer(), texture, x, y, width, height);
    }
    
    public void flush() {
        vertexArray.getBuffer().flip();
        renderMode.onPreRendering(vertexArray);
        glDrawElements(mode, n * 6, GL_UNSIGNED_INT, 0);
        renderMode.onPostRendering();
        n = 0;
    }
    
    public void end() {
        if(n > 0) {
            flush();
        }
    }
    
    public void dispose() {
        tex.dispose();
        vertexArray.delete();
        ibo.delete();
    }
}
