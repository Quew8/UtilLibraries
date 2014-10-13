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
    
    public PreLoadedImageLoader(ImageLoader imgLoader) {
        this(imgLoader.getLoadedImage());
    }
    
    @Override
    public LoadedImage getLoadedImage() {
            return img;
    }
}
