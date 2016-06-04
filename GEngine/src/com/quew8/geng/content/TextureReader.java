package com.quew8.geng.content;

import com.quew8.geng.geometry.BasicTexture;
import com.quew8.gutils.content.ContentReader;
import com.quew8.gutils.content.Source;
import com.quew8.gutils.opengl.texture.TextureParams;

/**
 *
 * @author Quew8
 */
public class TextureReader implements ContentReader<BasicTexture> {
    
    @Override
    public BasicTexture read(Source in) {
        return new BasicTexture(in.getStream(), new TextureParams());
    }
    
}
