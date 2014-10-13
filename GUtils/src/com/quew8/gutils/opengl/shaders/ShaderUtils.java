package com.quew8.gutils.opengl.shaders;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import com.quew8.gutils.BufferUtils;
import com.quew8.gutils.Colour;
import static com.quew8.gutils.opengl.OpenGL.*;

/**
 * 
 * @author Quew8
 */
public class ShaderUtils {
    
    private ShaderUtils() {
        
    }
    
    public static void useFixedFunctions() {
        glUseProgram(0);
    }
    
    public static void useShader(int shaderId) {
        glUseProgram(shaderId);
    }
    
    public static void setAttribIndicesEnabled(boolean enabled, int... indices) {
        if(enabled) {
            for(int i = 0; i < indices.length; i++) {
                glEnableVertexAttribArray(indices[i]);
            }
        } else {
            for(int i = 0; i < indices.length; i++) {
                glDisableVertexAttribArray(indices[i]);
            }
        }
    }
    
    public static void bindAttribIndex(int programId, String name, int index) {
        glBindAttribLocation(programId, index, name);
    }
    
    /**
     * Returns the position of the specified uniform variable.
     * @param programId 
     * @param var The name of the variable.
     * @return  The position of the variable.
     */
    public static int getUniformVarPos(int programId, String var) {
        int i = glGetUniformLocation(programId, var);
        return i;
    }
    
    /**
     * Sets the value of the specified uniform variable to that given.
     * @param programId The id of the program containing the variable. 
     * @param name The name of the variable.
     * @param values The value or values to be set.
     */
    public static void setUniformVarf(int programId, String name, float... values) {
        int loc = glGetUniformLocation(programId, name);
        switch(values.length) {
            case 1: {glUniform1f(loc, values[0]); break;}
            case 2: {glUniform2f(loc, values[0], values[1]); break;}
            case 3: {glUniform3f(loc, values[0], values[1], values[2]); break;}
            case 4: {glUniform4f(loc, values[0], values[1], values[2], values[3]); break;}
            default: {
                FloatBuffer buff = BufferUtils.createFloatBuffer(values.length);
                buff.put(values);
                buff.rewind();
                glUniform1fv(loc, buff);
            }
        }
    }
    
    /**
     * 
     * @param programId
     * @param name
     * @param colour 
     */
    public static void setUniformColour(int programId, String name, Colour colour) {
        setUniformVarf(programId, name, colour.getRed(), colour.getGreen(), colour.getBlue(), colour.getAlpha());
    }
    
    /**
     * 
     * @param programId
     * @param name
     * @param fb 
     */
    public static void getUniformVarf(int programId, String name, FloatBuffer fb) {
        int loc = glGetUniformLocation(programId, name);
        glGetUniformfv(programId, loc, fb);
    }
    
    /**
     * 
     * @param programId
     * @param name
     * @param data 
     */
    public static void setUniformMatrix(int programId, String name, FloatBuffer data) {
        int loc = glGetUniformLocation(programId, name);
        glUniformMatrix4fv(loc, false, data);
    }
    
    /**
     * Sets the value of the specified uniform variable to that given.
     * @param programId The id of the program containing the variable. 
     * @param name The name of the variable.
     * @param values The value or values to be set.
     */
    public static void setUniformVari(int programId, String name, int... values) {
        int loc = glGetUniformLocation(programId, name);
        switch(values.length) {
            case 1: {glUniform1i(loc, values[0]); break;}
            case 2: {glUniform2i(loc, values[0], values[1]); break;}
            case 3: {glUniform3i(loc, values[0], values[1], values[2]); break;}
            case 4: {glUniform4i(loc, values[0], values[1], values[2], values[3]); break;}
            default: {
                IntBuffer buff = BufferUtils.createIntBuffer(values.length);
                buff.put(values);
                buff.rewind();
                glUniform1iv(loc, buff);
            }
        }
    }
    
    public static void getUniformVar(int programId, String name, IntBuffer fb) {
        int loc = glGetUniformLocation(programId, name);
        glGetUniformiv(programId, loc, fb);
    }
    
    /**
     * @return A string representing the maximum possible version of GLSL obtainable in the current OpenGl context.
     */
    public static String getMaxGLSLVersion() {
        return glGetString(GL_SHADING_LANGUAGE_VERSION);
    }
}
