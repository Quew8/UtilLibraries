package com.quew8.geng.rendering;

import com.quew8.geng.geometry.EmptyTexture;
import com.quew8.geng.geometry.Texture;
import com.quew8.geng.rendering.modes.FixedSizeDataInterpreter;
import com.quew8.geng.rendering.modes.StaticRenderMode;
import com.quew8.gutils.BufferUtils;
import static com.quew8.gutils.opengl.OpenGL.*;
import com.quew8.gutils.opengl.OpenGLUtils;
import com.quew8.gutils.opengl.VertexArrayObject;
import com.quew8.gutils.opengl.VertexBufferObject;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 * @param <T>
 */
public class SpriteBatcher<T> {
    private final Texture tex;
    private final StaticRenderMode renderMode;
    private final FixedSizeDataInterpreter<T, ?>  dataInterpreter;
    private final VertexArrayObject vao;
    private final VertexBufferObject vbo;
    private final ByteBuffer bufferData;
    private final VertexBufferObject ibo;
    private final int maxN;
    private int n = 0;
    
    public SpriteBatcher(Texture tex, StaticRenderMode renderMode, 
            FixedSizeDataInterpreter<T, ?>  dataInterpreter, int maxN) {
        
        this.tex = tex != null ? tex : EmptyTexture.INSTANCE;
        this.renderMode = renderMode;
        this.dataInterpreter = dataInterpreter;
        this.bufferData = BufferUtils.createByteBuffer(maxN * dataInterpreter.getNBytes());
        this.vbo = new VertexBufferObject(
                bufferData,
                GL_ARRAY_BUFFER, 
                GL_DYNAMIC_DRAW
        );
        this.maxN = maxN;
        int[] perSpriteIndices = dataInterpreter.getIndices();
        int[] indices = new int[maxN * perSpriteIndices.length];
        for(int i = 0, j = 0; i < indices.length; j += dataInterpreter.getNVertices()) {
            for(int k = 0; k < perSpriteIndices.length; k++) {
                indices[i++] = j + perSpriteIndices[k];
            }
        }
        ByteBuffer indicesBuffer = BufferUtils.createByteBuffer(indices.length * 4);
        indicesBuffer.asIntBuffer().put(indices);
        indicesBuffer.limit(indices.length * 4);
        this.ibo = new VertexBufferObject(
                indicesBuffer, 
                GL_ELEMENT_ARRAY_BUFFER, 
                GL_STATIC_DRAW
        );
        this.vao = new VertexArrayObject();
        vao.bind();
        ibo.bind();
        vbo.bind(); 
        for(int j = 0; j < renderMode.getNAttribs(); j++) {
            glEnableVertexAttribArray(j);
        }
        renderMode.onPreRendering(vbo);
        VertexArrayObject.unbind();
        vbo.unbind();
    }
    
    public void begin(Texture texture) {
        //RenderState.setRenderMode(true, renderMode);
        RenderState.setStaticRenderMode(renderMode);
        texture.bind();
        vao.bind();
        vbo.bind();
        //ibo.bind();
    }
    
    public void begin() {
        begin(this.tex);
    }
    
    public void batch(T t) {
        if(n >= maxN) {
            flush();
        }
        //dataInterpreter.addVertexData(va.getBuffer(), t);
        dataInterpreter.addVertexData(bufferData, t);
        n++;
    }
    
    public void flush() {
        if(n > 0) {
            bufferData.flip();
            glBufferSubData(GL_ARRAY_BUFFER, 0, bufferData);
            //renderMode.onPreRendering(vbo);
            /*va.getBuffer().rewind();
            renderMode.onPreRendering(va);*/
            /*System.out.println(OpenGLUtils.getPointerInfo());
            System.out.println(OpenGLUtils.getViewportInfo());
            System.out.println(OpenGLUtils.getArrayBufferDataAsFloat(vbo.getId()));
            System.out.println(OpenGLUtils.getElementBufferDataAsInt(ibo.getId()));*/
            glDrawElements(dataInterpreter.getMode(), n * dataInterpreter.getNIndices(), GL_UNSIGNED_INT, 0);
            renderMode.onPostRendering();
            n = 0;
            bufferData.limit(bufferData.capacity());
        }
    }
    
    public void end() {
        flush();
    }
    
    public void dispose() {
        vao.delete();
        tex.dispose();
        vbo.delete();
        ibo.delete();
    }
}
