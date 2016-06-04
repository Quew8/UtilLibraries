package com.quew8.gutils.opengl;

import com.quew8.gutils.BufferUtils;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 *
 * @author Quew8
 */
public class VertexBufferObjectOffsets {
    private final VertexBufferObject vbo;
    private final int[] offsets;
    
    public VertexBufferObjectOffsets(int[][] indices, int target, int usage) {
        this.offsets = new int[indices.length + 1];
        int total = 0;
        for(int i = 0; i < indices.length; i++) {
            this.offsets[i] = total;
            total += indices[i].length;
        }
        this.offsets[indices.length] = total;
        ByteBuffer bb = BufferUtils.createByteBuffer(4 * total);
        IntBuffer ib = bb.asIntBuffer();
        for(int i = 0; i < indices.length; i++) {
            ib.put(indices[i]);
        }
        //System.out.println(BufferUtils.toString(bb.asIntBuffer(), true, 200));
        vbo = new VertexBufferObject(bb, target, usage);
    }
    
    public VertexBufferObject getVBO() {
        return vbo;
    }
    
    public int getOffset(int i) {
        return offsets[i];
    }
    
    public int getNElements() {
        return offsets.length;
    }
}
