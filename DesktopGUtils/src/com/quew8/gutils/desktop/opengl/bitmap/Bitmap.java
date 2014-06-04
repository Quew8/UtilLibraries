package com.quew8.gutils.desktop.opengl.bitmap;

import com.quew8.gutils.opengl.GBuffer;

/**
 * 
 * @param <T> 
 */
public class Bitmap<T extends GBuffer> {
    private final T buffer;
    private final int width, height;

    public Bitmap(T buffer, int width, int height) {
        this.buffer = buffer;
        this.width = width;
        this.height = height;
    }

    public T getBuffer() {
        return buffer;
    }

    public void bind() {
        buffer.bind();
    }

    public void unbind() {
        buffer.unbind();
    }

    public void delete() {
        buffer.delete();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
