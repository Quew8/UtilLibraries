package com.quew8.gutils.opengl;

import java.nio.ByteBuffer;
import static com.quew8.gutils.opengl.OpenGL.*;

/**
 *
 * @author Quew8
 */
public class VertexArray implements VertexData {
    private final ByteBuffer buffer;

    public VertexArray(ByteBuffer buffer) {
        this.buffer = buffer;
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }
    
    @Override
    public void vertexAttribPointer(int index, int size, int type, boolean normalized, int stride, int offset) {
        int oldPos = buffer.position();
        buffer.position(offset);
        switch(type) {
        case GL_FLOAT: {
            glVertexAttribPointer(index, size, normalized, stride, buffer.asFloatBuffer());
            break;
        }
        case GL_INT: {
            glVertexAttribPointer(index, size, false, normalized, stride, buffer.asIntBuffer());
            break;
        }
        case GL_UNSIGNED_INT: {
            glVertexAttribPointer(index, size, true, normalized, stride, buffer.asIntBuffer());
            break;
        }
        case GL_BYTE: {
            glVertexAttribPointer(index, size, false, normalized, stride, buffer);
            break;
        }
        case GL_UNSIGNED_BYTE: {
            glVertexAttribPointer(index, size, true, normalized, stride, buffer);
            break;
        }
        default: {
            throw new IllegalArgumentException("type is not a recognized enum: " + type);
        }
        }
        buffer.position(oldPos);
    }

    @Override
    public void bind() {
        
    }
    
    @Override
    public void delete() {
        
    }
}
