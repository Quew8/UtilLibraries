package com.quew8.gutils.opengl;

import com.quew8.gutils.PlatformBackend;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 *
 * @author Quew8
 */
public abstract class OpenGL {
    public static final int GL_ACTIVE_TEXTURE = 0x84E0;
    public static final int GL_DEPTH_BUFFER_BIT = 0x100;
    public static final int GL_STENCIL_BUFFER_BIT = 0x400;
    public static final int GL_COLOR_BUFFER_BIT = 0x4000;
    public static final int GL_FALSE = 0x0;
    public static final int GL_TRUE = 0x1;
    public static final int GL_POINTS = 0x0;
    public static final int GL_LINES = 0x1;
    public static final int GL_LINE_LOOP = 0x2;
    public static final int GL_LINE_STRIP = 0x3;
    public static final int GL_TRIANGLES = 0x4;
    public static final int GL_TRIANGLE_STRIP = 0x5;
    public static final int GL_TRIANGLE_FAN = 0x6;
    public static final int GL_ZERO = 0x0;
    public static final int GL_ONE = 0x1;
    public static final int GL_SRC_COLOR = 0x300;
    public static final int GL_ONE_MINUS_SRC_COLOR = 0x301;
    public static final int GL_SRC_ALPHA = 0x302;
    public static final int GL_ONE_MINUS_SRC_ALPHA = 0x303;
    public static final int GL_DST_ALPHA = 0x304;
    public static final int GL_ONE_MINUS_DST_ALPHA = 0x305;
    public static final int GL_DST_COLOR = 0x306;
    public static final int GL_ONE_MINUS_DST_COLOR = 0x307;
    public static final int GL_SRC_ALPHA_SATURATE = 0x308;
    public static final int GL_FUNC_ADD = 0x8006;
    public static final int GL_BLEND_EQUATION = 0x8009;
    public static final int GL_FUNC_SUBTRACT = 0x800A;
    public static final int GL_FUNC_REVERSE_SUBTRACT = 0x800B;
    public static final int GL_BLEND_DST_RGB = 0x80C8;
    public static final int GL_BLEND_SRC_RGB = 0x80C9;
    public static final int GL_BLEND_DST_ALPHA = 0x80CA;
    public static final int GL_BLEND_SRC_ALPHA = 0x80CB;
    public static final int GL_CONSTANT_COLOR = 0x8001;
    public static final int GL_ONE_MINUS_CONSTANT_COLOR = 0x8002;
    public static final int GL_CONSTANT_ALPHA = 0x8003;
    public static final int GL_ONE_MINUS_CONSTANT_ALPHA = 0x8004;
    public static final int GL_BLEND_COLOR = 0x8005;
    public static final int GL_ARRAY_BUFFER = 0x8892;
    public static final int GL_ELEMENT_ARRAY_BUFFER = 0x8893;
    public static final int GL_ARRAY_BUFFER_BINDING = 0x8894;
    public static final int GL_ELEMENT_ARRAY_BUFFER_BINDING = 0x8895;
    public static final int GL_STREAM_DRAW = 0x88E0;
    public static final int GL_STATIC_DRAW = 0x88E4;
    public static final int GL_DYNAMIC_DRAW = 0x88E8;
    public static final int GL_BUFFER_SIZE = 0x8764;
    public static final int GL_BUFFER_USAGE = 0x8765;
    public static final int GL_CURRENT_VERTEX_ATTRIB = 0x8626;
    public static final int GL_FRONT = 0x404;
    public static final int GL_BACK = 0x405;
    public static final int GL_FRONT_AND_BACK = 0x408;
    public static final int GL_TEXTURE_2D = 0xDE1;
    public static final int GL_CULL_FACE = 0xB44;
    public static final int GL_BLEND = 0xBE2;
    public static final int GL_DITHER = 0xBD0;
    public static final int GL_STENCIL_TEST = 0xB90;
    public static final int GL_DEPTH_TEST = 0xB71;
    public static final int GL_SCISSOR_TEST = 0xC11;
    public static final int GL_POLYGON_OFFSET_FILL = 0x8037;
    public static final int GL_SAMPLE_ALPHA_TO_COVERAGE = 0x809E;
    public static final int GL_SAMPLE_COVERAGE = 0x80A0;
    public static final int GL_NO_ERROR = 0x0;
    public static final int GL_INVALID_ENUM = 0x500;
    public static final int GL_INVALID_VALUE = 0x501;
    public static final int GL_INVALID_OPERATION = 0x502;
    public static final int GL_OUT_OF_MEMORY = 0x505;
    public static final int GL_CW = 0x900;
    public static final int GL_CCW = 0x901;
    public static final int GL_LINE_WIDTH = 0xB21;
    public static final int GL_ALIASED_POINT_SIZE_RANGE = 0x846D;
    public static final int GL_ALIASED_LINE_WIDTH_RANGE = 0x846E;
    public static final int GL_CULL_FACE_MODE = 0xB45;
    public static final int GL_FRONT_FACE = 0xB46;
    public static final int GL_DEPTH_RANGE = 0xB70;
    public static final int GL_DEPTH_WRITEMASK = 0xB72;
    public static final int GL_DEPTH_CLEAR_VALUE = 0xB73;
    public static final int GL_DEPTH_FUNC = 0xB74;
    public static final int GL_STENCIL_CLEAR_VALUE = 0xB91;
    public static final int GL_STENCIL_FUNC = 0xB92;
    public static final int GL_STENCIL_FAIL = 0xB94;
    public static final int GL_STENCIL_PASS_DEPTH_FAIL = 0xB95;
    public static final int GL_STENCIL_PASS_DEPTH_PASS = 0xB96;
    public static final int GL_STENCIL_REF = 0xB97;
    public static final int GL_STENCIL_VALUE_MASK = 0xB93;
    public static final int GL_STENCIL_WRITEMASK = 0xB98;
    public static final int GL_VIEWPORT = 0xBA2;
    public static final int GL_SCISSOR_BOX = 0xC10;
    public static final int GL_COLOR_CLEAR_VALUE = 0xC22;
    public static final int GL_COLOR_WRITEMASK = 0xC23;
    public static final int GL_UNPACK_ALIGNMENT = 0xCF5;
    public static final int GL_PACK_ALIGNMENT = 0xD05;
    public static final int GL_MAX_TEXTURE_SIZE = 0xD33;
    public static final int GL_MAX_VIEWPORT_DIMS = 0xD3A;
    public static final int GL_SUBPIXEL_BITS = 0xD50;
    public static final int GL_RED_BITS = 0xD52;
    public static final int GL_GREEN_BITS = 0xD53;
    public static final int GL_BLUE_BITS = 0xD54;
    public static final int GL_ALPHA_BITS = 0xD55;
    public static final int GL_DEPTH_BITS = 0xD56;
    public static final int GL_STENCIL_BITS = 0xD57;
    public static final int GL_POLYGON_OFFSET_UNITS = 0x2A00;
    public static final int GL_POLYGON_OFFSET_FACTOR = 0x8038;
    public static final int GL_TEXTURE_BINDING_2D = 0x8069;
    public static final int GL_SAMPLE_BUFFERS = 0x80A8;
    public static final int GL_SAMPLES = 0x80A9;
    public static final int GL_SAMPLE_COVERAGE_VALUE = 0x80AA;
    public static final int GL_SAMPLE_COVERAGE_INVERT = 0x80AB;
    public static final int GL_NUM_COMPRESSED_TEXTURE_FORMATS = 0x86A2;
    public static final int GL_COMPRESSED_TEXTURE_FORMATS = 0x86A3;
    public static final int GL_DONT_CARE = 0x1100;
    public static final int GL_FASTEST = 0x1101;
    public static final int GL_NICEST = 0x1102;
    public static final int GL_GENERATE_MIPMAP_HINT = 0x8192;
    public static final int GL_BYTE = 0x1400;
    public static final int GL_UNSIGNED_BYTE = 0x1401;
    public static final int GL_SHORT = 0x1402;
    public static final int GL_UNSIGNED_SHORT = 0x1403;
    public static final int GL_INT = 0x1404;
    public static final int GL_UNSIGNED_INT = 0x1405;
    public static final int GL_FLOAT = 0x1406;
    public static final int GL_DEPTH_COMPONENT = 0x1902;
    public static final int GL_RED = 0x1903;
    public static final int GL_GREEN = 0x1904;
    public static final int GL_BLUE = 0x1905;
    public static final int GL_ALPHA = 0x1906;
    public static final int GL_RGB = 0x1907;
    public static final int GL_RGBA = 0x1908;
    public static final int GL_LUMINANCE = 0x1909;
    public static final int GL_LUMINANCE_ALPHA = 0x190A;
    public static final int GL_UNSIGNED_SHORT_4_4_4_4 = 0x8033;
    public static final int GL_UNSIGNED_SHORT_5_5_5_1 = 0x8034;
    public static final int GL_UNSIGNED_SHORT_5_6_5 = 0x8363;
    public static final int GL_FRAGMENT_SHADER = 0x8B30;
    public static final int GL_VERTEX_SHADER = 0x8B31;
    public static final int GL_MAX_VERTEX_ATTRIBS = 0x8869;
    public static final int GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS = 0x8B4D;
    public static final int GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS = 0x8B4C;
    public static final int GL_MAX_TEXTURE_IMAGE_UNITS = 0x8872;
    public static final int GL_SHADER_TYPE = 0x8B4F;
    public static final int GL_DELETE_STATUS = 0x8B80;
    public static final int GL_LINK_STATUS = 0x8B82;
    public static final int GL_VALIDATE_STATUS = 0x8B83;
    public static final int GL_ATTACHED_SHADERS = 0x8B85;
    public static final int GL_ACTIVE_UNIFORMS = 0x8B86;
    public static final int GL_ACTIVE_UNIFORM_MAX_LENGTH = 0x8B87;
    public static final int GL_ACTIVE_ATTRIBUTES = 0x8B89;
    public static final int GL_ACTIVE_ATTRIBUTE_MAX_LENGTH = 0x8B8A;
    public static final int GL_SHADING_LANGUAGE_VERSION = 0x8B8C;
    public static final int GL_CURRENT_PROGRAM = 0x8B8D;
    public static final int GL_NEVER = 0x200;
    public static final int GL_LESS = 0x201;
    public static final int GL_EQUAL = 0x202;
    public static final int GL_LEQUAL = 0x203;
    public static final int GL_GREATER = 0x204;
    public static final int GL_NOTEQUAL = 0x205;
    public static final int GL_GEQUAL = 0x206;
    public static final int GL_ALWAYS = 0x207;
    public static final int GL_KEEP = 0x1E00;
    public static final int GL_REPLACE = 0x1E01;
    public static final int GL_INCR = 0x1E02;
    public static final int GL_DECR = 0x1E03;
    public static final int GL_INVERT = 0x150A;
    public static final int GL_INCR_WRAP = 0x8507;
    public static final int GL_DECR_WRAP = 0x8508;
    public static final int GL_VENDOR = 0x1F00;
    public static final int GL_RENDERER = 0x1F01;
    public static final int GL_VERSION = 0x1F02;
    public static final int GL_EXTENSIONS = 0x1F03;
    public static final int GL_NEAREST = 0x2600;
    public static final int GL_LINEAR = 0x2601;
    public static final int GL_NEAREST_MIPMAP_NEAREST = 0x2700;
    public static final int GL_LINEAR_MIPMAP_NEAREST = 0x2701;
    public static final int GL_NEAREST_MIPMAP_LINEAR = 0x2702;
    public static final int GL_LINEAR_MIPMAP_LINEAR = 0x2703;
    public static final int GL_TEXTURE_MAG_FILTER = 0x2800;
    public static final int GL_TEXTURE_MIN_FILTER = 0x2801;
    public static final int GL_TEXTURE_WRAP_S = 0x2802;
    public static final int GL_TEXTURE_WRAP_T = 0x2803;
    public static final int GL_TEXTURE = 0x1702;
    public static final int GL_TEXTURE_CUBE_MAP = 0x8513;
    public static final int GL_TEXTURE_BINDING_CUBE_MAP = 0x8514;
    public static final int GL_TEXTURE_CUBE_MAP_POSITIVE_X = 0x8515;
    public static final int GL_TEXTURE_CUBE_MAP_NEGATIVE_X = 0x8516;
    public static final int GL_TEXTURE_CUBE_MAP_POSITIVE_Y = 0x8517;
    public static final int GL_TEXTURE_CUBE_MAP_NEGATIVE_Y = 0x8518;
    public static final int GL_TEXTURE_CUBE_MAP_POSITIVE_Z = 0x8519;
    public static final int GL_TEXTURE_CUBE_MAP_NEGATIVE_Z = 0x851A;
    public static final int GL_MAX_CUBE_MAP_TEXTURE_SIZE = 0x851C;
    public static final int GL_TEXTURE0 = 0x84C0;
    public static final int GL_TEXTURE1 = 0x84C1;
    public static final int GL_TEXTURE2 = 0x84C2;
    public static final int GL_TEXTURE3 = 0x84C3;
    public static final int GL_TEXTURE4 = 0x84C4;
    public static final int GL_TEXTURE5 = 0x84C5;
    public static final int GL_TEXTURE6 = 0x84C6;
    public static final int GL_TEXTURE7 = 0x84C7;
    public static final int GL_TEXTURE8 = 0x84C8;
    public static final int GL_TEXTURE9 = 0x84C9;
    public static final int GL_TEXTURE10 = 0x84CA;
    public static final int GL_TEXTURE11 = 0x84CB;
    public static final int GL_TEXTURE12 = 0x84CC;
    public static final int GL_TEXTURE13 = 0x84CD;
    public static final int GL_TEXTURE14 = 0x84CE;
    public static final int GL_TEXTURE15 = 0x84CF;
    public static final int GL_TEXTURE16 = 0x84D0;
    public static final int GL_TEXTURE17 = 0x84D1;
    public static final int GL_TEXTURE18 = 0x84D2;
    public static final int GL_TEXTURE19 = 0x84D3;
    public static final int GL_TEXTURE20 = 0x84D4;
    public static final int GL_TEXTURE21 = 0x84D5;
    public static final int GL_TEXTURE22 = 0x84D6;
    public static final int GL_TEXTURE23 = 0x84D7;
    public static final int GL_TEXTURE24 = 0x84D8;
    public static final int GL_TEXTURE25 = 0x84D9;
    public static final int GL_TEXTURE26 = 0x84DA;
    public static final int GL_TEXTURE27 = 0x84DB;
    public static final int GL_TEXTURE28 = 0x84DC;
    public static final int GL_TEXTURE29 = 0x84DD;
    public static final int GL_TEXTURE30 = 0x84DE;
    public static final int GL_TEXTURE31 = 0x84DF;
    public static final int GL_REPEAT = 0x2901;
    public static final int GL_CLAMP_TO_EDGE = 0x812F;
    public static final int GL_MIRRORED_REPEAT = 0x8370;
    public static final int GL_FLOAT_VEC2 = 0x8B50;
    public static final int GL_FLOAT_VEC3 = 0x8B51;
    public static final int GL_FLOAT_VEC4 = 0x8B52;
    public static final int GL_INT_VEC2 = 0x8B53;
    public static final int GL_INT_VEC3 = 0x8B54;
    public static final int GL_INT_VEC4 = 0x8B55;
    public static final int GL_BOOL = 0x8B56;
    public static final int GL_BOOL_VEC2 = 0x8B57;
    public static final int GL_BOOL_VEC3 = 0x8B58;
    public static final int GL_BOOL_VEC4 = 0x8B59;
    public static final int GL_FLOAT_MAT2 = 0x8B5A;
    public static final int GL_FLOAT_MAT3 = 0x8B5B;
    public static final int GL_FLOAT_MAT4 = 0x8B5C;
    public static final int GL_SAMPLER_2D = 0x8B5E;
    public static final int GL_SAMPLER_CUBE = 0x8B60;
    public static final int GL_VERTEX_ATTRIB_ARRAY_ENABLED = 0x8622;
    public static final int GL_VERTEX_ATTRIB_ARRAY_SIZE = 0x8623;
    public static final int GL_VERTEX_ATTRIB_ARRAY_STRIDE = 0x8624;
    public static final int GL_VERTEX_ATTRIB_ARRAY_TYPE = 0x8625;
    public static final int GL_VERTEX_ATTRIB_ARRAY_NORMALIZED = 0x886A;
    public static final int GL_VERTEX_ATTRIB_ARRAY_POINTER = 0x8645;
    public static final int GL_VERTEX_ATTRIB_ARRAY_BUFFER_BINDING = 0x889F;
    public static final int GL_COMPILE_STATUS = 0x8B81;
    public static final int GL_INFO_LOG_LENGTH = 0x8B84;
    public static final int GL_SHADER_SOURCE_LENGTH = 0x8B88;
    public static final int GL_FRAMEBUFFER = 0x8D40;
    public static final int GL_RENDERBUFFER = 0x8D41;
    public static final int GL_RENDERBUFFER_WIDTH = 0x8D42;
    public static final int GL_RENDERBUFFER_HEIGHT = 0x8D43;
    public static final int GL_RENDERBUFFER_INTERNAL_FORMAT = 0x8D44;
    public static final int GL_RENDERBUFFER_RED_SIZE = 0x8D50;
    public static final int GL_RENDERBUFFER_GREEN_SIZE = 0x8D51;
    public static final int GL_RENDERBUFFER_BLUE_SIZE = 0x8D52;
    public static final int GL_RENDERBUFFER_ALPHA_SIZE = 0x8D53;
    public static final int GL_RENDERBUFFER_DEPTH_SIZE = 0x8D54;
    public static final int GL_RENDERBUFFER_STENCIL_SIZE = 0x8D55;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_OBJECT_TYPE = 0x8CD0;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_OBJECT_NAME = 0x8CD1;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LEVEL = 0x8CD2;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_CUBE_MAP_FACE = 0x8CD3;
    public static final int GL_COLOR_ATTACHMENT0 = 0x8CE0;
    public static final int GL_DEPTH_ATTACHMENT = 0x8D00;
    public static final int GL_STENCIL_ATTACHMENT = 0x8D20;
    public static final int GL_NONE = 0x0;
    public static final int GL_FRAMEBUFFER_COMPLETE = 0x8CD5;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT = 0x8CD6;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT = 0x8CD7;
    public static final int GL_FRAMEBUFFER_UNSUPPORTED = 0x8CDD;
    public static final int GL_FRAMEBUFFER_BINDING = 0x8CA6;
    public static final int GL_RENDERBUFFER_BINDING = 0x8CA7;
    public static final int GL_MAX_RENDERBUFFER_SIZE = 0x84E8;
    public static final int GL_INVALID_FRAMEBUFFER_OPERATION = 0x506;
    
    public static void glActiveTexture(int texture) {
        PlatformBackend.backend.glActiveTexture_P(texture);
    }

    public static void glAttachShader(int program, int shader) {
        PlatformBackend.backend.glAttachShader_P(program, shader);
    }

    public static void glBindAttribLocation(int program, int index, String name) {
        PlatformBackend.backend.glBindAttribLocation_P(program, index, name);
    }

    public static void glBindBuffer(int target, int buffer) {
        PlatformBackend.backend.glBindBuffer_P(target, buffer);
    }

    public static void glBindFramebuffer(int target, int framebuffer) {
        PlatformBackend.backend.glBindFramebuffer_P(target, framebuffer);
    }

    public static void glBindRenderbuffer(int target, int renderbuffer) {
        PlatformBackend.backend.glBindRenderbuffer_P(target, renderbuffer);
    }

    public static void glBindTexture(int target, int texture) {
        PlatformBackend.backend.glBindTexture_P(target, texture);
    }

    public static void glBlendColor(float red, float green, float blue, float alpha) {
        PlatformBackend.backend.glBlendColor_P(red, green, blue, alpha);
    }

    public static void glBlendEquation(int mode) {
        PlatformBackend.backend.glBlendEquation_P(mode);
    }

    public static void glBlendFunc(int sFactor, int dFactor) {
        PlatformBackend.backend.glBlendFunc_P(sFactor, dFactor);
    }

    public static void glBufferData(int target, FloatBuffer data, int usage) {
        PlatformBackend.backend.glBufferData_P(target, data, usage);
    }

    public static void glBufferData(int target, IntBuffer data, int usage) {
        PlatformBackend.backend.glBufferData_P(target, data, usage);
    }

    public static void glBufferData(int target, ByteBuffer data, int usage) {
        PlatformBackend.backend.glBufferData_P(target, data, usage);
    }

    public static void glBufferSubData(int target, int offset, FloatBuffer data) {
        PlatformBackend.backend.glBufferSubData_P(target, offset, data);
    }

    public static void glBufferSubData(int target, int offset, IntBuffer data) {
        PlatformBackend.backend.glBufferSubData_P(target, offset, data);
    }

    public static void glBufferSubData(int target, int offset, ByteBuffer data) {
        PlatformBackend.backend.glBufferSubData_P(target, offset, data);
    }

    public static int glCheckFramebufferStatus(int target) {
        return PlatformBackend.backend.glCheckFramebufferStatus_P(target);
    }

    public static void glClear(int mask) {
        PlatformBackend.backend.glClear_P(mask);
    }

    public static void glClearColor(float red, float green, float blue, float alpha) {
        PlatformBackend.backend.glClearColor_P(red, green, blue, alpha);
    }

    public static void glClearDepth(float depth) {
        PlatformBackend.backend.glClearDepth_P(depth);
    }

    public static void glClearStencil(int stencil) {
        PlatformBackend.backend.glClearStencil_P(stencil);
    }

    public static void glColorMask(boolean red, boolean green, boolean blue, boolean alpha) {
        PlatformBackend.backend.glColorMask_P(red, green, blue, alpha);
    }

    public static void glCompileShader(int shader) {
        PlatformBackend.backend.glCompileShader_P(shader);
    }
    
    public static void glCompressedTexImage2D_P(int target, int level, int internalFormat, int width, int height, int border, ByteBuffer data) {
        PlatformBackend.backend.glCompressedTexImage2D_P(target, level, internalFormat, width, height, border, data);
    }
    
    public static void glCompressedTexSubImage2D_P(int target, int level, int xOffset, int yOffset, int width, int height, int format, ByteBuffer data) {
        PlatformBackend.backend.glCompressedTexSubImage2D_P(target, level, xOffset, yOffset, width, height, format, data);
    }

    public static void glCopyTexImage2D(int target, int level, int internalForamt, int x, int y, int width, int height, int border) {
        PlatformBackend.backend.glCopyTexImage2D_P(target, level, internalForamt, x, y, width, height, border);
    }

    public static void glCopyTexSubImage2D(int target, int level, int xOffset, int yOffset, int x, int y, int width, int height) {
        PlatformBackend.backend.glCopyTexSubImage2D_P(target, level, xOffset, yOffset, x, y, width, height);
    }

    public static int glCreateProgram() {
        return PlatformBackend.backend.glCreateProgram_P();
    }

    public static int glCreateShader(int type) {
        return PlatformBackend.backend.glCreateShader_P(type);
    }

    public static void glCullFace(int mode) {
        PlatformBackend.backend.glCullFace_P(mode);
    }

    public static void glDeleteBuffers(IntBuffer buffers) {
        PlatformBackend.backend.glDeleteBuffers_P(buffers);
    }

    public static void glDeleteFramebuffers(IntBuffer buffers) {
        PlatformBackend.backend.glDeleteFramebuffers_P(buffers);
    }

    public static void glDeleteProgram(int program) {
        PlatformBackend.backend.glDeleteProgram_P(program);
    }

    public static void glDeleteRenderbuffers(IntBuffer buffers) {
        PlatformBackend.backend.glDeleteRenderbuffers_P(buffers);
    }

    public static void glDeleteShader(int shader) {
        PlatformBackend.backend.glDeleteShader_P(shader);
    }

    public static void glDeleteTextures(IntBuffer arg1) {
        PlatformBackend.backend.glDeleteTextures_P(arg1);
    }

    public static void glDepthFunc(int func) {
        PlatformBackend.backend.glDepthFunc_P(func);
    }

    public static void glDepthMask(boolean flag) {
        PlatformBackend.backend.glDepthMask_P(flag);
    }

    public static void glDepthRange(float nearVal, float farVal) {
        PlatformBackend.backend.glDepthRange_P(nearVal, farVal);
    }

    public static void glDetachShader(int program, int shader) {
        PlatformBackend.backend.glDetachShader_P(program, shader);
    }

    public static void glDisable(int cap) {
        PlatformBackend.backend.glDisable_P(cap);
    }

    public static void glDisableVertexAttribArray(int index) {
        PlatformBackend.backend.glDisableVertexAttribArray_P(index);
    }

    public static void glDrawArrays(int mode, int first, int count) {
        PlatformBackend.backend.glDrawArrays_P(mode, first, count);
    }

    public static void glDrawElements(int mode, int count, int type, int bufferOffset) {
        PlatformBackend.backend.glDrawElements_P(mode, count, type, bufferOffset);
    }

    public static void glDrawElements(int mode, IntBuffer buffer) {
        PlatformBackend.backend.glDrawElements_P(mode, buffer);
    }

    public static void glDrawElements(int mode, ByteBuffer buffer) {
        PlatformBackend.backend.glDrawElements_P(mode, buffer);
    }

    public static void glEnable(int cap) {
        PlatformBackend.backend.glEnable_P(cap);
    }

    public static void glEnableVertexAttribArray(int index) {
        PlatformBackend.backend.glEnableVertexAttribArray_P(index);
    }

    public static void glFinish() {
        PlatformBackend.backend.glFinish_P();
    }

    public static void glFlush() {
        PlatformBackend.backend.glFlush_P();
    }

    public static void glFramebufferRenderbuffer(int target, int attachment, int renderbufferTarget, int renderbuffer) {
        PlatformBackend.backend.glFramebufferRenderbuffer_P(target, attachment, renderbufferTarget, renderbuffer);
    }

    public static void glFramebufferTexture2D(int target, int attachment, int texTarget, int texture, int level) {
        PlatformBackend.backend.glFramebufferTexture2D_P(target, attachment, texTarget, texture, level);
    }

    public static void glFrontFace(int mode) {
        PlatformBackend.backend.glFrontFace_P(mode);
    }

    public static void glGenBuffers(IntBuffer buffers) {
        PlatformBackend.backend.glGenBuffers_P(buffers);
    }

    public static void glGenFramebuffers(IntBuffer buffers) {
        PlatformBackend.backend.glGenFramebuffers_P(buffers);
    }
    
    public static void glGenRenderbuffers(IntBuffer buffers) {
        PlatformBackend.backend.glGenRenderbuffers_P(buffers);
    }

    public static void glGenTextures(IntBuffer buffers) {
        PlatformBackend.backend.glGenTextures_P(buffers);
    }

    public static String glGetActiveAttrib(int program, int index, IntBuffer length, IntBuffer type) {
        return PlatformBackend.backend.glGetActiveAttrib_P(program, index, length, type);
    }

    public static void glGetAttachedShaders(int program, IntBuffer count, IntBuffer shaders) {
        PlatformBackend.backend.glGetAttachedShaders_P(program, count, shaders);
    }
    
    public static int glGetAttribLocation(int program, String name) {
        return PlatformBackend.backend.glGetAttribLocation_P(program, name);
    }

    public static void glGetBooleanv(int pname, ByteBuffer params) {
        PlatformBackend.backend.glGetBooleanv_P(pname, params);
    }
    
    public static void glGetBufferParameteriv(int target, int value, IntBuffer data) {
        PlatformBackend.backend.glGetBufferParameteriv_P(target, value, data);
    }

    public static int glGetError() {
        return PlatformBackend.backend.glGetError_P();
    }
    
    public static void glGetFloatv(int pname, FloatBuffer params) {
        PlatformBackend.backend.glGetFloatv_P(pname, params);
    }

    public static void glGetFramebufferAttachmentParameteriv(int target, int attatchment, int pname, IntBuffer params) {
        PlatformBackend.backend.glGetFramebufferAttachmentParameteriv_P(target, attatchment, pname, params);
    }

    public static void glGetIntegerv(int pname, IntBuffer params) {
        PlatformBackend.backend.glGetIntegerv_P(pname, params);
    }

    public static void glGetProgramiv(int program, int pname, IntBuffer params) {
        PlatformBackend.backend.glGetProgramiv_P(program, pname, params);
    }

    public static String glGetProgramInfoLog(int program) {
        return PlatformBackend.backend.glGetProgramInfoLog_P(program);
    }

    public static void glGetRenderbufferParameteriv(int target, int pname, IntBuffer params) {
        PlatformBackend.backend.glGetRenderbufferParameteriv_P(target, pname, params);
    }

    public static void glGetShaderiv(int shader, int pname, IntBuffer params) {
        PlatformBackend.backend.glGetShaderiv_P(shader, pname, params);
    }

    public static String glGetShaderInfoLog(int shader) {
        return PlatformBackend.backend.glGetShaderInfoLog_P(shader);
    }

    public static String glGetShaderSource(int shader) {
        return PlatformBackend.backend.glGetShaderSource_P(shader);
    }

    public static String glGetString(int pname) {
        return PlatformBackend.backend.glGetString_P(pname);
    }

    public static void glGetTexParameterfv(int target, int pname, FloatBuffer params) {
        PlatformBackend.backend.glGetTexParameterfv_P(target, pname, params);
    }

    public static void glGetTexParameteriv(int target, int pname, IntBuffer params) {
        PlatformBackend.backend.glGetTexParameteriv_P(target, pname, params);
    }

    public static void glGetUniformfv(int program, int location, FloatBuffer params) {
        PlatformBackend.backend.glGetUniformfv_P(program, location, params);
    }

    public static void glGetUniformiv(int program, int location, IntBuffer params) {
        PlatformBackend.backend.glGetUniformiv_P(program, location, params);
    }

    public static int glGetUniformLocation(int program, String name) {
        return PlatformBackend.backend.glGetUniformLocation_P(program, name);
    }

    public static void glGetVertexAttribfv(int index, int pname, FloatBuffer params) {
        PlatformBackend.backend.glGetVertexAttribfv_P(index, pname, params);
    }

    public static void glGetVertexAttribiv(int index, int pname, IntBuffer params) {
        PlatformBackend.backend.glGetVertexAttribiv_P(index, pname, params);
    }

    public static void glHint(int target, int mode) {
        PlatformBackend.backend.glHint_P(target, mode);
    }

    public static boolean glIsBuffer(int buffer) {
        return PlatformBackend.backend.glIsBuffer_P(buffer);
    }

    public static boolean glIsEnabled(int cap) {
        return PlatformBackend.backend.glIsEnabled_P(cap);
    }

    public static boolean glIsFramebuffer(int framebuffer) {
        return PlatformBackend.backend.glIsFramebuffer_P(framebuffer);
    }

    public static boolean glIsProgram(int program) {
        return PlatformBackend.backend.glIsProgram_P(program);
    }

    public static boolean glIsRenderbuffer(int renderbuffer) {
        return PlatformBackend.backend.glIsRenderbuffer_P(renderbuffer);
    }

    public static boolean glIsShader(int shader) {
        return PlatformBackend.backend.glIsShader_P(shader);
    }

    public static boolean glIsTexture(int texture) {
        return PlatformBackend.backend.glIsTexture_P(texture);
    }

    public static void glLineWidth(float width) {
        PlatformBackend.backend.glLineWidth_P(width);
    }

    public static void glLinkProgram(int program) {
        PlatformBackend.backend.glLinkProgram_P(program);
    }

    public static void glPixelStorei(int pname, int param) {
        PlatformBackend.backend.glPixelStorei_P(pname, param);
    }

    public static void glPolygonOffset(float factor, float units) {
        PlatformBackend.backend.glPolygonOffset_P(factor, units);
    }

    public static void glReadPixels(int x, int y, int width, int height, int format, int type, FloatBuffer data) {
        PlatformBackend.backend.glReadPixels_P(x, y, width, height, format, type, data);
    }

    public static void glReadPixels(int x, int y, int width, int height, int format, int type, ByteBuffer data) {
        PlatformBackend.backend.glReadPixels_P(x, y, width, height, format, type, data);
    }

    public static void glReadPixels(int x, int y, int width, int height, int format, int type, IntBuffer data) {
        PlatformBackend.backend.glReadPixels_P(x, y, width, height, format, type, data);
    }

    public static void glRenderbufferStorage(int target, int internalFormat, int width, int height) {
        PlatformBackend.backend.glRenderbufferStorage_P(target, internalFormat, width, height);
    }

    public static void glSampleCoverage(float value, boolean invert) {
        PlatformBackend.backend.glSampleCoverage_P(value, invert);
    }

    public static void glScissor(int x, int y, int width, int height) {
        PlatformBackend.backend.glScissor_P(x, y, width, height);
    }

    public static void glShaderSource(int shader, String source) {
        PlatformBackend.backend.glShaderSource_P(shader, source);
    }

    public static void glStencilFunc(int func, int ref, int mask) {
        PlatformBackend.backend.glStencilFunc_P(func, ref, mask);
    }

    public static void glStencilMask(int mask) {
        PlatformBackend.backend.glStencilMask_P(mask);
    }

    public static void glStencilOp(int sfail, int dpfail, int dppass) {
        PlatformBackend.backend.glStencilOp_P(sfail, dpfail, dppass);
    }

    public static void glTexImage2D(int target, int level, int internalFormat, int width, int height, int border, int format, int type, FloatBuffer data) {
        PlatformBackend.backend.glTexImage2D_P(target, level, internalFormat, width, height, border, format, type, data);
    }

    public static void glTexImage2D(int target, int level, int internalFormat, int width, int height, int border, int format, int type, IntBuffer data) {
        PlatformBackend.backend.glTexImage2D_P(target, level, internalFormat, width, height, border, format, type, data);
    }

    public static void glTexImage2D(int target, int level, int internalFormat, int width, int height, int border, int format, int type, ByteBuffer data) {
        PlatformBackend.backend.glTexImage2D_P(target, level, internalFormat, width, height, border, format, type, data);
    }

    public static void glTexParameterf(int target, int pname, float param) {
        PlatformBackend.backend.glTexParameterf_P(target, pname, param);
    }

    public static void glTexParameterfv(int target, int pname, FloatBuffer param) {
        PlatformBackend.backend.glTexParameterfv_P(target, pname, param);
    }

    public static void glTexParameteri(int target, int pname, int param) {
        PlatformBackend.backend.glTexParameteri_P(target, pname, param);
    }

    public static void glTexParameteriv(int target, int pname, IntBuffer param) {
        PlatformBackend.backend.glTexParameteriv_P(target, pname, param);
    }

    public static void glTexSubImage2D(int target, int level, int xOffset, int yOffset, int width, int height, int format, int type, FloatBuffer data) {
        PlatformBackend.backend.glTexSubImage2D_P(target, level, xOffset, yOffset, width, height, format, type, data);
    }

    public static void glTexSubImage2D(int target, int level, int xOffset, int yOffset, int width, int height, int format, int type, IntBuffer data) {
        PlatformBackend.backend.glTexSubImage2D_P(target, level, xOffset, yOffset, width, height, format, type, data);
    }

    public static void glTexSubImage2D(int target, int level, int xOffset, int yOffset, int width, int height, int format, int type, ByteBuffer data) {
        PlatformBackend.backend.glTexSubImage2D_P(target, level, xOffset, yOffset, width, height, format, type, data);
    }

    public static void glUniform1f(int location, float v1) {
        PlatformBackend.backend.glUniform1f_P(location, v1);
    }

    public static void glUniform1fv(int location, FloatBuffer value) {
        PlatformBackend.backend.glUniform1fv_P(location, value);
    }

    public static void glUniform1i(int location, int v1) {
        PlatformBackend.backend.glUniform1i_P(location, v1);
    }

    public static void glUniform1iv(int location, IntBuffer value) {
        PlatformBackend.backend.glUniform1iv_P(location, value);
    }

    public static void glUniform2f(int location, float v1, float v2) {
        PlatformBackend.backend.glUniform2f_P(location, v1, v2);
    }

    public static void glUniform2fv(int location, FloatBuffer value) {
        PlatformBackend.backend.glUniform2fv_P(location, value);
    }

    public static void glUniform2i(int location, int v1, int v2) {
        PlatformBackend.backend.glUniform2i_P(location, v1, v2);
    }

    public static void glUniform2iv(int location, IntBuffer value) {
        PlatformBackend.backend.glUniform2iv_P(location, value);
    }

    public static void glUniform3f(int location, float v1, float v2, float v3) {
        PlatformBackend.backend.glUniform3f_P(location, v1, v2, v3);
    }

    public static void glUniform3fv(int location, FloatBuffer value) {
        PlatformBackend.backend.glUniform3fv_P(location, value);
    }

    public static void glUniform3i(int location, int v1, int v2, int v3) {
        PlatformBackend.backend.glUniform3i_P(location, v1, v2, v3);
    }

    public static void glUniform3iv(int location, IntBuffer value) {
        PlatformBackend.backend.glUniform3iv_P(location, value);
    }

    public static void glUniform4f(int location, float v1, float v2, float v3, float v4) {
        PlatformBackend.backend.glUniform4f_P(location, v1, v2, v3, v4);
    }

    public static void glUniform4fv(int location, FloatBuffer value) {
        PlatformBackend.backend.glUniform4fv_P(location, value);
    }

    public static void glUniform4i(int location, int v1, int v2, int v3, int v4) {
        PlatformBackend.backend.glUniform4i_P(location, v1, v2, v3, v4);
    }

    public static void glUniform4iv(int location, IntBuffer value) {
        PlatformBackend.backend.glUniform4iv_P(location, value);
    }

    public static void glUniformMatrix2fv(int location, boolean transpose, FloatBuffer value) {
        PlatformBackend.backend.glUniformMatrix2fv_P(location, transpose, value);
    }

    public static void glUniformMatrix3fv(int location, boolean transpose, FloatBuffer value) {
        PlatformBackend.backend.glUniformMatrix3fv_P(location, transpose, value);
    }

    public static void glUniformMatrix4fv(int location, boolean transpose, FloatBuffer value) {
        PlatformBackend.backend.glUniformMatrix4fv_P(location, transpose, value);
    }

    public static void glUseProgram(int program) {
        PlatformBackend.backend.glUseProgram_P(program);
    }

    public static void glValidateProgram(int program) {
        PlatformBackend.backend.glValidateProgram_P(program);
    }

    public static void glVertexAttrib1f(int index, float v1) {
        PlatformBackend.backend.glVertexAttrib1f_P(index, v1);
    }

    public static void glVertexAttrib2f(int index, float v1, float v2) {
        PlatformBackend.backend.glVertexAttrib2f_P(index, v1, v2);
    }

    public static void glVertexAttrib3f(int index, float v1, float v2, float v3) {
        PlatformBackend.backend.glVertexAttrib3f_P(index, v1, v2, v3);
    }

    public static void glVertexAttrib4f(int index, float v1, float v2, float v3, float v4) {
        PlatformBackend.backend.glVertexAttrib4f_P(index, v1, v2, v3, v4);
    }

    public static void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, int bufferOffset) {
        PlatformBackend.backend.glVertexAttribPointer_P(index, size, type, normalized, stride, bufferOffset);
    }

    public static void glVertexAttribPointer(int index, int size, boolean normalized, int stride, FloatBuffer buffer) {
        PlatformBackend.backend.glVertexAttribPointer_P(index, size, normalized, stride, buffer);
    }

    public static void glVertexAttribPointer(int index, int size, boolean unsigned, boolean normalized, int stride, IntBuffer buffer) {
        PlatformBackend.backend.glVertexAttribPointer_P(index, size, unsigned, normalized, stride, buffer);
    }

    public static void glVertexAttribPointer(int index, int size, boolean unsigned, boolean normalized, int stride, ByteBuffer buffer) {
        PlatformBackend.backend.glVertexAttribPointer_P(index, size, unsigned, normalized, stride, buffer);
    }

    public static void glViewport(int x, int y, int width, int height) {
        PlatformBackend.backend.glViewport_P(x, y, width, height);
    }
    
    
}
