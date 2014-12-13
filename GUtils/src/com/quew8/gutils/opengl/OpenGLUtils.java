package com.quew8.gutils.opengl;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 *
 * @author Quew8
 */
public class OpenGLUtils {
    
    private OpenGLUtils() {
        
    }
    
    public static String toOpenGLString(int code) {
        try {
            Field[] fields = OpenGL.class.getDeclaredFields();
            for(int i = 0; i < fields.length; i++) {
                if(Modifier.isStatic(fields[i].getModifiers()) && (fields[i].getInt(null) == code) ) {
                    return fields[i].getName();
                }
            }
            return "Unknown GL Enum";
        } catch (    IllegalArgumentException | IllegalAccessException ex) {
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
        } catch (    IllegalArgumentException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }
}
