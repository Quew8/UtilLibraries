package com.quew8.gutils.opengl.texture;

import java.io.InputStream;

/**
 *
 * @author Quew8
 */
class StreamImageLoader implements ImageLoader {
    private final InputStream is;
    private final boolean flip;

    StreamImageLoader(InputStream is, boolean flip) {
	this.is = is;
	this.flip = flip;
    }
	
    StreamImageLoader(InputStream is) {
        this(is, false);
    }

    @Override
    public LoadedImage getLoadedImage() {
        return TextureUtils.loadImage(is, flip);
    }
}
