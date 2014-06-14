package com.quew8.gutils.opengl;

/**
 *
 * @author Quew8
 */
public interface VertexData {
    public void vertexAttribPointer(int index, int type, int size, boolean normalized, int stride, int offset);
    public void bind();
    public void delete();
}
