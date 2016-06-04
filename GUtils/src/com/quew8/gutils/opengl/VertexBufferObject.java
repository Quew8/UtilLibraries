package com.quew8.gutils.opengl;

import java.nio.ByteBuffer;
import static com.quew8.gutils.opengl.OpenGL.*;

/**
 * 
 * @author Quew8
 */
public class VertexBufferObject extends GBuffer {
    
    public VertexBufferObject(ByteBuffer data, int target, int usage) {
        super(data, target, usage);
    }
    
    public VertexBufferObject(ByteBuffer data, int target) {
        this(data, target, GL_STATIC_DRAW);
    }

    public void vertexAttribPointer(int index, int size, int type, boolean normalized, int stride, int offset) {
        glVertexAttribPointer(index, size, type, normalized, stride, offset);
    }
    
}
