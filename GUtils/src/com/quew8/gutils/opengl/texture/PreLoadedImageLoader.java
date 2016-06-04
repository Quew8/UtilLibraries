package com.quew8.gutils.opengl.texture;

/**
 *
 * @author Quew8
 */
class PreLoadedImageLoader implements ImageLoader {
    private final LoadedImage img;

    PreLoadedImageLoader(LoadedImage img) {
    	this.img = img;
    }
    
    PreLoadedImageLoader(ImageLoader imgLoader) {
        this(imgLoader.getLoadedImage());
    }
    
    @Override
    public LoadedImage getLoadedImage() {
            return img;
    }
}
