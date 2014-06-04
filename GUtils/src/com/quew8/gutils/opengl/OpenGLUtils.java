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
    
}
