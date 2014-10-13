package com.quew8.geng.content;

import com.quew8.geng.geometry.TextureSheet;
import com.quew8.gutils.content.ContentReader;
import com.quew8.gutils.content.Source;
import com.quew8.gutils.opengl.texture.TextureParams;

/**
 *
 * @author Quew8
 */
public class TextureSheetReader implements ContentReader<TextureSheet> {
    private static final String 
            TEX_WIDTH = "tex_width", 
            TEX_HEIGHT = "tex_height",
            IMG_WIDTH = "img_width", 
            IMG_HEIGHT = "img_height", 
            BORDER = "border";
    
    @Override
    public TextureSheet read(Source in) {
        int texWidth = in.getParamInt(TEX_WIDTH);
        int texHeight = in.getParamInt(TEX_HEIGHT);
        int imgWidth = in.getParamInt(IMG_WIDTH);
        int imgHeight = in.getParamInt(IMG_HEIGHT);
        int gridWidth = texWidth / imgWidth;
        int gridHeight = texHeight / imgHeight;
        int borderSize = in.getParamInt(BORDER);
        
        return new TextureSheet(in.getStream(), texWidth, texHeight, 
                imgWidth, imgHeight, gridWidth, gridHeight, borderSize, 
                TextureParams.create());
    }
    
}
