package com.quew8.gutils.opengl.shaders.glsl.formatting;

/**
 *
 * @author Quew8
 */
public class ReplaceStruct {
    public final String replace;
    public final int startIndex;
    public final int endIndex;

    public ReplaceStruct(String replace, int startIndex, int endIndex) {
        this.replace = replace;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }
    
}
