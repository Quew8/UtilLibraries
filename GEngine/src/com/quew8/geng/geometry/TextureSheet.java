package com.quew8.geng.geometry;

import com.quew8.gutils.opengl.texture.TextureParams;
import com.quew8.gutils.opengl.texture.TextureUtils;
import com.quew8.gutils.opengl.texture.TextureSheetDetails;
import java.io.InputStream;

/**
 * 
 * @author Quew8
 */
public class TextureSheet implements Texture {
    private final TextureSheetDetails textureDetails;
	
    public TextureSheet(TextureSheetDetails textureDetails) {
        this.textureDetails = textureDetails;
    }
	
    public TextureSheet(InputStream[] imgIns, int imgWidth, 
            int imgHeight, int borderSize, TextureParams params, 
            boolean hasAlpha) {
        
        this(TextureUtils.createTextureSheet(TextureUtils.getImageLoaders(imgIns, true), -1, params, imgWidth, imgHeight, borderSize));
    }

    public TextureSheet(InputStream[] imgIns, int borderSize, TextureParams params) {
        this(TextureUtils.createTextureSheet(TextureUtils.getImageLoaders(imgIns, true), -1, params, borderSize));
    }
	
    public TextureSheet(InputStream imgIn, int texWidth, int texHeight,
            int imgWidth, int imgHeight, int gridWidth, int gridHeight, 
            int borderSize, TextureParams params) {
		
        this(TextureUtils.createTextureSheet(
                TextureUtils.createTexture(TextureUtils.getImageLoader(imgIn, true), -1, params, texWidth, texHeight), 
                imgWidth, imgHeight, gridWidth, gridHeight, borderSize
                ));
    }
	
    public TextureSheet(InputStream imgIn, int imgWidth, int imgHeight, 
            int gridWidth, int gridHeight, int borderSize, TextureParams params) {
        
        this(TextureUtils.createTextureSheet(
                TextureUtils.createTexture(TextureUtils.getImageLoader(imgIn, true), -1, params), 
                imgWidth, imgHeight, gridWidth, gridHeight, borderSize
                ));
    }
	
    @Override
    public void bind() {
        textureDetails.texture.bind();
    }

    @Override
    public Texture getTexture() {
        return this;
    }
    
    public Image getArea(int xPos, int yPos, int nw, int nh) {
        int sx = textureDetails.imgWidth + textureDetails.borderSize;
        int sy = textureDetails.imgHeight + textureDetails.borderSize;
        int xPosP = xPos * sx;
        int yPosP = yPos * sy;
        int widthP = ( nw * sx ) - textureDetails.borderSize;
        int heightP = ( nh * sy ) - textureDetails.borderSize;
        return Image.getRegion(
                (float)xPosP / textureDetails.texWidth,
                (float)yPosP / textureDetails.texHeight,
                ( (float)xPosP + widthP ) / textureDetails.texWidth,
                ( (float)yPosP + heightP ) / textureDetails.texHeight
                );
    }

    public Image getArea(int xPos, int yPos) {
        return getArea(xPos, yPos, 1, 1);
    }

    /**
     * Returns an Image representing the column-major (the default used in the 
     * com.quew8.gutils.opengl.texture package) indexed texture within this
     * sheet.
     * 
     * @param index
     * @return 
     */
    public Image getArea(int index) {
        int y = index % textureDetails.gridHeight;
        int x = ( index - y ) / textureDetails.gridHeight;
        return getArea(x, y);
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
