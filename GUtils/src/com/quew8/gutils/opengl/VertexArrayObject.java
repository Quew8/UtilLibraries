package com.quew8.gutils.opengl;

import static com.quew8.gutils.opengl.GObject.idBuff;
import static com.quew8.gutils.opengl.OpenGL.glBindVertexArray;
import static com.quew8.gutils.opengl.OpenGL.glDeleteVertexArrays;
import static com.quew8.gutils.opengl.OpenGL.glGenVertexArrays;

/**
 *
 * @author Quew8
 */
public class VertexArrayObject extends GObject {

    public VertexArrayObject() {
        super(genId());
    }
    
    public void bind() {
        glBindVertexArray(getId());
    }
    
    @Override
    public void delete() {
        glDeleteVertexArrays(getIdBuffer());
    }
    
    public static void unbind() {
        glBindVertexArray(0);
    }
    
    private static int genId() {
        glGenVertexArrays(idBuff);
        return idBuff.get(0);
    }
}
