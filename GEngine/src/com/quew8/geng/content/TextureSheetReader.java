package com.quew8.geng.content;

import com.quew8.geng.geometry.TextureSheet;
import com.quew8.gutils.content.ContentReader;
import com.quew8.gutils.content.Source;
import com.quew8.gutils.content.SourceSheet;
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
        int texWidth = Integer.parseInt(in.getParams().get(TEX_WIDTH));
        int texHeight = Integer.parseInt(in.getParams().get(TEX_HEIGHT));
        int imgWidth = Integer.parseInt(in.getParams().get(IMG_WIDTH));
        int imgHeight = Integer.parseInt(in.getParams().get(IMG_HEIGHT));
        int gridWidth = texWidth / imgWidth;
        int gridHeight = texHeight / imgHeight;
        int borderSize = Integer.parseInt(in.getParams().get(BORDER));
        
        return new TextureSheet(in.getStream(), texWidth, texHeight, 
                imgWidth, imgHeight, gridWidth, gridHeight, borderSize, 
                TextureParams.create());
    }
    
}
