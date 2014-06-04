package com.quew8.gutils.opengl.shaders;

import com.quew8.gutils.opengl.GLException;

/**
 * 
 * @author Quew8
 */
public class ShaderCompileException extends GLException {
    
    public ShaderCompileException(String infoLog, String shaderSource) {
        super("Error Compiling Shader: \n" + infoLog + "\nSource:\n" + withLineNumbers(shaderSource));
    }
    
    private static String withLineNumbers(String s) {
        String[] lines = s.split("\n");
        s = "";
        for (int i = 0; i < lines.length; i++) {
            s += Integer.toString(i + 1) + ": " + lines[i] + "\n";
        }
        return s;
    }
    
}
