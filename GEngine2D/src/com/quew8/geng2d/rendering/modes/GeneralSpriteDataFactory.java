package com.quew8.geng2d.rendering.modes;

import com.quew8.geng.geometry.Image;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public class GeneralSpriteDataFactory implements SpriteDataFactory {
    public static final GeneralSpriteDataFactory INSTANCE = new GeneralSpriteDataFactory();
    
    private GeneralSpriteDataFactory() {
        
    }
    
    @Override
    public int getBytesPerSprite() {
        return 64;
    }
    
    @Override
    public void addData(ByteBuffer to, Image texture, float x, float y, float width, float height) {
        float[] uv1 = texture.transformCoords(0, 0);
        float[] uv2 = texture.transformCoords(1, 1);
        to.putFloat(x);
        to.putFloat(y);
        to.putFloat(uv1[0]);
        to.putFloat(uv1[1]);
        to.putFloat(x + width);
        to.putFloat(y);
        to.putFloat(uv2[0]);
        to.putFloat(uv1[1]);
        to.putFloat(x + width);
        to.putFloat(y + height);
        to.putFloat(uv2[0]);
        to.putFloat(uv2[1]);
        to.putFloat(x);
        to.putFloat(y + height);
        to.putFloat(uv1[0]);
        to.putFloat(uv2[1]);
    }
}
