package com.quew8.gutils.desktop;

import static com.quew8.gutils.opengl.OpenGL.*;

/**
 * 
 * @author Quew8
 */
public class GLTexFormat {
	public static final GLTexFormat
            RGBA = new GLTexFormat(GL_RGBA, 4), 
            RGB = new GLTexFormat(GL_RGB, 3),
            DEPTH = new GLTexFormat(GL_DEPTH_COMPONENT, 1);

    private final int glEnum;
    private final int nComponents;
    
    private GLTexFormat(int glEnum, int nComponents) {
        this.glEnum = glEnum;
        this.nComponents = nComponents;
    }

    protected int getGLEnum() {
        return glEnum;
    }

    protected int getNComponents() {
    	return nComponents;
    }
    
    public static GLTexFormat getFormat(int nComponents) {
        switch(nComponents) {
            case 1: return DEPTH;
            case 3: return RGB;
            case 4: return RGBA;
            default: throw new IllegalArgumentException();
        }
    }
}