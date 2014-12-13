package com.quew8.geng2d.rendering;

import com.quew8.geng.geometry.Texture;
import com.quew8.geng.rendering.SpriteBatcher;
import com.quew8.geng.rendering.modes.StaticRenderMode;
import com.quew8.geng2d.rendering.modes.SpriteDataFactoryGeneric;

/**
 *
 * @author Quew8
 * @param <T>
 */
public class SpriteBatcherGeneric<T> extends SpriteBatcher<SpriteDataFactoryGeneric<T>> {

    public SpriteBatcherGeneric(Texture tex, StaticRenderMode renderMode, SpriteDataFactoryGeneric<T> dataFactory, int size) {
        super(tex, renderMode, dataFactory, size);
    }
    
    public void draw(T data) {
        predraw();
        getFactory().addData(getBuffer(), data);
    }
    
}
