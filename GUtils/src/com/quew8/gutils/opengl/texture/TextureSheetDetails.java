package com.quew8.gutils.opengl.texture;

/**
 *
 */
public class TextureSheetDetails extends TextureDetails {
    public final int gridWidth;
    public final int gridHeight;
    public final int imgWidth;
    public final int imgHeight;
    public final int borderSize;

    protected TextureSheetDetails(TextureObj texture, 
            int gridWidth, int gridHeight, 
            int imgWidth, int imgHeight, 
            int texWidth, int texHeight, 
            int borderSize) {
        
        super(texture, gridWidth * (imgWidth + borderSize), gridHeight * (imgHeight + borderSize), texWidth, texHeight);
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
        this.borderSize = borderSize;
    }

    protected TextureSheetDetails(TextureDetails details, 
            int imgWidth, int imgHeight, 
            int gridWidth, int gridHeight, 
            int borderSize) {
        
        super(details.texture, details.texWidth, details.texHeight, details.usedWidth, details.usedHeight);
        if (imgWidth == -1) {
            imgWidth = (details.usedWidth / gridWidth) - borderSize;
            imgHeight = (details.usedHeight / gridHeight) - borderSize;
        } else if (gridWidth == -1) {
            gridWidth = details.usedWidth / (imgWidth + borderSize);
            gridHeight = details.usedHeight / (imgHeight + borderSize);
        } else if (borderSize == -1) {
            borderSize = (details.usedWidth / gridWidth) - imgWidth;
        }
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.borderSize = borderSize;
    }

    @Override
    public String toString() {
        return super.toString() + "\nImage: " + imgWidth + ", " + imgHeight + "\nGrid:" + gridWidth + ", " + gridHeight;
    }
    
}
