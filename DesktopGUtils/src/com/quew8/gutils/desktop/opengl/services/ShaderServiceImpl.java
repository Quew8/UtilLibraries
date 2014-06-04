package com.quew8.gutils.desktop.opengl.services;

import com.quew8.gutils.services.ServiceImpl;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 *
 * @author Quew8
 */
public interface ShaderServiceImpl extends ServiceImpl {
    
    public void glAttachShader_P(int program, int shader);

    public void glBindAttribLocation_P(int program, int index, String name);

    public void glCompileShader_P(int shader);

    public int glCreateProgram_P();

    public int glCreateShader_P(int type);

    public void glDeleteProgram_P(int program);

    public void glDeleteShader_P(int shader);

    public void glDetachShader_P(int program, int shader);

    public void glDisableVertexAttribArray_P(int index);

    public void glEnableVertexAttribArray_P(int index);

    public String glGetActiveAttrib_P(int program, int index, IntBuffer length, IntBuffer type);

    public void glGetAttachedShaders_P(int program, IntBuffer count, IntBuffer shaders);

    public int glGetAttribLocation_P(int program, String name);

    public void glGetProgramiv_P(int program, int pname, IntBuffer params);

    public String glGetProgramInfoLog_P(int program);

    public void glGetShaderiv_P(int shader, int pname, IntBuffer params);

    public String glGetShaderInfoLog_P(int shader);

    public String glGetShaderSource_P(int shader);

    public void glGetUniformfv_P(int program, int location, FloatBuffer params);

    public void glGetUniformiv_P(int program, int location, IntBuffer params);

    public int glGetUniformLocation_P(int program, String name);
    
    public void glGetVertexAttribfv_P(int index, int pname, FloatBuffer params);

    public void glGetVertexAttribiv_P(int index, int pname, IntBuffer params);

    public boolean glIsProgram_P(int program);

    public boolean glIsShader_P(int shader);

    public void glLinkProgram_P(int program);

    public void glShaderSource_P(int shader, String source);
    
    public void glUniform1f_P(int location, float v1);

    public void glUniform1fv_P(int location, FloatBuffer value);

    public void glUniform1i_P(int location, int v1);

    public void glUniform1iv_P(int location, IntBuffer value);

    public void glUniform2f_P(int location, float v1, float v2);

    public void glUniform2fv_P(int location, FloatBuffer value);

    public void glUniform2i_P(int location, int v1, int v2);

    public void glUniform2iv_P(int location, IntBuffer value);

    public void glUniform3f_P(int location, float v1, float v2, float v3);

    public void glUniform3fv_P(int location, FloatBuffer value);

    public void glUniform3i_P(int location, int v1, int v2, int v3);

    public void glUniform3iv_P(int location, IntBuffer value);

    public void glUniform4f_P(int location, float v1, float v2, float v3, float v4);

    public void glUniform4fv_P(int location, FloatBuffer value);

    public void glUniform4i_P(int location, int v1, int v2, int v3, int v4);

    public void glUniform4iv_P(int location, IntBuffer value);

    public void glUniformMatrix2fv_P(int location, boolean transpose, FloatBuffer value);

    public void glUniformMatrix3fv_P(int location, boolean transpose, FloatBuffer value);

    public void glUniformMatrix4fv_P(int location, boolean transpose, FloatBuffer value);

    public void glUseProgram_P(int program);

    public void glValidateProgram_P(int program);

    public void glVertexAttrib1f_P(int index, float v1);

    public void glVertexAttrib2f_P(int index, float v1, float v2);

    public void glVertexAttrib3f_P(int index, float v1, float v2, float v3);

    public void glVertexAttrib4f_P(int index, float v1, float v2, float v3, float v4);

    public void glVertexAttribPointer_P(int index, int size, int type, boolean normalized, int stride, int bufferOffset);

    public void glVertexAttribPointer_P(int index, int size, boolean normalized, int stride, FloatBuffer buffer);
    
    public void glVertexAttribPointer_P(int index, int size, boolean signed, boolean normalized, int stride, IntBuffer buffer);
    
    public void glVertexAttribPointer_P(int index, int size, boolean signed, boolean normalized, int stride, ByteBuffer buffer);
}
