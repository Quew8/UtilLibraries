package com.quew8.geng.rendering;

/**
 *
 * @author Quew8
 * @param <T>
 */
public interface Sprite<T extends SpriteBatcher<?>> {
    public void draw(T batcher);
}
