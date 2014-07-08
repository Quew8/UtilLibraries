package com.quew8.gutils.opengl.shaders;

import com.quew8.gutils.opengl.GLException;

/**
 *
 * @author Quew8
 */
public class ProgramLinkException extends GLException {

    public ProgramLinkException(String infoLog) {
        super("Error Linking Shader: " + infoLog);
    }
    
}
