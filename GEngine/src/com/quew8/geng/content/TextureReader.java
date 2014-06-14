package com.quew8.geng.content;

import com.quew8.geng.geometry.BasicImage;
import com.quew8.gutils.content.ContentReader;
import com.quew8.gutils.content.Source;
import com.quew8.gutils.opengl.texture.TextureParams;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 *
 * @author Quew8
 */
public class TextureReader implements ContentReader<BasicImage> {
    
    @Override
    public BasicImage read(Source in) {
        return new BasicImage(in.getStream(), TextureParams.create());
    }
    
}
