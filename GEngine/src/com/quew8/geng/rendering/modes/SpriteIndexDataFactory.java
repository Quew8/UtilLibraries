package com.quew8.geng.rendering.modes;

/**
 *
 * @author Quew8
 */
public abstract class SpriteIndexDataFactory {
    private final int mode, requiredVerticesPerSprite, indicesPerSprite;

    public SpriteIndexDataFactory(int mode, int requiredVerticesPerSprite, int indicesPerSprite) {
        this.mode = mode;
        this.requiredVerticesPerSprite = requiredVerticesPerSprite;
        this.indicesPerSprite = indicesPerSprite;
    }
    
    public int getMode() {
        return mode;
    }
    
    public int getRequiredVerticesPerSprite() {
        return requiredVerticesPerSprite;
    }
    
    public int getIndicesPerSprite() {
        return indicesPerSprite;
    }
    
    public abstract int[] getIndices();
}
