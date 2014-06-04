package com.quew8.gutils.opengl.texture;

public interface LoadedImage {
	public int getWidth();
	public int getHeight();
	public boolean hasAlpha();
	public void unload();
}
