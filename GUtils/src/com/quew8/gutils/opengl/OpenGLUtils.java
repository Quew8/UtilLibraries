package com.quew8.gutils.opengl;

import com.quew8.gutils.BufferUtils;
import com.quew8.gutils.bufferreading.BufferReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.IntBuffer;
import static com.quew8.gutils.opengl.OpenGL.*;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public class OpenGLUtils {
    
    private OpenGLUtils() {
        
    }
    
    public static ByteBuffer getBufferData(int name, int targetEnum, int targetBindingEnum) {
        IntBuffer ib = BufferUtils.createIntBuffer(1);
        glGetIntegerv(targetBindingEnum, ib);
        int currentBuffer = ib.get(0);
        glBindBuffer(targetEnum, name);
        glGetBufferParameteriv(targetEnum, GL_BUFFER_SIZE, ib);
        int size = ib.get(0);
        ByteBuffer bb = BufferUtils.createByteBuffer(size);
        OpenGL.glGetBufferSubData(targetEnum, 0, bb);
        glBindBuffer(targetEnum, currentBuffer);
        return bb;
    }
    
    public static String getArrayBufferDataAsFloat(int name) {
        IntBuffer ib = BufferUtils.createIntBuffer(1);
        glGetIntegerv(GL_ARRAY_BUFFER_BINDING, ib);
        int currentBuffer = ib.get(0);
        glBindBuffer(GL_ARRAY_BUFFER, name);
        glGetBufferParameteriv(GL_ARRAY_BUFFER, GL_BUFFER_SIZE, ib);
        int size = ib.get(0);
        ByteBuffer bb = BufferUtils.createByteBuffer(size);
        OpenGL.glGetBufferSubData(GL_ARRAY_BUFFER, 0, bb);
        glBindBuffer(GL_ARRAY_BUFFER, currentBuffer);
        return BufferUtils.toString(bb.asFloatBuffer(), Integer.MAX_VALUE);
    }
    
    public static String getElementBufferDataAsInt(int name) {
        IntBuffer ib = BufferUtils.createIntBuffer(1);
        glGetIntegerv(GL_ELEMENT_ARRAY_BUFFER_BINDING, ib);
        int currentBuffer = ib.get(0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, name);
        glGetBufferParameteriv(GL_ELEMENT_ARRAY_BUFFER, GL_BUFFER_SIZE, ib);
        int size = ib.get(0);
        ByteBuffer bb = BufferUtils.createByteBuffer(size);
        OpenGL.glGetBufferSubData(GL_ELEMENT_ARRAY_BUFFER, 0, bb);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, currentBuffer);
        return BufferUtils.toString(bb.asIntBuffer(), Integer.MAX_VALUE);
    }
    
    public static String getViewportInfo() {
        IntBuffer ib = BufferUtils.createIntBuffer(4);
        glGetIntegerv(GL_VIEWPORT, ib);
        return "Viewport{" + ib.get(0) + ", " + ib.get(1) + ", " + ib.get(2) + ", " + ib.get(3) + "}";
    }
    
    public static String getPointerInfo() {
        IntBuffer ib = BufferUtils.createIntBuffer(1);
        glGetIntegerv(GL_MAX_VERTEX_ATTRIBS, ib);
        int n = ib.get(0);
        String s = getElementBufferBinding();
        for(int i = 0; i < n; i++) {
            if(isVertexAttribEnabled(i)) {
                s += "\n" + getVertexAttribPointerString(i);
            }
        }
        return s;
    }
    
    public static String getElementBufferBinding() {
        IntBuffer ib = BufferUtils.createIntBuffer(1);
        glGetIntegerv(GL_ELEMENT_ARRAY_BUFFER_BINDING, ib);
        return "Element Buffer Binding " + ib.get(0);
    }
    
    public static boolean isVertexAttribEnabled(int index) {
        IntBuffer ib = BufferUtils.createIntBuffer(1);
        glGetVertexAttribiv(index, GL_VERTEX_ATTRIB_ARRAY_ENABLED, ib);
        return ib.get(0) != 0;
    }
    
    public static String getVertexAttribPointerString(int index) {
        IntBuffer ib = BufferUtils.createIntBuffer(1);
        glGetVertexAttribiv(index, GL_VERTEX_ATTRIB_ARRAY_ENABLED, ib);
        boolean enabled = ib.get(0) != 0;
        glGetVertexAttribiv(index, GL_VERTEX_ATTRIB_ARRAY_SIZE, ib);
        int size = ib.get(0);
        glGetVertexAttribiv(index, GL_VERTEX_ATTRIB_ARRAY_STRIDE, ib);
        int stride = ib.get(0);
        glGetVertexAttribiv(index, GL_VERTEX_ATTRIB_ARRAY_TYPE, ib);
        String type;
        switch(ib.get(0)) {
            case GL_BYTE: type = "GL_BYTE"; break;
            case GL_UNSIGNED_BYTE: type = "GL_UNSIGNED_BYTE"; break;
            case GL_SHORT: type = "GL_SHORT"; break;
            case GL_UNSIGNED_SHORT: type = "GL_UNSIGNED_SHORT"; break;
            case GL_INT: type = "GL_INT"; break;
            case GL_UNSIGNED_INT: type = "GL_UNSIGNED_INT"; break;
            case GL_FLOAT: type = "GL_FLOAT"; break;
            case GL_DOUBLE: type = "GL_DOUBLE"; break;
            default: type = "UNKNOWN ENUM " + ib.get(0);
        }
        glGetVertexAttribiv(index, GL_VERTEX_ATTRIB_ARRAY_NORMALIZED, ib);
        boolean normalized = ib.get(0) != 0;
        glGetVertexAttribiv(index, GL_VERTEX_ATTRIB_ARRAY_BUFFER_BINDING, ib);
        int binding = ib.get(0);
        return "Vertex Pointer " + index + " Enabled " + enabled + ", Size " + size + 
                ", Stride " + stride + ", Type " + type + ", Normalized " + 
                normalized + ", Binding " + binding;
    }
    
    public static String toOpenGLEnum(int code) {
        try {
            Field[] fields = OpenGL.class.getDeclaredFields();
            for(int i = 0; i < fields.length; i++) {
                if((!fields[i].getName().equals("GL_TRUE")) && 
                        Modifier.isStatic(fields[i].getModifiers()) && 
                        (fields[i].getInt(null) == code) ) {
                    return fields[i].getName();
                }
            }
            return "Unknown GL Enum";
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static String toOpenGLMask(int mask) {
        try {
            String s = "";
            Field[] fields = OpenGL.class.getDeclaredFields();
            for(int i = 0; i < fields.length; i++) {
                if(Modifier.isStatic(fields[i].getModifiers()) && (fields[i].getName().endsWith("_BIT")) ) {
                    if((fields[i].getInt(null) & mask) != 0) {
                        s += s.isEmpty() ? fields[i].getName() : " | " + fields[i].getName();
                    }
                }
            }
            return !s.isEmpty() ? s : "NO_BITFIELDS";
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }
}
