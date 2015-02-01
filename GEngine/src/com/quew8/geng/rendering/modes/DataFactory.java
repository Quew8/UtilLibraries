package com.quew8.geng.rendering.modes;

import com.quew8.geng.rendering.modes.Mode;

/**
 *
 * @author Quew8
 */
public interface DataFactory {
    public int getBytesPerSprite(Mode mode);
    public int getVerticesPerSprite();
}
