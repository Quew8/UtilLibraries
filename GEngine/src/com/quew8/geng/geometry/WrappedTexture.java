package com.quew8.geng.geometry;

import com.quew8.gutils.BufferUtils;
import static com.quew8.gutils.opengl.OpenGL.*;

/**
 *
 * @author Quew8
 */
public class WrappedTexture implements Texture {
    private final int textureId;

    public WrappedTexture(int textureId) {
        this.textureId = textureId;
    }

    @Override
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, textureId);
    }

    @Override
    public Image getWholeArea() {
        return Image.WHOLE;
    }

    @Override
    public void dispose() {
        glDeleteTextures(BufferUtils.createIntBuffer(new int[textureId]));
    }

    @Override
    public Texture getTexture() {
        return this;
    }
}
