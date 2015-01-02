package com.quew8.geng3d.rendering.modes;

import com.quew8.geng.geometry.Image;
import com.quew8.geng.geometry.Plane;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public class UVQuadSpriteDataFactory extends SpriteDataFactory2D {
    public static final UVQuadSpriteDataFactory INSTANCE = new UVQuadSpriteDataFactory();
    
    private UVQuadSpriteDataFactory() {
        super(32, 8, 4);
    }

    @Override
    public void addData(ByteBuffer to, Image texture, float x, float y, float z, float width, float height, Plane plane) {
        float[] uv = texture.transformCoords(0, 0);
        to.putFloat(uv[0]);
        to.putFloat(uv[1]);
        uv = texture.transformCoords(1, 0);
        to.putFloat(uv[0]);
        to.putFloat(uv[1]);
        uv = texture.transformCoords(1, 1);
        to.putFloat(uv[0]);
        to.putFloat(uv[1]);
        uv = texture.transformCoords(0, 1);
        to.putFloat(uv[0]);
        to.putFloat(uv[1]);
    }
    
}
