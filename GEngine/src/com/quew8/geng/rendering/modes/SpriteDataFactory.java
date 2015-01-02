package com.quew8.geng.rendering.modes;

/**
 *
 * @author Quew8
 */
public abstract class SpriteDataFactory {
    private final int bytesPerSprite, bytesPerVertex, verticesPerSprite;
    
    public SpriteDataFactory(int bytesPerSprite, int bytesPerVertex, int verticesPerSprite) {
        this.bytesPerSprite = bytesPerSprite;
        this.bytesPerVertex = bytesPerVertex;
        this.verticesPerSprite = verticesPerSprite;
    }
    
    public int getBytesPerSprite() {
        return bytesPerSprite;
    }

    public int getBytesPerVertex() {
        return bytesPerVertex;
    }
    
    public int getVerticesPerSprite() {
        return verticesPerSprite;
    }
}
