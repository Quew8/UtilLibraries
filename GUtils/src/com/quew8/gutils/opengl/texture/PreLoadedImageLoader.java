package com.quew8.gutils.opengl.texture;

/**
 *
 * @author Quew8
 */
class PreLoadedImageLoader implements ImageLoader {
    private final LoadedImage img;

    public PreLoadedImageLoader(LoadedImage img) {
    	this.img = img;
    }
    
	@Override
	public LoadedImage getLoadedImage() {
		return img;
	}
}
