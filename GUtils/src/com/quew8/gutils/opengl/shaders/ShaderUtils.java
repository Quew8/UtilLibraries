package com.quew8.gutils.opengl.shaders;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import com.quew8.gutils.BufferUtils;
import static com.quew8.gutils.opengl.OpenGL.*;

/**
 * 
 * @author Quew8
 */
public class ShaderUtils {
    private static int inUseShader = 0;
    
    private ShaderUtils() {
        
    }
    
    public static void setVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, int offset) {
        glVertexAttribPointer(index, size, type, normalized, stride, offset);
    }
    
    public static void vertexAttrib1f(int index, float f1) {
        glVertexAttrib1f(index, f1);
    }
    
    public static void vertexAttrib2f(int index, float f1, float f2) {
        glVertexAttrib2f(index, f1, f2);
    }
    
    public static void vertexAttrib3f(int index, float f1, float f2, float f3) {
        glVertexAttrib3f(index, f1, f2, f3);
    }
    
    public static void vertexAttrib4f(int index, float f1, float f2, float f3, float f4) {
        glVertexAttrib4f(index, f1, f2, f3, f4);
    }
    
    protected static void setInUseShader(int shaderId) {
        inUseShader = shaderId;
    }
    
    protected static void setInUseShader(ShaderProgram shader) {
        setInUseShader(shader.getId());
    }
    
    public static int getInUseShader() {
        return inUseShader;
    }
    
    /**
     * Sets OpenGL to use the fixed function pipeline.
     */
    public static void useFixedFunctions() {
        glUseProgram(0);
        setInUseShader(0);
    }
    
    public static void useShader(int shaderId) {
        glUseProgram(shaderId);
        setInUseShader(shaderId);
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
    
    public static void bindAttribIndex(String name, int index) {
        bindAttribIndex(inUseShader, name, index);
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
    
    public static int getUniformVarPos(String var) {
        return getUniformVarPos(inUseShader, var);
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
     * @param name
     * @param values 
     */
    public static void setUniformVarf(String name, float... values) {
        setUniformVarf(inUseShader, name, values);
    }
    
    public static void getUniformVarf(int programId, String name, FloatBuffer fb) {
        int loc = glGetUniformLocation(programId, name);
        glGetUniformfv(programId, loc, fb);
    }
    
    public static void getUniformVarf(String name, FloatBuffer fb) {
        getUniformVarf(inUseShader, name, fb);
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
     * 
     * @param name
     * @param data 
     */
    public static void setUniformMatrix(String name, FloatBuffer data) {
        setUniformMatrix(inUseShader, name, data);
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
    
    /**
     * 
     * @param name
     * @param values 
     */
    public static void setUniformVari(String name, int... values) {
        setUniformVari(inUseShader, name, values);
    }
    
    public static void getUniformVar(int programId, String name, IntBuffer fb) {
        int loc = glGetUniformLocation(programId, name);
        glGetUniformiv(programId, loc, fb);
    }
    
    public static void getUniformVar(String name, IntBuffer fb) {
        getUniformVar(inUseShader, name, fb);
    }
    
    /**
     * @return A string representing the maximum possible version of GLSL obtainable in the current OpenGl context.
     */
    public static String getMaxGLSLVersion() {
        return glGetString(GL_SHADING_LANGUAGE_VERSION);
    }
}
