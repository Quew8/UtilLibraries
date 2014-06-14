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
public class BasicImage implements Image {
    protected final TextureDetails textureDetails;

    public BasicImage(TextureDetails details) {
        this.textureDetails = details;
    }

    public BasicImage(InputStream imgIn, int imgWidth, int imgHeight, TextureParams params) {
        this(TextureUtils.createTexture(TextureUtils.getImageLoader(imgIn, true), -1, params, imgWidth, imgHeight));
    }

    public BasicImage(InputStream imgIn, TextureParams params) {
        this(TextureUtils.createTexture(TextureUtils.getImageLoader(imgIn, true), -1, params));
    }

    @Override
    public void bind() {
        textureDetails.texture.bind();
    }

    @Override
    public Image getImage() {
        return this;
    }
    
    @Override
    public TextureArea getWholeArea() {
        return new TextureArea(0, 0, 
                textureDetails.usedWidth/textureDetails.texWidth, 
                textureDetails.usedHeight/textureDetails.texHeight);
    }

    @Override
    public void dispose() {
        textureDetails.texture.delete();
    }
}
