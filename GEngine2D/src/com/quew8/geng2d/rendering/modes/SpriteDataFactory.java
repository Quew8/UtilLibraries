package com.quew8.geng2d.rendering.modes;

import com.quew8.geng.geometry.Image;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public interface SpriteDataFactory {
    public int getBytesPerSprite();
    public void addData(ByteBuffer to, Image texture, float x, float y, float width, float height);
}
