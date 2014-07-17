package com.quew8.gutils.desktop;

import com.quew8.gutils.PlatformBackend;
import com.quew8.gutils.debug.LogLevel;
import com.quew8.gutils.debug.LogStream;
import com.quew8.gutils.desktop.opengl.services.ShaderServiceImpl;
import com.quew8.gutils.desktop.opengl.services.FramebufferServiceImpl;
import com.quew8.gutils.desktop.opengl.services.NoFramebufferServiceImpl;
import com.quew8.gutils.desktop.opengl.services.NoShaderServiceImpl;
import com.quew8.gutils.services.ServiceImplLoader;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;

/**
 *
 * @author Quew8
 */
public class DesktopBackend extends PlatformBackend<DesktopLoadedImage> {
    private static final LogStream 
            DEFAULT_STREAM = new LogStream() {
                @Override
                public void log(String log, String msg) {
                    System.out.println(log + " :: " + msg);
                }
            },
            ERROR_STREAM = new LogStream() {
                @Override
                public void log(String log, String msg) {
                    System.err.println(log + " :: " + msg);
                }
            };
    
    private final ShaderServiceImpl shaderService;
    private final FramebufferServiceImpl framebufferService;
    
    public DesktopBackend(ShaderServiceImpl shaderService, FramebufferServiceImpl framebufferService) {
        this.shaderService = shaderService;
        this.framebufferService = framebufferService;
    }
    
    public DesktopBackend(URL[] urls) {
        this(
                new ServiceImplLoader<ShaderServiceImpl>(
                        ShaderServiceImpl.class, 
                        urls,
                        NoShaderServiceImpl.INSTANCE
                ).getImplementationNoThrow(),
                new ServiceImplLoader<FramebufferServiceImpl>(
                        FramebufferServiceImpl.class, 
                        urls,
                        NoFramebufferServiceImpl.INSTANCE
                ).getImplementationNoThrow()
        );
    }

    @Override
    public void glActiveTexture_P(int texture) {
        GL13.glActiveTexture(texture);
    }

    @Override
    public void glAttachShader_P(int program, int shader) {
        shaderService.glAttachShader_P(program, shader);
    }

    @Override
    public void glBindAttribLocation_P(int program, int index, String name) {
        shaderService.glBindAttribLocation_P(program, index, name);
    }

    @Override
    public void glBindBuffer_P(int target, int buffer) {
        GL15.glBindBuffer(target, buffer);
    }

    @Override
    public void glBindFramebuffer_P(int target, int framebuffer) {
        framebufferService.glBindFramebuffer_P(target, framebuffer);
    }

    @Override
    public void glBindRenderbuffer_P(int target, int renderbuffer) {
        framebufferService.glBindRenderbuffer_P(target, renderbuffer);
    }

    @Override
    public void glBindTexture_P(int target, int texture) {
        GL11.glBindTexture(target, texture);
    }

    @Override
    public void glBlendColor_P(float red, float green, float blue, float alpha) {
        GL14.glBlendColor(red, green, blue, alpha);
    }

    @Override
    public void glBlendEquation_P(int mode) {
        GL14.glBlendEquation(mode);
    }

    @Override
    public void glBlendFunc_P(int sFactor, int dFactor) {
        GL11.glBlendFunc(sFactor, dFactor);
    }

    @Override
    public void glBufferData_P(int target, FloatBuffer data, int usage) {
        GL15.glBufferData(target, data, usage);
    }

    @Override
    public void glBufferData_P(int target, IntBuffer data, int usage) {
        GL15.glBufferData(target, data, usage);
    }

    @Override
    public void glBufferData_P(int target, ByteBuffer data, int usage) {
        GL15.glBufferData(target, data, usage);
    }

    @Override
    public void glBufferSubData_P(int target, int offset, FloatBuffer data) {
        GL15.glBufferSubData(target, offset, data);
    }

    @Override
    public void glBufferSubData_P(int target, int offset, IntBuffer data) {
        GL15.glBufferSubData(target, offset, data);
    }

    @Override
    public void glBufferSubData_P(int target, int offset, ByteBuffer data) {
        GL15.glBufferSubData(target, offset, data);
    }

    @Override
    public int glCheckFramebufferStatus_P(int target) {
        return framebufferService.glCheckFramebufferStatus_P(target);
    }

    @Override
    public void glClear_P(int mask) {
        GL11.glClear(mask);
    }

    @Override
    public void glClearColor_P(float red, float green, float blue, float alpha) {
        GL11.glClearColor(red, green, blue, alpha);
    }

    @Override
    public void glClearDepth_P(float depth) {
        GL11.glClearDepth(depth);
    }

    @Override
    public void glClearStencil_P(int stencil) {
        GL11.glClearStencil(stencil);
    }

    @Override
    public void glColorMask_P(boolean red, boolean green, boolean blue, boolean alpha) {
        GL11.glColorMask(red, green, blue, alpha);
    }

    @Override
    public void glCompileShader_P(int shader) {
        shaderService.glCompileShader_P(shader);
    }
    
    @Override
    public void glCompressedTexImage2D_P(int target, int level, int internalFormat, int width, int height, int border, ByteBuffer data) {
        GL13.glCompressedTexImage2D(target, level, internalFormat, width, height, border, data);
    }
    
    @Override
    public void glCompressedTexSubImage2D_P(int target, int level, int xOffset, int yOffset, int width, int height, int format, ByteBuffer data) {
        GL13.glCompressedTexSubImage2D(target, level, xOffset, yOffset, width, height, format, data);
    }

    @Override
    public void glCopyTexImage2D_P(int target, int level, int internalForamt, int x, int y, int width, int height, int border) {
        GL11.glCopyTexImage2D(target, level, internalForamt, x, y, width, height, border);
    }

    @Override
    public void glCopyTexSubImage2D_P(int target, int level, int xOffset, int yOffset, int x, int y, int width, int height) {
        GL11.glCopyTexSubImage2D(target, level, xOffset, yOffset, x, y, width, height);
    }

    @Override
    public int glCreateProgram_P() {
        return shaderService.glCreateProgram_P(); 
    }

    @Override
    public int glCreateShader_P(int type) {
        return shaderService.glCreateShader_P(type);
    }

    @Override
    public void glCullFace_P(int mode) {
        GL11.glCullFace(mode);
    }

    @Override
    public void glDeleteBuffers_P(IntBuffer buffers) {
        GL15.glDeleteBuffers(buffers);
    }

    @Override
    public void glDeleteFramebuffers_P(IntBuffer buffers) {
        framebufferService.glDeleteFramebuffers_P(buffers);
    }

    @Override
    public void glDeleteProgram_P(int program) {
        shaderService.glDeleteProgram_P(program);
    }

    @Override
    public void glDeleteRenderbuffers_P(IntBuffer buffers) {
        framebufferService.glDeleteRenderbuffers_P(buffers);
    }

    @Override
    public void glDeleteShader_P(int shader) {
        shaderService.glDeleteShader_P(shader);
    }

    @Override
    public void glDeleteTextures_P(IntBuffer arg1) {
        GL11.glDeleteTextures(arg1);
    }

    @Override
    public void glDepthFunc_P(int func) {
        GL11.glDepthFunc(func);
    }

    @Override
    public void glDepthMask_P(boolean flag) {
        GL11.glDepthMask(flag);
    }

    @Override
    public void glDepthRange_P(float nearVal, float farVal) {
        GL11.glDepthRange(nearVal, farVal);
    }

    @Override
    public void glDetachShader_P(int program, int shader) {
        shaderService.glDetachShader_P(program, shader);
    }

    @Override
    public void glDisable_P(int cap) {
        GL11.glDisable(cap);
    }

    @Override
    public void glDisableVertexAttribArray_P(int index) {
        shaderService.glDisableVertexAttribArray_P(index);
    }

    @Override
    public void glDrawArrays_P(int mode, int first, int count) {
        GL11.glDrawArrays(mode, first, count);
    }

    @Override
    public void glDrawElements_P(int mode, int count, int type, int bufferOffset) {
        GL11.glDrawElements(mode, count, type, bufferOffset);
    }

    @Override
    public void glDrawElements_P(int mode, IntBuffer buffer) {
        GL11.glDrawElements(mode, buffer);
    }

    @Override
    public void glDrawElements_P(int mode, ByteBuffer buffer) {
        GL11.glDrawElements(mode, buffer);
    }

    @Override
    public void glEnable_P(int cap) {
        GL11.glEnable(cap);
    }

    @Override
    public void glEnableVertexAttribArray_P(int index) {
        shaderService.glEnableVertexAttribArray_P(index);
    }

    @Override
    public void glFinish_P() {
        GL11.glFinish();
    }

    @Override
    public void glFlush_P() {
        GL11.glFlush();
    }

    @Override
    public void glFramebufferRenderbuffer_P(int target, int attachment, int renderbufferTarget, int renderbuffer) {
        framebufferService.glFramebufferRenderbuffer_P(target, attachment, renderbufferTarget, renderbuffer);
    }

    @Override
    public void glFramebufferTexture2D_P(int target, int attachment, int texTarget, int texture, int level) {
        framebufferService.glFramebufferTexture2D_P(target, attachment, texTarget, texture, level);
    }

    @Override
    public void glFrontFace_P(int mode) {
        GL11.glFrontFace(mode);
    }

    @Override
    public void glGenBuffers_P(IntBuffer buffers) {
        GL15.glGenBuffers(buffers);
    }

    @Override
    public void glGenFramebuffers_P(IntBuffer buffers) {
        framebufferService.glGenFramebuffers_P(buffers);
    }
    @Override
    public void glGenRenderbuffers_P(IntBuffer buffers) {
        framebufferService.glGenRenderbuffers_P(buffers);
    }

    @Override
    public void glGenTextures_P(IntBuffer buffers) {
        GL11.glGenTextures(buffers);
    }

    @Override
    public String glGetActiveAttrib_P(int program, int index, IntBuffer length, IntBuffer type) {
        return shaderService.glGetActiveAttrib_P(program, index, length, type);
    }

    @Override
    public void glGetAttachedShaders_P(int program, IntBuffer count, IntBuffer shaders) {
        shaderService.glGetAttachedShaders_P(program, count, shaders);
    }

    @Override
    public int glGetAttribLocation_P(int program, String name) {
        return shaderService.glGetAttribLocation_P(program, name);
    }

    @Override
    public void glGetBooleanv_P(int pname, ByteBuffer params) {
        GL11.glGetBoolean(pname, params);
    }

    @Override
    public void glGetBufferParameteriv_P(int target, int value, IntBuffer data) {
        GL15.glGetBufferParameter(target, value, data);
    }
    
    @Override
    public int glGetError_P() {
        return GL11.glGetError();
    }

    @Override
    public void glGetFloatv_P(int pname, FloatBuffer params) {
        GL11.glGetFloat(pname, params);
    }

    @Override
    public void glGetFramebufferAttachmentParameteriv_P(int target, int attatchment, int pname, IntBuffer params) {
        framebufferService.glGetFramebufferAttachmentParameteriv_P(target, attatchment, pname, params);
    }

    @Override
    public void glGetIntegerv_P(int pname, IntBuffer params) {
        GL11.glGetInteger(pname, params);
    }

    @Override
    public void glGetProgramiv_P(int program, int pname, IntBuffer params) {
        shaderService.glGetProgramiv_P(program, pname, params);
    }

    @Override
    public String glGetProgramInfoLog_P(int program) {
        return shaderService.glGetProgramInfoLog_P(program);
    }

    @Override
    public void glGetRenderbufferParameteriv_P(int target, int pname, IntBuffer params) {
        framebufferService.glGetRenderbufferParameteriv_P(target, pname, params);
    }

    @Override
    public void glGetShaderiv_P(int shader, int pname, IntBuffer params) {
        shaderService.glGetShaderiv_P(shader, pname, params);
    }

    @Override
    public String glGetShaderInfoLog_P(int shader) {
        return shaderService.glGetShaderInfoLog_P(shader);
    }

    @Override
    public String glGetShaderSource_P(int shader) {
        return shaderService.glGetShaderSource_P(shader);
    }

    @Override
    public String glGetString_P(int pname) {
        return GL11.glGetString(pname);
    }

    @Override
    public void glGetTexParameterfv_P(int target, int pname, FloatBuffer params) {
        GL11.glGetTexParameter(target, pname, params);
    }

    @Override
    public void glGetTexParameteriv_P(int target, int pname, IntBuffer params) {
        GL11.glGetTexParameter(target, pname, params);
    }

    @Override
    public void glGetUniformfv_P(int program, int location, FloatBuffer params) {
        shaderService.glGetUniformfv_P(program, location, params);
    }

    @Override
    public void glGetUniformiv_P(int program, int location, IntBuffer params) {
        shaderService.glGetUniformiv_P(program, location, params);
    }
    
    @Override
    public int glGetUniformLocation_P(int program, String name) {
        return shaderService.glGetUniformLocation_P(program, name);
    }

    @Override
    public void glGetVertexAttribfv_P(int index, int pname, FloatBuffer params) {
        shaderService.glGetVertexAttribfv_P(index, pname, params);
    }

    @Override
    public void glGetVertexAttribiv_P(int index, int pname, IntBuffer params) {
        shaderService.glGetVertexAttribiv_P(index, pname, params);
    }

    @Override
    public void glHint_P(int target, int mode) {
        GL11.glHint(target, mode);
    }

    @Override
    public boolean glIsBuffer_P(int buffer) {
        return GL15.glIsBuffer(buffer);
    }

    @Override
    public boolean glIsEnabled_P(int cap) {
        return GL11.glIsEnabled(cap);
    }

    @Override
    public boolean glIsFramebuffer_P(int framebuffer) {
        return framebufferService.glIsFramebuffer_P(framebuffer);
    }

    @Override
    public boolean glIsProgram_P(int program) {
        return shaderService.glIsProgram_P(program);
    }

    @Override
    public boolean glIsRenderbuffer_P(int renderbuffer) {
        return framebufferService.glIsRenderbuffer_P(renderbuffer);
    }

    @Override
    public boolean glIsShader_P(int shader) {
        return shaderService.glIsShader_P(shader);
    }

    @Override
    public boolean glIsTexture_P(int texture) {
        return GL11.glIsTexture(texture);
    }

    @Override
    public void glLineWidth_P(float width) {
        GL11.glLineWidth(width);
    }

    @Override
    public void glLinkProgram_P(int program) {
        shaderService.glLinkProgram_P(program);
    }

    @Override
    public void glPixelStorei_P(int pname, int param) {
        GL11.glPixelStorei(pname, param);
    }

    @Override
    public void glPolygonOffset_P(float factor, float units) {
        GL11.glPolygonOffset(factor, units);
    }

    @Override
    public void glReadPixels_P(int x, int y, int width, int height, int format, int type, FloatBuffer data) {
        GL11.glReadPixels(x, y, width, height, format, type, data);
    }

    @Override
    public void glReadPixels_P(int x, int y, int width, int height, int format, int type, IntBuffer data) {
        GL11.glReadPixels(x, y, width, height, format, type, data);
    }

    @Override
    public void glReadPixels_P(int x, int y, int width, int height, int format, int type, ByteBuffer data) {
        GL11.glReadPixels(x, y, width, height, format, type, data);
    }

    @Override
    public void glRenderbufferStorage_P(int target, int internalFormat, int width, int height) {
        framebufferService.glRenderbufferStorage_P(target, internalFormat, width, height);
    }

    @Override
    public void glSampleCoverage_P(float value, boolean invert) {
        GL13.glSampleCoverage(value, invert);
    }

    @Override
    public void glScissor_P(int x, int y, int width, int height) {
        GL11.glScissor(x, y, width, height);
    }

    @Override
    public void glShaderSource_P(int shader, String source) {
        shaderService.glShaderSource_P(shader, source);
    }

    @Override
    public void glStencilFunc_P(int func, int ref, int mask) {
        GL11.glStencilFunc(func, ref, mask);
    }

    @Override
    public void glStencilMask_P(int mask) {
        GL11.glStencilMask(mask);
    }

    @Override
    public void glStencilOp_P(int sfail, int dpfail, int dppass) {
        GL11.glStencilOp(sfail, dpfail, dppass);
    }

    @Override
    public void glTexImage2D_P(int target, int level, int internalFormat, int width, int height, int border, int format, int type, FloatBuffer data) {
        GL11.glTexImage2D(target, level, internalFormat, width, height, border, format, type, data);
    }

    @Override
    public void glTexImage2D_P(int target, int level, int internalFormat, int width, int height, int border, int format, int type, IntBuffer data) {
        GL11.glTexImage2D(target, level, internalFormat, width, height, border, format, type, data);
    }

    @Override
    public void glTexImage2D_P(int target, int level, int internalFormat, int width, int height, int border, int format, int type, ByteBuffer data) {
        GL11.glTexImage2D(target, level, internalFormat, width, height, border, format, type, data);
    }

    @Override
    public void glTexParameterf_P(int target, int pname, float param) {
        GL11.glTexParameterf(target, pname, param);
    }

    @Override
    public void glTexParameterfv_P(int target, int pname, FloatBuffer param) {
        GL11.glTexParameter(target, pname, param);
    }

    @Override
    public void glTexParameteri_P(int target, int pname, int param) {
        GL11.glTexParameteri(target, pname, param);
    }

    @Override
    public void glTexParameteriv_P(int target, int pname, IntBuffer param) {
        GL11.glTexParameter(target, pname, param);
    }

    @Override
    public void glTexSubImage2D_P(int target, int level, int xOffset, int yOffset, int width, int height, int format, int type, FloatBuffer data) {
        GL11.glTexSubImage2D(target, level, xOffset, yOffset, width, height, format, type, data);
    }

    @Override
    public void glTexSubImage2D_P(int target, int level, int xOffset, int yOffset, int width, int height, int format, int type, IntBuffer data) {
        GL11.glTexSubImage2D(target, level, xOffset, yOffset, width, height, format, type, data);
    }

    @Override
    public void glTexSubImage2D_P(int target, int level, int xOffset, int yOffset, int width, int height, int format, int type, ByteBuffer data) {
        GL11.glTexSubImage2D(target, level, xOffset, yOffset, width, height, format, type, data);
    }

    @Override
    public void glUniform1f_P(int location, float v1) {
        shaderService.glUniform1f_P(location, v1);
    }

    @Override
    public void glUniform1fv_P(int location, FloatBuffer value) {
        shaderService.glUniform1fv_P(location, value);
    }

    @Override
    public void glUniform1i_P(int location, int v1) {
        shaderService.glUniform1i_P(location, v1);
    }

    @Override
    public void glUniform1iv_P(int location, IntBuffer value) {
        shaderService.glUniform1iv_P(location, value);
    }

    @Override
    public void glUniform2f_P(int location, float v1, float v2) {
        shaderService.glUniform2f_P(location, v1, v2);
    }

    @Override
    public void glUniform2fv_P(int location, FloatBuffer value) {
        shaderService.glUniform2fv_P(location, value);
    }

    @Override
    public void glUniform2i_P(int location, int v1, int v2) {
        shaderService.glUniform2i_P(location, v1, v2);
    }

    @Override
    public void glUniform2iv_P(int location, IntBuffer value) {
        shaderService.glUniform2iv_P(location, value);
    }

    @Override
    public void glUniform3f_P(int location, float v1, float v2, float v3) {
        shaderService.glUniform3f_P(location, v1, v2, v3);
    }

    @Override
    public void glUniform3fv_P(int location, FloatBuffer value) {
        shaderService.glUniform3fv_P(location, value);
    }

    @Override
    public void glUniform3i_P(int location, int v1, int v2, int v3) {
        shaderService.glUniform3i_P(location, v1, v2, v3);
    }

    @Override
    public void glUniform3iv_P(int location, IntBuffer value) {
        shaderService.glUniform3iv_P(location, value);
    }

    @Override
    public void glUniform4f_P(int location, float v1, float v2, float v3, float v4) {
        shaderService.glUniform4f_P(location, v1, v2, v3, v4);
    } 

    @Override
    public void glUniform4fv_P(int location, FloatBuffer value) {
        shaderService.glUniform4fv_P(location, value);
    }

    @Override
    public void glUniform4i_P(int location, int v1, int v2, int v3, int v4) {
        shaderService.glUniform4i_P(location, v1, v2, v3, v4);
    }

    @Override
    public void glUniform4iv_P(int location, IntBuffer value) {
        shaderService.glUniform4iv_P(location, value);
    }

    @Override
    public void glUniformMatrix2fv_P(int location, boolean transpose, FloatBuffer value) {
        shaderService.glUniformMatrix2fv_P(location, transpose, value);
    }

    @Override
    public void glUniformMatrix3fv_P(int location, boolean transpose, FloatBuffer value) {
        shaderService.glUniformMatrix3fv_P(location, transpose, value);
    }

    @Override
    public void glUniformMatrix4fv_P(int location, boolean transpose, FloatBuffer value) {
        shaderService.glUniformMatrix4fv_P(location, transpose, value);
    }

    @Override
    public void glUseProgram_P(int program) {
        shaderService.glUseProgram_P(program);
    }

    @Override
    public void glValidateProgram_P(int program) {
        shaderService.glValidateProgram_P(program);
    }

    @Override
    public void glVertexAttrib1f_P(int index, float v1) {
        shaderService.glVertexAttrib1f_P(index, v1);
    }

    @Override
    public void glVertexAttrib2f_P(int index, float v1, float v2) {
        shaderService.glVertexAttrib2f_P(index, v1, v2);
    }
    
    @Override
    public void glVertexAttrib3f_P(int index, float v1, float v2, float v3) {
        shaderService.glVertexAttrib3f_P(index, v1, v2, v3);
    }

    @Override
    public void glVertexAttrib4f_P(int index, float v1, float v2, float v3, float v4) {
        shaderService.glVertexAttrib4f_P(index, v1, v2, v3, v4);
    }

    @Override
    public void glVertexAttribPointer_P(int index, int size, int type, boolean normalized, int stride, int bufferOffset) {
        shaderService.glVertexAttribPointer_P(index, size, type, normalized, stride, bufferOffset);
    }

    @Override
    public void glVertexAttribPointer_P(int index, int size, boolean normalized, int stride, FloatBuffer buffer) {
        shaderService.glVertexAttribPointer_P(index, size, normalized, stride, buffer);
    }

    @Override
    public void glVertexAttribPointer_P(int index, int size, boolean signed, boolean normalized, int stride, IntBuffer buffer) {
        shaderService.glVertexAttribPointer_P(index, size, signed, normalized, stride, buffer);
    }

    @Override
    public void glVertexAttribPointer_P(int index, int size, boolean signed, boolean normalized, int stride, ByteBuffer buffer) {
        shaderService.glVertexAttribPointer_P(index, size, signed, normalized, stride, buffer);
    }

    @Override
    public void glViewport_P(int x, int y, int width, int height) {
        GL11.glViewport(x, y, width, height);
    }
    
    @Override
    public DesktopLoadedImage loadImage_P(InputStream is, boolean flip) {
        return new DesktopLoadedImage(is, flip);
    }

    @Override
    public LogStream getLogStream_P(LogLevel level) {
        if(level == LogLevel.ERROR || level == LogLevel.WARN) {
            return ERROR_STREAM;
        } else {
            return DEFAULT_STREAM;
        }
    }

    @Override
    public File getLogFile_P(String filename) {
        return new File(filename);
    }
    
    /*@Override
    public void fillAlphaMaskTypeTexture_P(DesktopLoadedImage img, int destFormat, int texWidth, int texHeight) {
        GL11.glTexImage2D(
                GL11.GL_TEXTURE_2D, 
                0, 
                destFormat, 
                texWidth, texHeight, 
                0, 
                img.getFormat(), 
                GL11.GL_UNSIGNED_BYTE, 
                img.getData(texWidth, texHeight)
        );
    }*/
    
    @Override
    public void fillTypeTexture_P(DesktopLoadedImage img, int destFormat, int texWidth, int texHeight) {
        GL11.glTexImage2D(
                GL11.GL_TEXTURE_2D, 
                0, 
                destFormat, 
                texWidth, texHeight, 
                0, 
                img.getFormat(), 
                GL11.GL_UNSIGNED_BYTE, 
                img.getData(texWidth, texHeight)
        );
    }
    
    @Override
    public void fillTypeSubTexture_P(int xPos, int yPos, DesktopLoadedImage img) {
        GL11.glTexSubImage2D(
                GL11.GL_TEXTURE_2D, 
                0, 
                xPos, yPos, 
                img.getWidth(), img.getHeight(), 
                img.getFormat(), 
                GL11.GL_UNSIGNED_BYTE, 
                img.getData()
        );
    }
    
    @Override
    public long getTimeMillis_P() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }
    
    @Override
    public int getPlatformConstant() {
        return DESKTOP_CONSTANT;
    }
}
