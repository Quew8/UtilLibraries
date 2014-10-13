package com.quew8.geng2d.rendering.modes;

import com.quew8.geng.geometry.Image;
import com.quew8.geng.rendering.modes.SpriteDataFactory;
import com.quew8.gutils.Colour;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public abstract class SpriteDataFactory2D extends SpriteDataFactory {

    public SpriteDataFactory2D(int mode, int bytesPerSprite, int verticesPerSprite, int indicesPerSprite) {
        super(mode, bytesPerSprite, verticesPerSprite, indicesPerSprite);
    }
    
    public void addData(ByteBuffer to, Image texture, float x, float y, float width, float height) {
        throw new UnsupportedOperationException("This factory does not support this operation");
    }
    
    public void addData(ByteBuffer to, Colour colour, float x, float y, float width, float height) {
        throw new UnsupportedOperationException("This factory does not support this operation");
    }
    
    public void addData(ByteBuffer to, float x, float y, float width, float height) {
        throw new UnsupportedOperationException("This factory does not support this operation");
    }
}
