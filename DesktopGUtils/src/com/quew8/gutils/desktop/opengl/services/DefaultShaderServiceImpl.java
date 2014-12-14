package com.quew8.gutils.desktop.opengl.services;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GLContext;

/**
 *
 * @author Quew8
 */
public class DefaultShaderServiceImpl implements ShaderServiceImpl {
    public static final DefaultShaderServiceImpl INSTANCE = new DefaultShaderServiceImpl();
    private static final int PROGRAM_INFO_LOG_MAX_LENGTH = 2048;
    private static final int SHADER_INFO_LOG_MAX_LENGTH = 2048;
    private static final int SHADER_SOURCE_MAX_LENGTH = 2048;
    
    @Override
    public boolean isApplicable() {
        //#TODO Work out this too.
        return true;
        //return GLContext.getCapabilities().OpenGL20;
    }

    @Override
    public int getPrecedence() {
        return 1;
    }
    
    @Override
    public void glAttachShader_P(int program, int shader) {
        GL20.glAttachShader(program, shader);
    }

    @Override
    public void glBindAttribLocation_P(int program, int index, String name) {
        GL20.glBindAttribLocation(program, index, name);
    }

    @Override
    public void glCompileShader_P(int shader) {
        GL20.glCompileShader(shader);
    }

    @Override
    public int glCreateProgram_P() {
        return GL20.glCreateProgram();
    }
    
    @Override
    public int glCreateShader_P(int type) {
        return GL20.glCreateShader(type);
    }

    @Override
    public void glDeleteProgram_P(int program) {
        GL20.glDeleteProgram(program);
    }

    @Override
    public void glDeleteShader_P(int shader) {
        GL20.glDeleteShader(shader);
    }

    @Override
    public void glDetachShader_P(int program, int shader) {
        GL20.glDetachShader(program, shader);
    }

    @Override
    public void glDisableVertexAttribArray_P(int index) {
        GL20.glDisableVertexAttribArray(index);
    }

    @Override
    public void glEnableVertexAttribArray_P(int index) {
        GL20.glEnableVertexAttribArray(index);
    }

    @Override
    public String glGetActiveAttrib_P(int program, int index, IntBuffer length, IntBuffer type) {
        return GL20.glGetActiveAttrib(program, index, length, type);
    }

    @Override
    public void glGetAttachedShaders_P(int program, IntBuffer count, IntBuffer shaders) {
        GL20.glGetAttachedShaders(program, count, shaders);
    }

    @Override
    public int glGetAttribLocation_P(int program, String name) {
        return GL20.glGetAttribLocation(program, name);
    }

    @Override
    public void glGetProgramiv_P(int program, int pname, IntBuffer params) {
        GL20.glGetProgram(program, pname, params);
    }

    @Override
    public String glGetProgramInfoLog_P(int program) {
        return GL20.glGetProgramInfoLog(program, PROGRAM_INFO_LOG_MAX_LENGTH);
    }

    @Override
    public void glGetShaderiv_P(int shader, int pname, IntBuffer params) {
        GL20.glGetShader(shader, pname, params);
    }

    @Override
    public String glGetShaderInfoLog_P(int shader) {
        return GL20.glGetShaderInfoLog(shader, SHADER_INFO_LOG_MAX_LENGTH);
    }

    @Override
    public String glGetShaderSource_P(int shader) {
        return GL20.glGetShaderSource(shader, SHADER_SOURCE_MAX_LENGTH);
    }

    @Override
    public void glGetUniformfv_P(int program, int location, FloatBuffer params) {
        GL20.glGetUniform(program, location, params);
    }

    @Override
    public void glGetUniformiv_P(int program, int location, IntBuffer params) {
        GL20.glGetUniform(program, location, params);
    }

    @Override
    public int glGetUniformLocation_P(int program, String name) {
        return GL20.glGetUniformLocation(program, name);
    }
    
    @Override
    public void glGetVertexAttribfv_P(int index, int pname, FloatBuffer params) {
        GL20.glGetVertexAttrib(index, pname, params);
    }

    @Override
    public void glGetVertexAttribiv_P(int index, int pname, IntBuffer params) {
        GL20.glGetVertexAttrib(index, pname, params);
    }

    @Override
    public boolean glIsProgram_P(int program) {
        return GL20.glIsProgram(program);
    }

    @Override
    public boolean glIsShader_P(int shader) {
        return GL20.glIsShader(shader);
    }

    @Override
    public void glLinkProgram_P(int program) {
        GL20.glLinkProgram(program);
    }

    @Override
    public void glShaderSource_P(int shader, String source) {
        GL20.glShaderSource(shader, source);
    }

    @Override
    public void glUniform1f_P(int location, float v1) {
        GL20.glUniform1f(location, v1);
    }

    @Override
    public void glUniform1fv_P(int location, FloatBuffer value) {
        GL20.glUniform1(location, value);
    }

    @Override
    public void glUniform1i_P(int location, int v1) {
        GL20.glUniform1i(location, v1);
    }

    @Override
    public void glUniform1iv_P(int location, IntBuffer value) {
        GL20.glUniform1(location, value);
    }

    @Override
    public void glUniform2f_P(int location, float v1, float v2) {
        GL20.glUniform2f(location, v1, v2);
    }

    @Override
    public void glUniform2fv_P(int location, FloatBuffer value) {
        GL20.glUniform2(location, value);
    }

    @Override
    public void glUniform2i_P(int location, int v1, int v2) {
        GL20.glUniform2i(location, v1, v2);
    }

    @Override
    public void glUniform2iv_P(int location, IntBuffer value) {
        GL20.glUniform2(location, value);
    }

    @Override
    public void glUniform3f_P(int location, float v1, float v2, float v3) {
        GL20.glUniform3f(location, v1, v2, v3);
    }

    @Override
    public void glUniform3fv_P(int location, FloatBuffer value) {
        GL20.glUniform3(location, value);
    }

    @Override
    public void glUniform3i_P(int location, int v1, int v2, int v3) {
        GL20.glUniform3i(location, v1, v2, v3);
    }

    @Override
    public void glUniform3iv_P(int location, IntBuffer value) {
        GL20.glUniform3(location, value);
    }

    @Override
    public void glUniform4f_P(int location, float v1, float v2, float v3, float v4) {
        GL20.glUniform4f(location, v1, v2, v3, v4);
    }

    @Override
    public void glUniform4fv_P(int location, FloatBuffer value) {
        GL20.glUniform4(location, value);
    }

    @Override
    public void glUniform4i_P(int location, int v1, int v2, int v3, int v4) {
        GL20.glUniform4i(location, v1, v2, v3, v4);
    }

    @Override
    public void glUniform4iv_P(int location, IntBuffer value) {
        GL20.glUniform4(location, value);
    }

    @Override
    public void glUniformMatrix2fv_P(int location, boolean transpose, FloatBuffer value) {
        GL20.glUniformMatrix2(location, transpose, value);
    }

    @Override
    public void glUniformMatrix3fv_P(int location, boolean transpose, FloatBuffer value) {
        GL20.glUniformMatrix3(location, transpose, value);
    }

    @Override
    public void glUniformMatrix4fv_P(int location, boolean transpose, FloatBuffer value) {
        GL20.glUniformMatrix4(location, transpose, value);
    }

    @Override
    public void glUseProgram_P(int program) {
        GL20.glUseProgram(program);
    }

    @Override
    public void glValidateProgram_P(int program) {
        GL20.glValidateProgram(program);
    }

    @Override
    public void glVertexAttrib1f_P(int index, float v1) {
        GL20.glVertexAttrib1f(index, v1);
    }

    @Override
    public void glVertexAttrib2f_P(int index, float v1, float v2) {
        GL20.glVertexAttrib2f(index, v1, v2);
    }

    @Override
    public void glVertexAttrib3f_P(int index, float v1, float v2, float v3) {
        GL20.glVertexAttrib3f(index, v1, v2, v3);
    }

    @Override
    public void glVertexAttrib4f_P(int index, float v1, float v2, float v3, float v4) {
        GL20.glVertexAttrib4f(index, v1, v2, v3, v4);
    }

    @Override
    public void glVertexAttribPointer_P(int index, int size, int type, boolean normalized, int stride, int bufferOffset) {
        GL20.glVertexAttribPointer(index, size, type, normalized, stride, bufferOffset);
    }

    @Override
    public void glVertexAttribPointer_P(int index, int size, boolean normalized, int stride, FloatBuffer buffer) {
        GL20.glVertexAttribPointer(index, size, normalized, stride, buffer);
    }

    @Override
    public void glVertexAttribPointer_P(int index, int size, boolean signed, boolean normalized, int stride, IntBuffer buffer) {
        if(!signed) {
            throw new UnsupportedOperationException();
        }
        GL20.glVertexAttribPointer(index, size, normalized, stride, buffer);
    }

    @Override
    public void glVertexAttribPointer_P(int index, int size, boolean signed, boolean normalized, int stride, ByteBuffer buffer) {
        GL20.glVertexAttribPointer(index, size, signed, normalized, stride, buffer);
    }
    
}
