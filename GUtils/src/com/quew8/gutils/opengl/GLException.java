package com.quew8.gutils.opengl;

/**
 * 
 * @author Quew8
 */
public class GLException extends RuntimeException {
    
    public static void checkGLError(String from) {
        int error = OpenGL.glGetError();
        if(error != OpenGL.GL_NO_ERROR) {
            throw new GLException(from, error);
        }
    }
    
    public static void checkGLError() {
        checkGLError("Unknown");
    }
    
    public GLException(String error) {
        super(error);
    }
    
    public GLException(String from, int code) {
        this("GL Error from " + from + ": " + OpenGLUtils.toOpenGLString(code));
    }
}
