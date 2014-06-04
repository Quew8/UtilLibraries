package com.quew8.gutils;

import java.io.File;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.quew8.gutils.debug.DebugLogger;
import com.quew8.gutils.debug.LogLevel;
import com.quew8.gutils.debug.LogStream;
import com.quew8.gutils.opengl.OpenGLUtils;
import com.quew8.gutils.opengl.texture.LoadedImage;

public class DebugBackend<T extends LoadedImage> extends PlatformBackend<T> {
    public static boolean showBufferContent = false;
    
	private final PlatformBackend<T> impl;
	
	public DebugBackend(PlatformBackend<T> impl) {
		this.impl = impl;
	}
	
	protected PlatformBackend<T> getImpl() {
		return impl;
	}
	
	@Override
	public void glActiveTexture_P(int texture) {
		DebugLogger.d("Backend Debug", "glActiveTexture_P() {texture = " + texture + "}");
		impl.glActiveTexture_P(texture);
	}

	@Override
	public void glAttachShader_P(int program, int shader) {
		DebugLogger.d("Backend Debug", "glAttachShader_P() {program = " + program + ", shader = " + shader + "}");
		impl.glAttachShader_P(program, shader);
	}

	@Override
	public void glBindAttribLocation_P(int program, int index, String name) {
		DebugLogger.d("Backend Debug", "glBindAttribLocation_P() {program = " + program + ", index = " + index  + ", name = " + name + "}");
		impl.glBindAttribLocation_P(program, index, name);
	}

	@Override
	public void glBindBuffer_P(int target, int buffer) {
		DebugLogger.d("Backend Debug", "glBindBuffer_P() {target = " + toString(target) + ", buffer = " + buffer + "}");
		impl.glBindBuffer_P(target, buffer);
	}

	@Override
	public void glBindFramebuffer_P(int target, int framebuffer) {
		DebugLogger.d("Backend Debug", "glBindFramebuffer_P() {target = " + toString(target) + ", framebuffer = " + framebuffer + "}");
		impl.glBindFramebuffer_P(target, framebuffer);
	}

	@Override
	public void glBindRenderbuffer_P(int target, int renderbuffer) {
		impl.glBindRenderbuffer_P(target, renderbuffer);
	}

	@Override
	public void glBindTexture_P(int target, int texture) {
		impl.glBindTexture_P(target, texture);
	}

	@Override
	public void glBlendColor_P(float red, float green, float blue, float alpha) {
		impl.glBlendColor_P(red, green, blue, alpha);
	}

	@Override
	public void glBlendEquation_P(int mode) {
		impl.glBlendEquation_P(mode);
	}

	@Override
	public void glBlendFunc_P(int sFactor, int dFactor) {
		impl.glBlendFunc_P(sFactor, dFactor);
	}

	@Override
	public void glBufferData_P(int target, FloatBuffer data, int usage) {
		DebugLogger.d("Backend Debug", "glBufferData_P() {target = " + toString(target) + ", data = " + toString(data) + ", usage = " + toString(usage) + "}");
		impl.glBufferData_P(target, data, usage);
	}

	@Override
	public void glBufferData_P(int target, IntBuffer data, int usage) {
		DebugLogger.d("Backend Debug", "glBufferData_P() {target = " + toString(target) + ", data = " + toString(data) + ", usage = " + toString(usage) + "}");
		impl.glBufferData_P(target, data, usage);
	}

	@Override
	public void glBufferData_P(int target, ByteBuffer data, int usage) {
		DebugLogger.d("Backend Debug", "glBufferData_P() {target = " + toString(target) + ", data = " + toString(data) + ", usage = " + toString(usage) + "}");
		impl.glBufferData_P(target, data, usage);
	}

	@Override
	public void glBufferSubData_P(int target, int offset, FloatBuffer data) {
		impl.glBufferSubData_P(target, offset, data);
	}

	@Override
	public void glBufferSubData_P(int target, int offset, IntBuffer data) {
		impl.glBufferSubData_P(target, offset, data);
	}

	@Override
	public void glBufferSubData_P(int target, int offset, ByteBuffer data) {
		impl.glBufferSubData_P(target, offset, data);
	}

	@Override
	public int glCheckFramebufferStatus_P(int target) {
		return impl.glCheckFramebufferStatus_P(target);
	}

	@Override
	public void glClear_P(int mask) {
		impl.glClear_P(mask);
	}

	@Override
	public void glClearColor_P(float red, float green, float blue, float alpha) {
		impl.glClearColor_P(red, green, blue, alpha);
	}

	@Override
	public void glClearDepth_P(float depth) {
		impl.glClearDepth_P(depth);
	}

	@Override
	public void glClearStencil_P(int stencil) {
		impl.glClearStencil_P(stencil);
	}

	@Override
	public void glColorMask_P(boolean red, boolean green, boolean blue,
			boolean alpha) {
		impl.glColorMask_P(red, green, blue, alpha);
	}

	@Override
	public void glCompileShader_P(int shader) {
		impl.glCompileShader_P(shader);
	}

	@Override
	public void glCompressedTexImage2D_P(int target, int level,
			int internalFormat, int width, int height, int border,
			ByteBuffer data) {
		impl.glCompressedTexImage2D_P(target, level, internalFormat, width,
				height, border, data);
	}

	@Override
	public void glCompressedTexSubImage2D_P(int target, int level, int xOffset,
			int yOffset, int width, int height, int format, ByteBuffer data) {
		impl.glCompressedTexSubImage2D_P(target, level, xOffset, yOffset,
				width, height, format, data);
	}

	@Override
	public void glCopyTexImage2D_P(int target, int level, int internalForamt,
			int x, int y, int width, int height, int border) {
		impl.glCopyTexImage2D_P(target, level, internalForamt, x, y, width,
				height, border);
	}

	@Override
	public void glCopyTexSubImage2D_P(int target, int level, int xOffset,
			int yOffset, int x, int y, int width, int height) {
		impl.glCopyTexSubImage2D_P(target, level, xOffset, yOffset, x, y,
				width, height);
	}

	@Override
	public int glCreateProgram_P() {
		return impl.glCreateProgram_P();
	}

	@Override
	public int glCreateShader_P(int type) {
		return impl.glCreateShader_P(type);
	}

	@Override
	public void glCullFace_P(int mode) {
		impl.glCullFace_P(mode);
	}

	@Override
	public void glDeleteBuffers_P(IntBuffer buffers) {
		impl.glDeleteBuffers_P(buffers);
	}

	@Override
	public void glDeleteFramebuffers_P(IntBuffer buffers) {
		impl.glDeleteFramebuffers_P(buffers);
	}

	@Override
	public void glDeleteProgram_P(int program) {
		impl.glDeleteProgram_P(program);
	}

	@Override
	public void glDeleteRenderbuffers_P(IntBuffer buffers) {
		impl.glDeleteRenderbuffers_P(buffers);
	}

	@Override
	public void glDeleteShader_P(int shader) {
		impl.glDeleteShader_P(shader);
	}

	@Override
	public void glDeleteTextures_P(IntBuffer arg1) {
		impl.glDeleteTextures_P(arg1);
	}

	@Override
	public void glDepthFunc_P(int func) {
		impl.glDepthFunc_P(func);
	}

	@Override
	public void glDepthMask_P(boolean flag) {
		impl.glDepthMask_P(flag);
	}

	@Override
	public void glDepthRange_P(float nearVal, float farVal) {
		impl.glDepthRange_P(nearVal, farVal);
	}

	@Override
	public void glDetachShader_P(int program, int shader) {
		impl.glDetachShader_P(program, shader);
	}

	@Override
	public void glDisable_P(int cap) {
		impl.glDisable_P(cap);
	}

	@Override
	public void glDisableVertexAttribArray_P(int index) {
		impl.glDisableVertexAttribArray_P(index);
	}

	@Override
	public void glDrawArrays_P(int mode, int first, int count) {
		impl.glDrawArrays_P(mode, first, count);
	}

	@Override
	public void glDrawElements_P(int mode, int count, int type, int bufferOffset) {
		DebugLogger.d("Backend Debug", "glDrawElements_P() {mode = " + toString(mode) + ", count = " + count + ", type = " + toString(type) + ", bufferOffset = " + bufferOffset  + "}");
		impl.glDrawElements_P(mode, count, type, bufferOffset);
	}

	@Override
	public void glDrawElements_P(int mode, IntBuffer buffer) {
		DebugLogger.d("Backend Debug", "glDrawElements_P() {mode = " + toString(mode) + ", buffer = " + toString(buffer) + "}");
		impl.glDrawElements_P(mode, buffer);
	}

	@Override
	public void glDrawElements_P(int mode, ByteBuffer buffer) {
		DebugLogger.d("Backend Debug", "glDrawElements_P() {mode = " + toString(mode) + ", buffer = " + toString(buffer) + "}");
		impl.glDrawElements_P(mode, buffer);
	}

	@Override
	public void glEnable_P(int cap) {
		DebugLogger.d("Backend Debug", "glEnable_P() {cap = " + toString(cap) + "}");
		impl.glEnable_P(cap);
	}

	@Override
	public void glEnableVertexAttribArray_P(int index) {
		DebugLogger.d("Backend Debug", "glEnableVertexAttribArray_P() {index = " + index + "}");
		impl.glEnableVertexAttribArray_P(index);
	}

	@Override
	public void glFinish_P() {
		impl.glFinish_P();
	}

	@Override
	public void glFlush_P() {
		impl.glFlush_P();
	}

	@Override
	public void glFramebufferRenderbuffer_P(int target, int attachment,
			int renderbufferTarget, int renderbuffer) {
		impl.glFramebufferRenderbuffer_P(target, attachment,
				renderbufferTarget, renderbuffer);
	}

	@Override
	public void glFramebufferTexture2D_P(int target, int attachment,
			int texTarget, int texture, int level) {
		impl.glFramebufferTexture2D_P(target, attachment, texTarget, texture,
				level);
	}

	@Override
	public void glFrontFace_P(int mode) {
		impl.glFrontFace_P(mode);
	}

	@Override
	public void glGenBuffers_P(IntBuffer buffers) {
		impl.glGenBuffers_P(buffers);
	}

	@Override
	public void glGenFramebuffers_P(IntBuffer buffers) {
		impl.glGenFramebuffers_P(buffers);
	}

	@Override
	public void glGenRenderbuffers_P(IntBuffer buffers) {
		impl.glGenRenderbuffers_P(buffers);
	}

	@Override
	public void glGenTextures_P(IntBuffer buffers) {
		impl.glGenTextures_P(buffers);
	}

	@Override
	public String glGetActiveAttrib_P(int program, int index, IntBuffer length,
			IntBuffer type) {
		return impl.glGetActiveAttrib_P(program, index, length, type);
	}

	@Override
	public void glGetAttachedShaders_P(int program, IntBuffer count,
			IntBuffer shaders) {
		impl.glGetAttachedShaders_P(program, count, shaders);
	}

	@Override
	public int glGetAttribLocation_P(int program, String name) {
		return impl.glGetAttribLocation_P(program, name);
	}

	@Override
	public void glGetBufferParameteriv_P(int target, int value, IntBuffer data) {
		impl.glGetBufferParameteriv_P(target, value, data);
	}

	@Override
	public void glGetBooleanv_P(int pname, ByteBuffer params) {
		impl.glGetBooleanv_P(pname, params);
	}

	@Override
	public int glGetError_P() {
		return impl.glGetError_P();
	}

	@Override
	public void glGetFloatv_P(int pname, FloatBuffer params) {
		impl.glGetFloatv_P(pname, params);
	}

	@Override
	public void glGetFramebufferAttachmentParameteriv_P(int target,
			int attatchment, int pname, IntBuffer params) {
		impl.glGetFramebufferAttachmentParameteriv_P(target, attatchment,
				pname, params);
	}

	@Override
	public void glGetIntegerv_P(int pname, IntBuffer params) {
		impl.glGetIntegerv_P(pname, params);
	}

	@Override
	public void glGetProgramiv_P(int program, int pname, IntBuffer params) {
		impl.glGetProgramiv_P(program, pname, params);
	}

	@Override
	public String glGetProgramInfoLog_P(int program) {
		return impl.glGetProgramInfoLog_P(program);
	}

	@Override
	public void glGetRenderbufferParameteriv_P(int target, int pname,
			IntBuffer params) {
		impl.glGetRenderbufferParameteriv_P(target, pname, params);
	}

	@Override
	public void glGetShaderiv_P(int shader, int pname, IntBuffer params) {
		impl.glGetShaderiv_P(shader, pname, params);
	}

	@Override
	public String glGetShaderInfoLog_P(int shader) {
		return impl.glGetShaderInfoLog_P(shader);
	}

	@Override
	public String glGetShaderSource_P(int shader) {
		return impl.glGetShaderSource_P(shader);
	}

	@Override
	public String glGetString_P(int pname) {
		return impl.glGetString_P(pname);
	}

	@Override
	public void glGetTexParameterfv_P(int target, int pname, FloatBuffer params) {
		impl.glGetTexParameterfv_P(target, pname, params);
	}

	@Override
	public void glGetTexParameteriv_P(int target, int pname, IntBuffer params) {
		impl.glGetTexParameteriv_P(target, pname, params);
	}

	@Override
	public void glGetUniformfv_P(int program, int location, FloatBuffer params) {
		impl.glGetUniformfv_P(program, location, params);
	}

	@Override
	public void glGetUniformiv_P(int program, int location, IntBuffer params) {
		impl.glGetUniformiv_P(program, location, params);
	}

	@Override
	public int glGetUniformLocation_P(int program, String name) {
		return impl.glGetUniformLocation_P(program, name);
	}

	@Override
	public void glGetVertexAttribfv_P(int index, int pname, FloatBuffer params) {
		impl.glGetVertexAttribfv_P(index, pname, params);
	}

	@Override
	public void glGetVertexAttribiv_P(int index, int pname, IntBuffer params) {
		impl.glGetVertexAttribiv_P(index, pname, params);
	}

	@Override
	public void glHint_P(int target, int mode) {
		impl.glHint_P(target, mode);
	}

	@Override
	public boolean glIsBuffer_P(int buffer) {
		return impl.glIsBuffer_P(buffer);
	}

	@Override
	public boolean glIsEnabled_P(int cap) {
		return impl.glIsEnabled_P(cap);
	}

	@Override
	public boolean glIsFramebuffer_P(int framebuffer) {
		return impl.glIsFramebuffer_P(framebuffer);
	}

	@Override
	public boolean glIsProgram_P(int program) {
		return impl.glIsProgram_P(program);
	}

	@Override
	public boolean glIsRenderbuffer_P(int renderbuffer) {
		return impl.glIsRenderbuffer_P(renderbuffer);
	}

	@Override
	public boolean glIsShader_P(int shader) {
		return impl.glIsShader_P(shader);
	}

	@Override
	public boolean glIsTexture_P(int texture) {
		return impl.glIsTexture_P(texture);
	}

	@Override
	public void glLineWidth_P(float width) {
		impl.glLineWidth_P(width);
	}

	@Override
	public void glLinkProgram_P(int program) {
		impl.glLinkProgram_P(program);
	}

	@Override
	public void glPixelStorei_P(int pname, int param) {
		impl.glPixelStorei_P(pname, param);
	}

	@Override
	public void glPolygonOffset_P(float factor, float units) {
		impl.glPolygonOffset_P(factor, units);
	}

	@Override
	public void glReadPixels_P(int x, int y, int width, int height, int format,
			int type, FloatBuffer data) {
		impl.glReadPixels_P(x, y, width, height, format, type, data);
	}

	@Override
	public void glReadPixels_P(int x, int y, int width, int height, int format,
			int type, IntBuffer data) {
		impl.glReadPixels_P(x, y, width, height, format, type, data);
	}

	@Override
	public void glReadPixels_P(int x, int y, int width, int height, int format,
			int type, ByteBuffer data) {
		impl.glReadPixels_P(x, y, width, height, format, type, data);
	}

	@Override
	public void glRenderbufferStorage_P(int target, int internalFormat,
			int width, int height) {
		impl.glRenderbufferStorage_P(target, internalFormat, width, height);
	}

	@Override
	public void glSampleCoverage_P(float value, boolean invert) {
		impl.glSampleCoverage_P(value, invert);
	}

	@Override
	public void glScissor_P(int x, int y, int width, int height) {
		impl.glScissor_P(x, y, width, height);
	}

	@Override
	public void glShaderSource_P(int shader, String source) {
		impl.glShaderSource_P(shader, source);
	}

	@Override
	public void glStencilFunc_P(int func, int ref, int mask) {
		impl.glStencilFunc_P(func, ref, mask);
	}

	@Override
	public void glStencilMask_P(int mask) {
		impl.glStencilMask_P(mask);
	}

	@Override
	public void glStencilOp_P(int sfail, int dpfail, int dppass) {
		impl.glStencilOp_P(sfail, dpfail, dppass);
	}

	@Override
	public void glTexImage2D_P(int target, int level, int internalFormat,
			int width, int height, int border, int format, int type,
			FloatBuffer data) {
		impl.glTexImage2D_P(target, level, internalFormat, width, height,
				border, format, type, data);
	}

	@Override
	public void glTexImage2D_P(int target, int level, int internalFormat,
			int width, int height, int border, int format, int type,
			IntBuffer data) {
		impl.glTexImage2D_P(target, level, internalFormat, width, height,
				border, format, type, data);
	}

	@Override
	public void glTexImage2D_P(int target, int level, int internalFormat,
			int width, int height, int border, int format, int type,
			ByteBuffer data) {
		impl.glTexImage2D_P(target, level, internalFormat, width, height,
				border, format, type, data);
	}

	@Override
	public void glTexParameterf_P(int target, int pname, float param) {
		impl.glTexParameterf_P(target, pname, param);
	}

	@Override
	public void glTexParameterfv_P(int target, int pname, FloatBuffer param) {
		impl.glTexParameterfv_P(target, pname, param);
	}

	@Override
	public void glTexParameteri_P(int target, int pname, int param) {
		impl.glTexParameteri_P(target, pname, param);
	}

	@Override
	public void glTexParameteriv_P(int target, int pname, IntBuffer param) {
		impl.glTexParameteriv_P(target, pname, param);
	}

	@Override
	public void glTexSubImage2D_P(int target, int level, int xOffset,
			int yOffset, int width, int height, int format, int type,
			FloatBuffer data) {
		impl.glTexSubImage2D_P(target, level, xOffset, yOffset, width, height,
				format, type, data);
	}

	@Override
	public void glTexSubImage2D_P(int target, int level, int xOffset,
			int yOffset, int width, int height, int format, int type,
			IntBuffer data) {
		impl.glTexSubImage2D_P(target, level, xOffset, yOffset, width, height,
				format, type, data);
	}

	@Override
	public void glTexSubImage2D_P(int target, int level, int xOffset,
			int yOffset, int width, int height, int format, int type,
			ByteBuffer data) {
		impl.glTexSubImage2D_P(target, level, xOffset, yOffset, width, height,
				format, type, data);
	}

	@Override
	public void glUniform1f_P(int location, float v1) {
		impl.glUniform1f_P(location, v1);
	}

	@Override
	public void glUniform1fv_P(int location, FloatBuffer value) {
		impl.glUniform1fv_P(location, value);
	}

	@Override
	public void glUniform1i_P(int location, int v1) {
		impl.glUniform1i_P(location, v1);
	}

	@Override
	public void glUniform1iv_P(int location, IntBuffer value) {
		impl.glUniform1iv_P(location, value);
	}

	@Override
	public void glUniform2f_P(int location, float v1, float v2) {
		impl.glUniform2f_P(location, v1, v2);
	}

	@Override
	public void glUniform2fv_P(int location, FloatBuffer value) {
		impl.glUniform2fv_P(location, value);
	}

	@Override
	public void glUniform2i_P(int location, int v1, int v2) {
		impl.glUniform2i_P(location, v1, v2);
	}

	@Override
	public void glUniform2iv_P(int location, IntBuffer value) {
		impl.glUniform2iv_P(location, value);
	}

	@Override
	public void glUniform3f_P(int location, float v1, float v2, float v3) {
		impl.glUniform3f_P(location, v1, v2, v3);
	}

	@Override
	public void glUniform3fv_P(int location, FloatBuffer value) {
		impl.glUniform3fv_P(location, value);
	}

	@Override
	public void glUniform3i_P(int location, int v1, int v2, int v3) {
		impl.glUniform3i_P(location, v1, v2, v3);
	}

	@Override
	public void glUniform3iv_P(int location, IntBuffer value) {
		impl.glUniform3iv_P(location, value);
	}

	@Override
	public void glUniform4f_P(int location, float v1, float v2, float v3,
			float v4) {
		impl.glUniform4f_P(location, v1, v2, v3, v4);
	}

	@Override
	public void glUniform4fv_P(int location, FloatBuffer value) {
		impl.glUniform4fv_P(location, value);
	}

	@Override
	public void glUniform4i_P(int location, int v1, int v2, int v3, int v4) {
		impl.glUniform4i_P(location, v1, v2, v3, v4);
	}

	@Override
	public void glUniform4iv_P(int location, IntBuffer value) {
		impl.glUniform4iv_P(location, value);
	}

	@Override
	public void glUniformMatrix2fv_P(int location, boolean transpose,
			FloatBuffer value) {
		impl.glUniformMatrix2fv_P(location, transpose, value);
	}

	@Override
	public void glUniformMatrix3fv_P(int location, boolean transpose,
			FloatBuffer value) {
		impl.glUniformMatrix3fv_P(location, transpose, value);
	}

	@Override
	public void glUniformMatrix4fv_P(int location, boolean transpose,
			FloatBuffer value) {
		impl.glUniformMatrix4fv_P(location, transpose, value);
	}

	@Override
	public void glUseProgram_P(int program) {
		impl.glUseProgram_P(program);
	}

	@Override
	public void glValidateProgram_P(int program) {
		impl.glValidateProgram_P(program);
	}

	@Override
	public void glVertexAttrib1f_P(int index, float v1) {
		impl.glVertexAttrib1f_P(index, v1);
	}

	@Override
	public void glVertexAttrib2f_P(int index, float v1, float v2) {
		impl.glVertexAttrib2f_P(index, v1, v2);
	}

	@Override
	public void glVertexAttrib3f_P(int index, float v1, float v2, float v3) {
		impl.glVertexAttrib3f_P(index, v1, v2, v3);
	}

	@Override
	public void glVertexAttrib4f_P(int index, float v1, float v2, float v3,
			float v4) {
		impl.glVertexAttrib4f_P(index, v1, v2, v3, v4);
	}

	@Override
	public void glVertexAttribPointer_P(int index, int size, int type,
			boolean normalized, int stride, int bufferOffset) {
        
		DebugLogger.d("Backend Debug", "glVertexAttribPointer_P() {index = " + index + ", size = " + size  + ", type = " + toString(type) + ", normalized = " + normalized + ", stride = " + stride + ", bufferOffset = " + bufferOffset + "}");
		impl.glVertexAttribPointer_P(index, size, type, normalized, stride,
				bufferOffset);
	}

	@Override
	public void glVertexAttribPointer_P(int index, int size,
			boolean normalized, int stride, FloatBuffer buffer) {
        
		DebugLogger.d("Backend Debug", "glVertexAttribPointer_P() {index = " + index + ", size = " + size  + ", normalized = " + normalized + ", stride = " + stride + ", buffer = " + toString(buffer) + "}");
		impl.glVertexAttribPointer_P(index, size, normalized, stride, buffer);
	}

	@Override
	public void glVertexAttribPointer_P(int index, int size, boolean unsigned,
			boolean normalized, int stride, IntBuffer buffer) {
        
		DebugLogger.d("Backend Debug", "glVertexAttribPointer_P() {index = " + index + ", size = " + size + ", unsigned = " + unsigned + ", normalized = " + normalized + ", stride = " + stride + ", buffer = " + toString(buffer) + "}");
		impl.glVertexAttribPointer_P(index, size, unsigned, normalized, stride,
				buffer);
	}

	@Override
	public void glVertexAttribPointer_P(int index, int size, boolean unsigned,
			boolean normalized, int stride, ByteBuffer buffer) {
        
		DebugLogger.d("Backend Debug", "glVertexAttribPointer_P() {index = " + index + ", size = " + size + ", unsigned = " + unsigned + ", normalized = " + normalized + ", stride = " + stride + ", buffer = " + toString(buffer) + "}");
		impl.glVertexAttribPointer_P(index, size, unsigned, normalized, stride,
				buffer);
	}

	@Override
	public void glViewport_P(int x, int y, int width, int height) {
		impl.glViewport_P(x, y, width, height);
	}

	@Override
	public LogStream getLogStream_P(LogLevel level) {
		return impl.getLogStream_P(level);
	}

	@Override
	public File getLogFile_P(String filename) {
		return impl.getLogFile_P(filename);
	}

	@Override
	public long getTimeMillis_P() {
		return impl.getTimeMillis_P();
	}

	@Override
	public T loadImage_P(InputStream is, boolean flip) {
		return impl.loadImage_P(is, flip);
	}

	@Override
	public void fillTexture_P(LoadedImage img, int destFormat, int texWidth,
			int texHeight) {
        
		impl.fillTexture_P(img, destFormat, texWidth, texHeight);
	}

	@Override
	public void fillTypeTexture_P(T img, int destFormat, int texWidth,
			int texHeight) {
        
		impl.fillTypeTexture_P(img, destFormat, texWidth, texHeight);
	}

	@Override
	public void fillSubTexture_P(int xPos, int yPos, LoadedImage img) {
		impl.fillSubTexture_P(xPos, yPos, img);
	}

	@Override
	public void fillTypeSubTexture_P(int xPos, int yPos, T img) {
		impl.fillTypeSubTexture_P(xPos, yPos, img);
	}

	@Override
	public int getPlatformConstant() {
		return impl.getPlatformConstant();
	}
	
	private static String toString(int glEnum) {
		return OpenGLUtils.toOpenGLString(glEnum);
	}
	
	private static String toString(FloatBuffer fb) {
		String s = "FB{pos = " + fb.position() + ", lim = " + fb.limit() + ", cap = " + fb.capacity() + "}[";
        if(showBufferContent) {
            if(fb.limit() > 0) {
                s += fb.get(0);
                for(int i = 1; i < fb.limit(); i++) {
                    s += ", " + fb.get(i);
                }
            }
        } else {
            s += "...";
        }
		s += "]";
		return s;
	}
	
	private static String toString(IntBuffer ib) {
		String s = "IB{pos = " + ib.position() + ", lim = " + ib.limit() + ", cap = " + ib.capacity() + "}[";
        if(showBufferContent) {
            if(ib.limit() > 0) {
                s += ib.get(0);
                for(int i = 1; i < ib.limit(); i++) {
                    s += ", " + ib.get(i);
                }
            }
        } else {
            s += "...";
        }
		s += "]";
		return s;
	}
	
	private static String toString(ByteBuffer bb) {
		String s = "BB{pos = " + bb.position() + ", lim = " + bb.limit() + ", cap = " + bb.capacity() + "}[";
        if(showBufferContent) {
            if(bb.limit() > 0) {
                s += bb.get(0);
                for(int i = 1; i < bb.limit(); i++) {
                    s += ", " + bb.get(i);
                }
            }
        } else {
            s += "...";
        }
		s += "]";
		return s;
	}
	
	public static <T extends LoadedImage> DebugBackend<?> getDebugBackend(PlatformBackend<T> impl) {
		return new DebugBackend<T>(impl);
	}
	
}