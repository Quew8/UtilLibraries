package com.quew8.geng.rendering;

import com.quew8.geng.geometry.EmptyTexture;
import com.quew8.geng.geometry.Texture;
import com.quew8.geng.rendering.modes.FixedSizeDataInterpreter;
import com.quew8.geng.rendering.modes.StaticRenderMode;
import com.quew8.gutils.BufferUtils;
import static com.quew8.gutils.opengl.OpenGL.*;
import com.quew8.gutils.opengl.VertexArray;
import com.quew8.gutils.opengl.VertexBuffer;

/**
 *
 * @author Quew8
 * @param <T>
 */
public abstract class SpriteBatcher<T> {
    //public static SpriteBatcher<?> DEBUG;
    private final Texture tex;
    private final StaticRenderMode renderMode;
    private final FixedSizeDataInterpreter<T, ?>  dataInterpreter;
    //private SpriteIndexDataFactory indexFactory;
    private final VertexArray va;
    private final VertexBuffer ibo;
    private final int maxN;
    private int n = 0;
    
    public SpriteBatcher(Texture tex, StaticRenderMode renderMode, FixedSizeDataInterpreter<T, ?>  dataInterpreter, int maxN) {
        /*if(indexFactory.getRequiredVerticesPerSprite() != data.getNVerticesPerSprite()) {
            throw new IllegalArgumentException("Number of required vertices in indexFactory doesn't match number in data");
        }*/
        this.tex = tex != null ? tex : EmptyTexture.INSTANCE;
        this.renderMode = renderMode;
        this.dataInterpreter = dataInterpreter;
        this.va = new VertexArray(BufferUtils.createByteBuffer(maxN * dataInterpreter.getNBytes()));
        this.maxN = maxN;
        int[] perSpriteIndices = dataInterpreter.getIndices();
        int[] indices = new int[maxN * perSpriteIndices.length];
        for(int i = 0, j = 0; i < indices.length; j += dataInterpreter.getNVertices()) {
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
    
    protected void batch(T t) {
        if(n >= maxN) {
            flush();
        }
        dataInterpreter.addVertexData(va.getBuffer(), t);
        n++;
    }
    
    public void flush() {
        /*if(this == DEBUG) {
            DebugLogger.broadcast("BEGGINING DEBUG SPRITEBATCHER---------------------");
        }*/
        va.getBuffer().rewind();
        renderMode.onPreRendering(va);
        glDrawElements(dataInterpreter.getMode(), n * dataInterpreter.getNIndices(), GL_UNSIGNED_INT, 0);
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
