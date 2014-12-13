package com.quew8.geng2d.rendering.modes;

import com.quew8.geng.rendering.modes.SpriteDataFactory;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 * @param <T>
 */
public abstract class SpriteDataFactoryGeneric<T> extends SpriteDataFactory {

    public SpriteDataFactoryGeneric(int mode, int bytesPerSprite, int verticesPerSprite, int indicesPerSprite) {
        super(mode, bytesPerSprite, verticesPerSprite, indicesPerSprite);
    }
    
    public abstract void addData(ByteBuffer to, T data);
}
