package com.quew8.gutils.opengl.texture;

/**
 * 
 * @author Quew8
 * @param <T> 
 */
class TextureLoadVariables {
    final ImageLoader[] imageLoaders;
    final int texWidth;
    final int texHeight;
    final TextureObj texture;
    final int destFormat;
    final TextureParams texParams;
    
    public TextureLoadVariables(
    		ImageLoader[] imageLoaders, 
    		int texWidth, int texHeight, 
    		TextureObj texture, 
    		int destFormat,
    		TextureParams texParams) {
    	
        this.imageLoaders = imageLoaders;
        this.texWidth = texWidth;
        this.texHeight = texHeight;
        this.texture = texture;
        this.destFormat = destFormat;
        this.texParams = texParams;
    }
}
