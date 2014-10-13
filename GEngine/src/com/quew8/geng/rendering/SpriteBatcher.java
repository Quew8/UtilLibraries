package com.quew8.geng.rendering;

import com.quew8.geng.geometry.EmptyTexture;
import com.quew8.geng.geometry.Texture;
import com.quew8.geng.rendering.modes.StaticRenderMode;
import com.quew8.geng.rendering.modes.SpriteDataFactory;
import com.quew8.gutils.BufferUtils;
import static com.quew8.gutils.opengl.OpenGL.*;
import com.quew8.gutils.opengl.VertexArray;
import com.quew8.gutils.opengl.VertexBuffer;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 * @param <T>
 */
public abstract class SpriteBatcher<T extends SpriteDataFactory> {
    private final Texture tex;
    private final StaticRenderMode renderMode;
    private final T dataFactory;
    private final VertexArray vertexArray;
    private final VertexBuffer ibo;
    private int n = 0;
    
    public SpriteBatcher(Texture tex, StaticRenderMode renderMode, T dataFactory, int size) {
        if(size < dataFactory.getBytesPerSprite()) {
            throw new IllegalArgumentException("Size of buffer cannot be less than the size required for one sprite");
        }
        this.tex = tex != null ? tex : EmptyTexture.INSTANCE;
        this.renderMode = renderMode;
        this.dataFactory = dataFactory;
        this.vertexArray = new VertexArray(BufferUtils.createByteBuffer(size));
        int[] indices = new int[( size / dataFactory.getBytesPerSprite() ) * dataFactory.getIndicesPerSprite()];
        int[] perSpriteIndices = dataFactory.getIndices();
        for(int i = 0, j = 0; i < indices.length; j += dataFactory.getVerticesPerSprite()) {
            for(int k = 0; k < perSpriteIndices.length; k++) {
                indices[i++] = j + perSpriteIndices[k];
            }
        }
        this.ibo = new VertexBuffer(
                BufferUtils.createByteBuffer(indices), 
                GL_ELEMENT_ARRAY_BUFFER, 
                GL_STATIC_DRAW
        );
    }
    
    public void begin() {
        RenderState.setRenderMode(true, renderMode);
        tex.bind();
        ibo.bind();
    }
    
    protected void predraw() {
        if(vertexArray.getBuffer().limit() - dataFactory.getBytesPerSprite() < vertexArray.getBuffer().position()) {
            flush();
        }
        n++;
    }
    
    protected T getFactory() {
        return dataFactory;
    }
    
    protected ByteBuffer getBuffer() {
        return vertexArray.getBuffer();
    }
    
    public void flush() {
        vertexArray.getBuffer().flip();
        renderMode.onPreRendering(vertexArray);
        glDrawElements(dataFactory.getMode(), n * dataFactory.getIndicesPerSprite(), GL_UNSIGNED_INT, 0);
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
