package com.quew8.gutils.opengl.texture;

import static com.quew8.gutils.opengl.OpenGL.*;

public class TextureParams {
    private final int[] pnames, params;
    private final Runnable runnable;
    
	public TextureParams(int[] pnames, int[] params, Runnable runnable) {
		if(pnames.length != params.length) {
			throw new IllegalArgumentException("Number of pnames doesn't match number of params");
		}
		this.pnames = pnames;
		this.params = params;
		this.runnable = runnable;
	}
	
	public void run() {
		if(runnable != null) {
			runnable.run();
		}
	}
	
	public int getNParams() {
		return pnames.length;
	}
	
	public int getPName(int i) {
		return pnames[i];
	}
	
	public int getParam(int i) {
		return params[i];
	}
	
	public static TextureParams create(int minFilter, int magFilter, Runnable runnable) {
		return new TextureParams(
				new int[]{GL_TEXTURE_MIN_FILTER, GL_TEXTURE_MAG_FILTER},
				new int[]{minFilter, magFilter},
				runnable
				);
	}
	
	public static TextureParams create(int minFilter, int magFilter) {
		return create(minFilter, magFilter, null);
	}
	
	public static TextureParams create(int minFilter, Runnable runnable) {
		return create(minFilter, GL_LINEAR, runnable);
	}
	
	public static TextureParams create(int minFilter) {
		return create(minFilter, null);
	}
	
	public static TextureParams create(Runnable runnable) {
		return create(GL_LINEAR, runnable);
	}
	
	public static TextureParams create() {
		return create(null);
	}
}
