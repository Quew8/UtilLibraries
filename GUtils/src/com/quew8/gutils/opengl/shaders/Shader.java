package com.quew8.gutils.opengl.shaders;

import com.quew8.gutils.opengl.GLException;
import com.quew8.gutils.opengl.GObject;
import static com.quew8.gutils.opengl.OpenGL.*;

/**
 * 
 * @author Quew8
 */
public class Shader extends GObject {
    
    public Shader(String source, int type) {
        super(genShaderId(type));
        glShaderSource(getId(), source);
        glCompileShader(getId());
        glGetShaderiv(getId(), GL_COMPILE_STATUS, idBuff);
        if (idBuff.get(0) != GL_TRUE) {
            throw new ShaderCompileException(glGetShaderInfoLog(getId()), source);
        }
        GLException.checkGLError();
    }
    
    @Override
    public void delete() {
        glDeleteShader(getId());
    }
    
    private static int genShaderId(int type) {
        return glCreateShader(type);
    }
}
