package com.quew8.gutils.opengl.texture;

/**
 * 
 * @author Quew8
 */
public interface LoadedImage {
    public int getWidth();
    public int getHeight();
    public boolean hasAlpha();
    public void unload();
}
