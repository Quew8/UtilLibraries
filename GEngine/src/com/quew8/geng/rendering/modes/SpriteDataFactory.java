package com.quew8.geng.rendering.modes;

/**
 *
 * @author Quew8
 */
public abstract class SpriteDataFactory {
    private final int mode, bytesPerSprite, verticesPerSprite, indicesPerSprite;
    
    public SpriteDataFactory(int mode, int bytesPerSprite, int verticesPerSprite, int indicesPerSprite) {
        this.mode = mode;
        this.bytesPerSprite = bytesPerSprite;
        this.verticesPerSprite = verticesPerSprite;
        this.indicesPerSprite = indicesPerSprite;
    }
    
    public int getMode() {
        return mode;
    }
    
    public int getBytesPerSprite() {
        return bytesPerSprite;
    }
    
    public int getVerticesPerSprite() {
        return verticesPerSprite;
    }
    
    public int getIndicesPerSprite() {
        return indicesPerSprite;
    }
    
    public abstract int[] getIndices();
}
