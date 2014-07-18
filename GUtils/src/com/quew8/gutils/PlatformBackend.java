package com.quew8.gutils;

import com.quew8.gutils.debug.DebugLogger;
import java.io.File;
import java.io.InputStream;
import java.nio.FloatBuffer;

import com.quew8.gutils.debug.LogLevel;
import com.quew8.gutils.debug.LogStream;
import com.quew8.gutils.opengl.texture.LoadedImage;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 *
 * @author Quew8
 * @param <T>
 */
public abstract class PlatformBackend<T extends LoadedImage> {
    public static final int
            DESKTOP_CONSTANT = 0,
            ANDROID_CONSTANT = 1;
    public static boolean debug = false;
    protected static boolean initialized = false;
    public static PlatformBackend<?> backend;
    
    public static void setDebug(boolean debug) {
        PlatformBackend.debug = debug;
    	if(debug && !(backend instanceof DebugBackend)) {
            backend = DebugBackend.getDebugBackend(backend);
    	} else if(!debug && (backend instanceof DebugBackend)) {
            backend = ((DebugBackend<?>) backend).getImpl();
    	}
    }
    
    public static void setBackend(PlatformBackend<?> backend) {
        PlatformBackend.backend = backend;
        if(debug) {
            setDebug(true);
        }
        DebugLogger.broadcast(LogLevel.VERBOSE, "New Backend: " + backend.toString());
        initialized = true;
        DebugLogger.onInit();
    }
    
    public abstract void glActiveTexture_P(int texture);
    
    public abstract void glAttachShader_P(int program, int shader);
    
    public abstract void glBindAttribLocation_P(int program, int index, String name);
    
    public abstract void glBindBuffer_P(int target, int buffer);
    
    public abstract void glBindFramebuffer_P(int target, int framebuffer);
    
    public abstract void glBindRenderbuffer_P(int target, int renderbuffer);
    
    public abstract void glBindTexture_P(int target, int texture);
    
    public abstract void glBlendColor_P(float red, float green, float blue, float alpha);
    
    public abstract void glBlendEquation_P(int mode);
    
    public abstract void glBlendFunc_P(int sFactor, int dFactor);
    
    public abstract void glBufferData_P(int target, FloatBuffer data, int usage);
    
    public abstract void glBufferData_P(int target, IntBuffer data, int usage);
    
    public abstract void glBufferData_P(int target, ByteBuffer data, int usage);
    
    public abstract void glBufferSubData_P(int target, int offset, FloatBuffer data);
    
    public abstract void glBufferSubData_P(int target, int offset, IntBuffer data);
    
    public abstract void glBufferSubData_P(int target, int offset, ByteBuffer data);
    
    public abstract int glCheckFramebufferStatus_P(int target);
    
    public abstract void glClear_P(int mask);
    
    public abstract void glClearColor_P(float red, float green, float blue, float alpha);
    
    public abstract void glClearDepth_P(float depth);
    
    public abstract void glClearStencil_P(int stencil);
    
    public abstract void glColorMask_P(boolean red, boolean green, boolean blue, boolean alpha);
    
    public abstract void glCompileShader_P(int shader);
    
    public abstract void glCompressedTexImage2D_P(int target, int level, int internalFormat, int width, int height, int border, ByteBuffer data);
    
    public abstract void glCompressedTexSubImage2D_P(int target, int level, int xOffset, int yOffset, int width, int height, int format, ByteBuffer data);
    
    public abstract void glCopyTexImage2D_P(int target, int level, int internalForamt, int x, int y, int width, int height, int border);
    
    public abstract void glCopyTexSubImage2D_P(int target, int level, int xOffset, int yOffset, int x, int y, int width, int height);
    
    public abstract int glCreateProgram_P();
    
    public abstract int glCreateShader_P(int type);
    
    public abstract void glCullFace_P(int mode);
    
    public abstract void glDeleteBuffers_P(IntBuffer buffers);
    
    public abstract void glDeleteFramebuffers_P(IntBuffer buffers);
    
    public abstract void glDeleteProgram_P(int program);
    
    public abstract void glDeleteRenderbuffers_P(IntBuffer buffers);
    
    public abstract void glDeleteShader_P(int shader);
    
    public abstract void glDeleteTextures_P(IntBuffer arg1);
    
    public abstract void glDepthFunc_P(int func);
    
    public abstract void glDepthMask_P(boolean flag);
    
    public abstract void glDepthRange_P(float nearVal, float farVal);
    
    public abstract void glDetachShader_P(int program, int shader);
    
    public abstract void glDisable_P(int cap);
    
    public abstract void glDisableVertexAttribArray_P(int index);
    
    public abstract void glDrawArrays_P(int mode, int first, int count);
    
    public abstract void glDrawElements_P(int mode, int count, int type, int bufferOffset);
    
    public abstract void glDrawElements_P(int mode, IntBuffer buffer);
    
    public abstract void glDrawElements_P(int mode, ByteBuffer buffer);
    
    public abstract void glEnable_P(int cap);
    
    public abstract void glEnableVertexAttribArray_P(int index);
    
    public abstract void glFinish_P();
    
    public abstract void glFlush_P();
    
    public abstract void glFramebufferRenderbuffer_P(int target, int attachment, int renderbufferTarget, int renderbuffer);
    
    public abstract void glFramebufferTexture2D_P(int target, int attachment, int texTarget, int texture, int level);
    
    public abstract void glFrontFace_P(int mode);
    
    public abstract void glGenBuffers_P(IntBuffer buffers);
    
    public abstract void glGenFramebuffers_P(IntBuffer buffers);
    
    public abstract void glGenRenderbuffers_P(IntBuffer buffers);
    
    public abstract void glGenTextures_P(IntBuffer buffers);
    
    public abstract String glGetActiveAttrib_P(int program, int index, IntBuffer length, IntBuffer type);
    
    public abstract void glGetAttachedShaders_P(int program, IntBuffer count, IntBuffer shaders);
    
    public abstract int glGetAttribLocation_P(int program, String name);
    
    public abstract void glGetBufferParameteriv_P(int target, int value, IntBuffer data);
    
    public abstract void glGetBooleanv_P(int pname, ByteBuffer params);
    
    public abstract int glGetError_P();
    
    public abstract void glGetFloatv_P(int pname, FloatBuffer params);
    
    public abstract void glGetFramebufferAttachmentParameteriv_P(int target, int attatchment, int pname, IntBuffer params);
    
    public abstract void glGetIntegerv_P(int pname, IntBuffer params);
    
    public abstract void glGetProgramiv_P(int program, int pname, IntBuffer params);
    
    public abstract String glGetProgramInfoLog_P(int program);
    
    public abstract void glGetRenderbufferParameteriv_P(int target, int pname, IntBuffer params);
    
    public abstract void glGetShaderiv_P(int shader, int pname, IntBuffer params);
    
    public abstract String glGetShaderInfoLog_P(int shader);
    
    public abstract String glGetShaderSource_P(int shader);
    
    public abstract String glGetString_P(int pname);
    
    public abstract void glGetTexParameterfv_P(int target, int pname, FloatBuffer params);
    
    public abstract void glGetTexParameteriv_P(int target, int pname, IntBuffer params);
    
    public abstract void glGetUniformfv_P(int program, int location, FloatBuffer params);
    
    public abstract void glGetUniformiv_P(int program, int location, IntBuffer params);
    
    public abstract int glGetUniformLocation_P(int program, String name);
    
    public abstract void glGetVertexAttribfv_P(int index, int pname, FloatBuffer params);
    
    public abstract void glGetVertexAttribiv_P(int index, int pname, IntBuffer params);
    
    public abstract void glHint_P(int target, int mode);
    
    public abstract boolean glIsBuffer_P(int buffer);
    
    public abstract boolean glIsEnabled_P(int cap);
    
    public abstract boolean glIsFramebuffer_P(int framebuffer);
    
    public abstract boolean glIsProgram_P(int program);
    
    public abstract boolean glIsRenderbuffer_P(int renderbuffer);
    
    public abstract boolean glIsShader_P(int shader);
    
    public abstract boolean glIsTexture_P(int texture);
    
    public abstract void glLineWidth_P(float width);
    
    public abstract void glLinkProgram_P(int program);
    
    public abstract void glPixelStorei_P(int pname, int param);
    
    public abstract void glPolygonOffset_P(float factor, float units);
    
    public abstract void glReadPixels_P(int x, int y, int width, int height, int format, int type, FloatBuffer data);
    
    public abstract void glReadPixels_P(int x, int y, int width, int height, int format, int type, IntBuffer data);
    
    public abstract void glReadPixels_P(int x, int y, int width, int height, int format, int type, ByteBuffer data);
    
    public abstract void glRenderbufferStorage_P(int target, int internalFormat, int width, int height);
    
    public abstract void glSampleCoverage_P(float value, boolean invert);
    
    public abstract void glScissor_P(int x, int y, int width, int height);
    
    public abstract void glShaderSource_P(int shader, String source);
    
    public abstract void glStencilFunc_P(int func, int ref, int mask);
    
    public abstract void glStencilMask_P(int mask);
    
    public abstract void glStencilOp_P(int sfail, int dpfail, int dppass);
    
    public abstract void glTexImage2D_P(int target, int level, int internalFormat, int width, int height, int border, int format, int type, FloatBuffer data);
    
    public abstract void glTexImage2D_P(int target, int level, int internalFormat, int width, int height, int border, int format, int type, IntBuffer data);
    
    public abstract void glTexImage2D_P(int target, int level, int internalFormat, int width, int height, int border, int format, int type, ByteBuffer data);
    
    public abstract void glTexParameterf_P(int target, int pname, float param);
    
    public abstract void glTexParameterfv_P(int target, int pname, FloatBuffer param);
    
    public abstract void glTexParameteri_P(int target, int pname, int param);
    
    public abstract void glTexParameteriv_P(int target, int pname, IntBuffer param);
    
    public abstract void glTexSubImage2D_P(int target, int level, int xOffset, int yOffset, int width, int height, int format, int type, FloatBuffer data);
    
    public abstract void glTexSubImage2D_P(int target, int level, int xOffset, int yOffset, int width, int height, int format, int type, IntBuffer data);
    
    public abstract void glTexSubImage2D_P(int target, int level, int xOffset, int yOffset, int width, int height, int format, int type, ByteBuffer data);
    
    public abstract void glUniform1f_P(int location, float v1);
    
    public abstract void glUniform1fv_P(int location, FloatBuffer value);
    
    public abstract void glUniform1i_P(int location, int v1);
    
    public abstract void glUniform1iv_P(int location, IntBuffer value);
    
    public abstract void glUniform2f_P(int location, float v1, float v2);
    
    public abstract void glUniform2fv_P(int location, FloatBuffer value);
    
    public abstract void glUniform2i_P(int location, int v1, int v2);
    
    public abstract void glUniform2iv_P(int location, IntBuffer value);
    
    public abstract void glUniform3f_P(int location, float v1, float v2, float v3);
    
    public abstract void glUniform3fv_P(int location, FloatBuffer value);
    
    public abstract void glUniform3i_P(int location, int v1, int v2, int v3);
    
    public abstract void glUniform3iv_P(int location, IntBuffer value);
    
    public abstract void glUniform4f_P(int location, float v1, float v2, float v3, float v4);
    
    public abstract void glUniform4fv_P(int location, FloatBuffer value);
    
    public abstract void glUniform4i_P(int location, int v1, int v2, int v3, int v4);
    
    public abstract void glUniform4iv_P(int location, IntBuffer value);
    
    public abstract void glUniformMatrix2fv_P(int location, boolean transpose, FloatBuffer value);
    
    public abstract void glUniformMatrix3fv_P(int location, boolean transpose, FloatBuffer value);
    
    public abstract void glUniformMatrix4fv_P(int location, boolean transpose, FloatBuffer value);
    
    public abstract void glUseProgram_P(int program);
    
    public abstract void glValidateProgram_P(int program);
    
    public abstract void glVertexAttrib1f_P(int index, float v1);
    
    public abstract void glVertexAttrib2f_P(int index, float v1, float v2);
    
    public abstract void glVertexAttrib3f_P(int index, float v1, float v2, float v3);
    
    public abstract void glVertexAttrib4f_P(int index, float v1, float v2, float v3, float v4);
    
    public abstract void glVertexAttribPointer_P(int index, int size, int type, boolean normalized, int stride, int bufferOffset);
    
    public abstract void glVertexAttribPointer_P(int index, int size, boolean normalized, int stride, FloatBuffer buffer);
    
    public abstract void glVertexAttribPointer_P(int index, int size, boolean unsigned, boolean normalized, int stride, IntBuffer buffer);
    
    public abstract void glVertexAttribPointer_P(int index, int size, boolean unsigned, boolean normalized, int stride, ByteBuffer buffer);
    
    public abstract void glViewport_P(int x, int y, int width, int height);
    
    public abstract LogStream getLogStream_P(LogLevel level);
    
    public abstract File getLogFile_P(String filename);
    
    public abstract long getTimeMillis_P();
    
    public abstract T loadImage_P(InputStream is, boolean flip);
    
    @SuppressWarnings("unchecked")
    public void fillTexture_P(LoadedImage img, int destFormat, int texWidth, int texHeight) {
    	fillTypeTexture_P((T) img, destFormat, texWidth, texHeight);
    }
    
    public abstract void fillTypeTexture_P(T img, int destFormat, int texWidth, int texHeight);
    
    @SuppressWarnings("unchecked")
    public void fillSubTexture_P(int xPos, int yPos, LoadedImage img) {
    	fillTypeSubTexture_P(xPos, yPos, (T) img);
    }
    
    public abstract void fillTypeSubTexture_P(int xPos, int yPos, T img);
    
    public abstract int getPlatformConstant();
}