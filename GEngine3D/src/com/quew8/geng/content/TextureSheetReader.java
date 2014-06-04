package com.quew8.geng.content;

import com.quew8.geng.geometry.ImageSheet;
import com.quew8.gutils.content.ContentReader;
import com.quew8.gutils.opengl.texture.TextureParams;
import java.io.InputStream;
import java.util.HashMap;

/**
 *
 * @author Quew8
 */
public class TextureSheetReader implements ContentReader<ImageSheet> {
    private static final String 
            TEX_WIDTH = "tex_width", 
            TEX_HEIGHT = "tex_height",
            IMG_WIDTH = "img_width", 
            IMG_HEIGHT = "img_height", 
            BORDER = "border";
    
    @Override
    public ImageSheet read(InputStream in, HashMap<String, String> params) {
        int texWidth = Integer.parseInt(params.get(TEX_WIDTH));
        int texHeight = Integer.parseInt(params.get(TEX_HEIGHT));
        int imgWidth = Integer.parseInt(params.get(IMG_WIDTH));
        int imgHeight = Integer.parseInt(params.get(IMG_HEIGHT));
        int gridWidth = texWidth / imgWidth;
        int gridHeight = texHeight / imgHeight;
        int borderSize = Integer.parseInt(params.get(BORDER));
        
        return new ImageSheet(in, texWidth, texHeight, imgWidth, imgHeight, gridWidth, gridHeight, borderSize, TextureParams.create());
    }
    
}
