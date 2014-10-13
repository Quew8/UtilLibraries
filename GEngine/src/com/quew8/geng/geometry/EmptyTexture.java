package com.quew8.geng.geometry;

/**
 *
 * @author Quew8
 */
public class EmptyTexture implements Texture {
    public static final EmptyTexture INSTANCE = new EmptyTexture();
    
    private EmptyTexture() {
        
    }
    
    @Override
    public void bind() {
        
    }

    @Override
    public Image getWholeArea() {
        return new Image();
    }

    @Override
    public void dispose() {
        
    }

    @Override
    public Texture getTexture() {
        return this;
    }
    
}
