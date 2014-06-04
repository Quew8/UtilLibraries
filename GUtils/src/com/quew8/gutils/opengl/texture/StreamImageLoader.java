package com.quew8.gutils.opengl.texture;

import java.io.InputStream;

import com.quew8.gutils.PlatformBackend;

/**
 *
 * @author Quew8
 */
class StreamImageLoader implements ImageLoader {
	private final InputStream is;
	private final boolean flip;

	public StreamImageLoader(InputStream is, boolean flip) {
		this.is = is;
		this.flip = flip;
	}
	
	public StreamImageLoader(InputStream is) {
		this(is, false);
	}

	@Override
	public LoadedImage getLoadedImage() {
		return PlatformBackend.backend.loadImage_P(is, flip);
	}
}