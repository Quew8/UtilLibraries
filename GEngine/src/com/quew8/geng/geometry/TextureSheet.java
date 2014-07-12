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
        return new Image(
                (float)xPosP / textureDetails.texWidth,
                (float)yPosP / textureDetails.texHeight,
                ( (float)xPosP + widthP ) / textureDetails.texWidth,
                ( (float)yPosP + heightP ) / textureDetails.texHeight
                );
    }

    public Image getArea(int xPos, int yPos) {
        return getArea(xPos, yPos, 1, 1);
    }

    public Image getArea(int index) {
        int[] pos = TextureUtils.getPositionInSheet(index, textureDetails.gridWidth, textureDetails.gridHeight);
        return getArea(pos[0], pos[1]);
    }

    @Override
    public Image getWholeArea() {
        return new Image(0, 0, 
                textureDetails.usedWidth/textureDetails.texWidth,
                textureDetails.usedHeight/textureDetails.texHeight);
    }

    @Override
    public void dispose() {
        textureDetails.texture.delete();
    }
    
}
