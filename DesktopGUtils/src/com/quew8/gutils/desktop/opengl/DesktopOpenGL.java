package com.quew8.gutils.desktop.opengl;

import com.quew8.gutils.opengl.OpenGL;
import java.nio.ByteBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

/**
 *
 * @author Quew8
 */
public abstract class DesktopOpenGL extends OpenGL {
    public static final int GL_ACCUM_BUFFER_BIT = 0x200;
    public static final int GL_ALL_ATTRIB_BITS = 0xFFFFF;
    public static final int GL_CLIENT_PIXEL_STORE_BIT	= 0x1;
    public static final int GL_CLIENT_VERTEX_ARRAY_BIT = 0x2;
    public static final int GL_CURRENT_BIT = 0x1;
    public static final int GL_ENABLE_BIT = 0x2000;
    public static final int GL_EVAL_BIT = 0x10000;
    public static final int GL_FILL = 0x1B02;
    public static final int GL_FOG_BIT = 0x80;
    public static final int GL_HINT_BIT = 0x8000;
    public static final int GL_LIGHTING_BIT = 0x40;
    public static final int GL_LINE = 0x1B01;
    public static final int GL_LINE_BIT = 0x4;
    public static final int GL_LIST_BIT = 0x20000;
    public static final int GL_MULTISAMPLE_BIT = 0x20000000;
    public static final int GL_PIXEL_MODE_BIT = 0x20;
    public static final int GL_PIXEL_PACK_BUFFER = 0x88EB;
    public static final int GL_PIXEL_PACK_BUFFER_BINDING = 0x88ED;
    public static final int GL_PIXEL_UNPACK_BUFFER = 0x88EC;
    public static final int GL_PIXEL_UNPACK_BUFFER_BINDING = 0x88EF;
    public static final int GL_POINT = 0x1B00;
    public static final int GL_POINT_BIT = 0x2;
    public static final int GL_POLYGON_BIT = 0x8;
    public static final int GL_POLYGON_STIPPLE_BIT = 0x10;
    public static final int GL_SCISSOR_BIT = 0x80000;
    public static final int GL_TEXTURE_BIT = 0x40000;
    public static final int GL_TRANSFORM_BIT = 0x1000;
    public static final int GL_VERTEX_PROGRAM_POINT_SIZE = 0x8642;
    public static final int GL_VERTEX_PROGRAM_TWO_SIDE = 0x8643;
    public static final int GL_VIEWPORT_BIT = 0x800;
    
    public static void glBitmap(int width, int height, float xOrig, float yOrig, float xMove, float yMove, ByteBuffer data) {
        GL11.glBitmap(width, height, xOrig, yOrig, xMove, yMove, data);
    }
    
    public static void glBitmap(int width, int height, float xOrig, float yOrig, float xMove, float yMove, long bufferOffset) {
        GL11.glBitmap(width, height, xOrig, yOrig, xMove, yMove, bufferOffset);
    }
    
    public static void glMapBuffer(int target, int access, ByteBuffer oldBuffer) {
        GL15.glMapBuffer(target, access, oldBuffer);
    }
    
    public static void glMapBuffer(int target, int access, long length, ByteBuffer oldBuffer) {
        GL15.glMapBuffer(target, access, length, oldBuffer);
    }
    
    public static void glPolygonMode(int face, int mode) {
        GL11.glPolygonMode(face, mode);
    }
    
    public static void glPopAttrib() {
        GL11.glPopAttrib();
    }
    
    public static void glPopClientAttrib() {
        GL11.glPopClientAttrib();
    }
    
    public static void glPushAttrib(int mask) {
        GL11.glPushAttrib(mask);
    }
    
    public static void glPushClientAttrib(int mask) {
        GL11.glPushClientAttrib(mask);
    }
    
    public static void glUnmapBuffer(int target) {
        GL15.glUnmapBuffer(target);
    }
    
    public static void glRasterPos2f(float x, float y) {
        GL11.glRasterPos2f(x, y);
    }
    
    public static void glRasterPos3f(float x, float y, float z) {
        GL11.glRasterPos3f(x, y, z);
    }
    
    public static void glRasterPos4f(float x, float y, float z, float w) {
        GL11.glRasterPos4f(x, y, z, w);
    }
}
