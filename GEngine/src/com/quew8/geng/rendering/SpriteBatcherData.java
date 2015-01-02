package com.quew8.geng.rendering;

import com.quew8.geng.rendering.modes.SpriteDataFactory;
import com.quew8.gutils.BufferUtils;
import com.quew8.gutils.opengl.NonInterleavedVertexArray;
import com.quew8.gutils.opengl.NonInterleavedVertexArray.VertexArrayWithOffset;
import com.quew8.gutils.opengl.VertexArray;
import com.quew8.gutils.opengl.VertexData;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.Arrays;

/**
 *
 * @author Quew8
 * @param <T>
 */
public class SpriteBatcherData<T extends SpriteDataFactory> {
    private final int nBatchable;
    private final int nVerticesPerSprite;
    private final int spriteSize;
    private final Attribute<T>[] attribDatas;
    private final VertexData vertexData;
    
    public SpriteBatcherData(int n, T... attribFactories) {
        if(n <= 0) {
            throw new IllegalArgumentException("Number of batchable sprites must be greater than 0");
        }
        this.nVerticesPerSprite = attribFactories[0].getVerticesPerSprite();
        for(int i = 1; i < attribFactories.length; i++) {
            if(attribFactories[i].getVerticesPerSprite() != nVerticesPerSprite) {
                throw new IllegalArgumentException("Number of vertices in each factory of SpriteBatcherData must match");
            }
        }
        this.nBatchable = n;
        int lSpriteSize = 0;
        for(T factory: attribFactories) {
            lSpriteSize += factory.getBytesPerSprite();
        }
        this.spriteSize = lSpriteSize;
        this.attribDatas = getArray(attribFactories.length);
        //ByteBuffer bb = BufferUtils.createByteBuffer(spriteSize * n);
        //int offset = 0;
        VertexArrayWithOffset[] vertexArrays = new VertexArrayWithOffset[attribFactories.length];
        int vertexOffset = 0;
        for(int i = 0; i < attribFactories.length; i++) {
            attribDatas[i] = new Attribute(
                    attribFactories[i],
                    /*new VertexArray(BufferUtils.getSlice(
                            bb, offset, attribFactories[i].getBytesPerSprite() *  n
                    )),
                    offset*/
                    new VertexArray(BufferUtils.createByteBuffer(attribFactories[i].getBytesPerSprite() *  n)),
                    0
            );
            vertexArrays[i] = new VertexArrayWithOffset(attribDatas[i].vertexArray, attribFactories[i].getBytesPerVertex(), vertexOffset);
            //offset += attribFactories[i].getBytesPerSprite() * n;
            vertexOffset += attribFactories[i].getBytesPerVertex();
        }
        this.vertexData = NonInterleavedVertexArray.interleaveVertexArrays(vertexArrays);
    }
    
    protected int getNBatchable() {
        return nBatchable;
    }

    protected int getNVerticesPerSprite() {
        return nVerticesPerSprite;
    }
    
    protected VertexData getVertexData() {
        return vertexData;
    }
    
    protected void rewindAllBuffers() {
        for(int i = 0; i < attribDatas.length; i++) {
            attribDatas[i].vertexArray.getBuffer().position(attribDatas[i].offset);
            //attribDatas[i].vertexArray.getBuffer().position(0);
            //attribDatas[i].vertexArray.getBuffer().rewind();
        }
    }
    
    public Attribute<T>[] getAllAttribs() {
        return attribDatas;
    }
    
    public static class Attribute<T extends SpriteDataFactory> {
        private final T dataFactory;
        private final VertexArray vertexArray;
        private final int offset;
        
        private Attribute(T dataFactory, VertexArray vertexArray, int offset) {
            this.dataFactory = dataFactory;
            this.vertexArray = vertexArray;
            this.offset = offset;
        }

        public T getFactory() {
            return dataFactory;
        }

        public ByteBuffer getBuffer() {
            return vertexArray.getBuffer();
        }

        public int getOffset() {
            return offset;
        }
    }
    
    private static <E> E[] getArray(int length, E... arr) {
        return Arrays.copyOf(arr, length);
    }
}
