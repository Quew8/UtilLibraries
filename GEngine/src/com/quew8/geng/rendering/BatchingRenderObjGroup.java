package com.quew8.geng.rendering;

import com.quew8.geng.GameObject;
import com.quew8.geng.interfaces.Disposable;
import com.quew8.geng.interfaces.FinalDrawable;

/**
 *
 * @author Quew8
 * @param <T>
 * @param <S>
 */
public class BatchingRenderObjGroup<T extends SpriteBatcher<?>, S extends Sprite<T>> extends GameObject implements FinalDrawable, Disposable {
    private final T batcher;
    private final S[] sprites;

    public BatchingRenderObjGroup(T batcher, S[] sprites) {
        this.batcher = batcher;
        this.sprites = sprites;
    }
    
    @Override
    public void draw() {
        batcher.begin();
        for(int i = 0; i < sprites.length; i++) {
            sprites[i].draw(batcher);
        }
        batcher.end();
    }

    @Override
    public void dispose() {
        batcher.dispose();
    }
    
    protected int getNSprites() {
        return sprites.length;
    }
    
    protected S getSprite(int index) {
        return sprites[index];
    }
    
    protected void setSprite(int index, S s) {
        sprites[index] = s;
    }
}
