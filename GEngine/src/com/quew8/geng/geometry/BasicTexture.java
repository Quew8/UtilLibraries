package com.quew8.geng.geometry;

import com.quew8.gutils.opengl.texture.TextureParams;
import com.quew8.gutils.opengl.texture.TextureUtils;
import com.quew8.gutils.opengl.texture.TextureDetails;
import java.io.InputStream;
;

/**
 * 
 * @author Quew8
 */
public class BasicTexture implements Texture {
    protected final TextureDetails textureDetails;

    public BasicTexture(TextureDetails details) {
        this.textureDetails = details;
    }

    public BasicTexture(InputStream imgIn, int imgWidth, int imgHeight, TextureParams params) {
        this(TextureUtils.createTexture(TextureUtils.getImageLoader(imgIn, true), -1, params, imgWidth, imgHeight));
    }

    public BasicTexture(InputStream imgIn, TextureParams params) {
        this(TextureUtils.createTexture(TextureUtils.getImageLoader(imgIn, true), -1, params));
    }

    @Override
    public void bind() {
        textureDetails.texture.bind();
    }

    @Override
    public Texture getTexture() {
        return this;
    }
    
    @Override
    public Image getWholeArea() {
        return Image.getRegion(0, 0, 
                textureDetails.usedWidth/textureDetails.texWidth, 
                textureDetails.usedHeight/textureDetails.texHeight);
    }

    @Override
    public void dispose() {
        textureDetails.texture.delete();
    }
}
