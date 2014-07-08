package com.quew8.geng2d.rendering.modes;

import com.quew8.geng.geometry.TextureArea;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public interface SpriteDataFactory {
    public int getBytesPerSprite();
    public void addData(ByteBuffer to, TextureArea texture, float x, float y, float width, float height);
}
