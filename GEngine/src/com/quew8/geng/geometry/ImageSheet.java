package com.quew8.geng.geometry;

import com.quew8.gutils.opengl.texture.TextureParams;
import com.quew8.gutils.opengl.texture.TextureUtils;
import com.quew8.gutils.opengl.texture.TextureSheetDetails;
import java.io.InputStream;

/**
 * 
 * @author Quew8
 */
public class ImageSheet implements Image {
    private final TextureSheetDetails textureDetails;
	
    public ImageSheet(TextureSheetDetails textureDetails) {
        this.textureDetails = textureDetails;
    }
	
    public ImageSheet(InputStream[] imgIns, int imgWidth, 
            int imgHeight, int borderSize, TextureParams params, 
            boolean hasAlpha) {
        
        this(TextureUtils.createTextureSheet(TextureUtils.getImageLoaders(imgIns, true), -1, params, imgWidth, imgHeight, borderSize));
    }

    public ImageSheet(InputStream[] imgIns, int borderSize, TextureParams params) {
        this(TextureUtils.createTextureSheet(TextureUtils.getImageLoaders(imgIns, true), -1, params, borderSize));
    }
	
    public ImageSheet(InputStream imgIn, int texWidth, int texHeight,
            int imgWidth, int imgHeight, int gridWidth, int gridHeight, 
            int borderSize, TextureParams params) {
		
        this(TextureUtils.createTextureSheet(
                TextureUtils.createTexture(TextureUtils.getImageLoader(imgIn, true), -1, params, texWidth, texHeight), 
                imgWidth, imgHeight, gridWidth, gridHeight, borderSize
                ));
    }
	
    public ImageSheet(InputStream imgIn, int imgWidth, int imgHeight, 
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
    public Image getImage() {
        return this;
    }
    
    public TextureArea getArea(int xPos, int yPos, int nw, int nh) {
        int sx = textureDetails.imgWidth + textureDetails.borderSize;
        int sy = textureDetails.imgHeight + textureDetails.borderSize;
        int xPosP = xPos * sx;
        int yPosP = yPos * sy;
        int widthP = ( nw * sx ) - textureDetails.borderSize;
        int heightP = ( nh * sy ) - textureDetails.borderSize;
        return new TextureArea(
                (float)xPosP / textureDetails.texWidth,
                (float)yPosP / textureDetails.texHeight,
                ( (float)xPosP + widthP ) / textureDetails.texWidth,
                ( (float)yPosP + heightP ) / textureDetails.texHeight
                );
    }

    public TextureArea getArea(int xPos, int yPos) {
        return getArea(xPos, yPos, 1, 1);
    }

    public TextureArea getArea(int index) {
        int[] pos = TextureUtils.getPositionInSheet(index, textureDetails.gridWidth, textureDetails.gridHeight);
        return getArea(pos[0], pos[1]);
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
