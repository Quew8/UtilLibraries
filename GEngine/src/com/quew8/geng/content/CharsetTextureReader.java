package com.quew8.geng.content;

import com.quew8.geng.geometry.CharsetTexture;
import com.quew8.gutils.content.ContentReader;
import com.quew8.gutils.content.Source;
import static com.quew8.gutils.opengl.OpenGL.GL_LINEAR;
import static com.quew8.gutils.opengl.OpenGL.GL_NEAREST;
import com.quew8.gutils.opengl.texture.TextureDetails;
import com.quew8.gutils.opengl.texture.TextureParams;
import com.quew8.gutils.opengl.texture.TextureUtils;

/**
 *
 * @author Quew8
 */
public class CharsetTextureReader implements ContentReader<CharsetTexture> {
    private static final String 
            CHARS = "chars",
            CHAR_WIDTH = "char_width",
            CHAR_HEIGHT = "char_height";
    
    @Override
    public CharsetTexture read(Source in) {
        TextureDetails details = TextureUtils.createAlphaMaskTexture(
                TextureUtils.getImageLoader(in.getStream(), true), 
                TextureParams.create(GL_LINEAR, GL_NEAREST)
        );
        return new CharsetTexture(
                details,
                CharsetTexture.getMapping(
                        in.getParams().get(CHARS),
                        in.getParamInt(CHAR_WIDTH),
                        in.getParamInt(CHAR_HEIGHT),
                        details
                )
        );
    }
    
}
