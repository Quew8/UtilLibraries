package com.quew8.gutils.desktop.opengl.services;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 *
 * @author Quew8
 */
public class NoShaderServiceImpl implements ShaderServiceImpl {
    public static final NoShaderServiceImpl INSTANCE = new NoShaderServiceImpl();

    @Override
    public void glAttachShader_P(int program, int shader) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glBindAttribLocation_P(int program, int index, String name) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glCompileShader_P(int shader) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public int glCreateProgram_P() {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public int glCreateShader_P(int type) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glDeleteProgram_P(int program) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glDeleteShader_P(int shader) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glDetachShader_P(int program, int shader) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glDisableVertexAttribArray_P(int index) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glEnableVertexAttribArray_P(int index) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public String glGetActiveAttrib_P(int program, int index, IntBuffer length, IntBuffer type) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glGetAttachedShaders_P(int program, IntBuffer count, IntBuffer shaders) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public int glGetAttribLocation_P(int program, String name) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glGetProgramiv_P(int program, int pname, IntBuffer params) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public String glGetProgramInfoLog_P(int program) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glGetShaderiv_P(int shader, int pname, IntBuffer params) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public String glGetShaderInfoLog_P(int shader) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public String glGetShaderSource_P(int shader) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glGetUniformfv_P(int program, int location, FloatBuffer params) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glGetUniformiv_P(int program, int location, IntBuffer params) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public int glGetUniformLocation_P(int program, String name) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glGetVertexAttribfv_P(int index, int pname, FloatBuffer params) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glGetVertexAttribiv_P(int index, int pname, IntBuffer params) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public boolean glIsProgram_P(int program) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public boolean glIsShader_P(int shader) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glLinkProgram_P(int program) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glShaderSource_P(int shader, String source) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glUniform1f_P(int location, float v1) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glUniform1fv_P(int location, FloatBuffer value) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glUniform1i_P(int location, int v1) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glUniform1iv_P(int location, IntBuffer value) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glUniform2f_P(int location, float v1, float v2) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glUniform2fv_P(int location, FloatBuffer value) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glUniform2i_P(int location, int v1, int v2) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glUniform2iv_P(int location, IntBuffer value) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glUniform3f_P(int location, float v1, float v2, float v3) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glUniform3fv_P(int location, FloatBuffer value) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glUniform3i_P(int location, int v1, int v2, int v3) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glUniform3iv_P(int location, IntBuffer value) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glUniform4f_P(int location, float v1, float v2, float v3, float v4) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glUniform4fv_P(int location, FloatBuffer value) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glUniform4i_P(int location, int v1, int v2, int v3, int v4) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glUniform4iv_P(int location, IntBuffer value) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glUniformMatrix2fv_P(int location, boolean transpose, FloatBuffer value) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glUniformMatrix3fv_P(int location, boolean transpose, FloatBuffer value) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glUniformMatrix4fv_P(int location, boolean transpose, FloatBuffer value) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glUseProgram_P(int program) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glValidateProgram_P(int program) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glVertexAttrib1f_P(int index, float v1) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glVertexAttrib2f_P(int index, float v1, float v2) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glVertexAttrib3f_P(int index, float v1, float v2, float v3) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glVertexAttrib4f_P(int index, float v1, float v2, float v3, float v4) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glVertexAttribPointer_P(int index, int size, int type, boolean normalized, int stride, int bufferOffset) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glVertexAttribPointer_P(int index, int size, boolean normalized, int stride, FloatBuffer buffer) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glVertexAttribPointer_P(int index, int size, boolean signed, boolean normalized, int stride, IntBuffer buffer) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public void glVertexAttribPointer_P(int index, int size, boolean signed, boolean normalized, int stride, ByteBuffer buffer) {
        throw new UnsupportedOperationException("There is no support for shaders in this implementation");
    }

    @Override
    public boolean isApplicable() {
        return true;
    }

    @Override
    public int getPrecedence() {
        return -1;
    }
    
}
