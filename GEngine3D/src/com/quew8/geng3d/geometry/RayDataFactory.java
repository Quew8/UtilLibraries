package com.quew8.geng3d.rendering.modes;

import com.quew8.gutils.Colour;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public class RaySpriteDataFactory extends SpriteDataFactory1D {
    public static final RaySpriteDataFactory INSTANCE = new RaySpriteDataFactory();

    public RaySpriteDataFactory() {
        super(24, 12, 2);
    }
    
    @Override
    public void addData(ByteBuffer to, Colour colour, float x, float y, float z, float width, float height, float depth) {
        addData(to, x, y, z, width, height, depth);
    }
    
    @Override
    public void addData(ByteBuffer to, float x, float y, float z, float width, float height, float depth) {
        to.putFloat(x);
        to.putFloat(y);
        to.putFloat(z);
        to.putFloat(x + width);
        to.putFloat(y + height);
        to.putFloat(z + depth);
    }
}
