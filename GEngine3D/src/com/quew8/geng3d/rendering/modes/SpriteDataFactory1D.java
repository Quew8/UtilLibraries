package com.quew8.geng3d.rendering.modes;

import com.quew8.geng.rendering.modes.SpriteDataFactory;
import com.quew8.gutils.Colour;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public abstract class SpriteDataFactory1D extends SpriteDataFactory {

    public SpriteDataFactory1D(int bytesPerSprite, int bytesPerVertex, int verticesPerSprite) {
        super(bytesPerSprite, bytesPerVertex, verticesPerSprite);
    }
    
    public void addData(ByteBuffer to, Colour colour, float x, float y, float z, float width, float height, float depth) {
        throw new UnsupportedOperationException("This factory does not support this operation");
    }
    
    public void addData(ByteBuffer to, float x, float y, float width, float z, float height, float depth) {
        throw new UnsupportedOperationException("This factory does not support this operation");
    }
}
