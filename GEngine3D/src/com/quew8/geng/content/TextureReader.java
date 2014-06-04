package com.quew8.geng.content;

import com.quew8.geng.geometry.BasicImage;
import com.quew8.gutils.content.ContentReader;
import com.quew8.gutils.opengl.texture.TextureParams;
import java.io.InputStream;
import java.util.HashMap;

/**
 *
 * @author Quew8
 */
public class TextureReader implements ContentReader<BasicImage> {
    
    @Override
    public BasicImage read(InputStream in, HashMap<String, String> params) {
        return new BasicImage(in, TextureParams.create());
    }
    
}
