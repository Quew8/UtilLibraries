package com.quew8.geng.rendering;

import com.quew8.geng.geometry.EmptyTexture;
import com.quew8.geng.geometry.Texture;
import com.quew8.geng.rendering.modes.StaticRenderMode;
import com.quew8.geng.rendering.modes.SpriteDataFactory;
import com.quew8.geng.rendering.modes.SpriteIndexDataFactory;
import com.quew8.gutils.BufferUtils;
import com.quew8.gutils.debug.DebugLogger;
import static com.quew8.gutils.opengl.OpenGL.*;
import com.quew8.gutils.opengl.VertexBuffer;

/**
 *
 * @author Quew8
 * @param <T>
 */
public abstract class SpriteBatcher<T extends SpriteDataFactory> {
    //public static SpriteBatcher<?> DEBUG;
    private final Texture tex;
    private final StaticRenderMode renderMode;
    private final SpriteBatcherData data;
    private SpriteIndexDataFactory indexFactory;
    private final VertexBuffer ibo;
    private int n = 0;
    
    public SpriteBatcher(Texture tex, StaticRenderMode renderMode, SpriteBatcherData<T> data, SpriteIndexDataFactory indexFactory) {
        if(indexFactory.getRequiredVerticesPerSprite() != data.getNVerticesPerSprite()) {
            throw new IllegalArgumentException("Number of required vertices in indexFactory doesn't match number in data");
        }
        this.tex = tex != null ? tex : EmptyTexture.INSTANCE;
        this.renderMode = renderMode;
        this.data = data;
        this.indexFactory = indexFactory;
        int[] indices = new int[data.getNBatchable() * indexFactory.getIndicesPerSprite()];
        int[] perSpriteIndices = indexFactory.getIndices();
        for(int i = 0, j = 0; i < indices.length; j += data.getNVerticesPerSprite()) {
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
        begin(tex);
    }
    
    public void begin(Texture texture) {
        RenderState.setRenderMode(true, renderMode);
        texture.bind();
        ibo.bind();
    }
    
    protected void predraw() {
        if(n >= data.getNBatchable()) {
            flush();
        }
        n++;
    }
    
    protected SpriteBatcherData.Attribute<T>[] getAllAttribs() {
        return data.getAllAttribs();
    }
    
    public void flush() {
        /*if(this == DEBUG) {
            DebugLogger.broadcast("BEGGINING DEBUG SPRITEBATCHER---------------------");
        }*/
        data.rewindAllBuffers();
        renderMode.onPreRendering(data.getVertexData());
        glDrawElements(indexFactory.getMode(), n * indexFactory.getIndicesPerSprite(), GL_UNSIGNED_INT, 0);
        renderMode.onPostRendering();
        n = 0;
        /*if(this == DEBUG) {
            DebugLogger.broadcast("ENDING DEBUG SPRITEBATCHER--------------------");
        }*/
    }
    
    public void end() {
        if(n > 0) {
            flush();
        }
    }
    
    public void dispose() {
        tex.dispose();
        ibo.delete();
    }
}
