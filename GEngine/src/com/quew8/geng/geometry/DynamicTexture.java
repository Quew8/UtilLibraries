package com.quew8.geng.geometry;

import com.quew8.gutils.opengl.Framebuffer;
import com.quew8.gutils.opengl.Framebuffer.FramebufferException;
import static com.quew8.gutils.opengl.OpenGL.GL_RGBA;
import com.quew8.gutils.opengl.texture.TextureUtils;
import com.quew8.gutils.opengl.texture.TextureParams;
import static com.quew8.gutils.opengl.OpenGL.*;
import com.quew8.gutils.opengl.Viewport;

/**
 * 
 * @author Quew8
 */
public class DynamicTexture extends BasicTexture {
    private final Framebuffer frameBuffer;
    
    public DynamicTexture(int width, int height) {
        super(TextureUtils.createEmptyTexture(width, height, GL_RGBA, TextureParams.create()));
        this.frameBuffer = new Framebuffer(width, height);
        this.frameBuffer.bind();
        this.frameBuffer.attatchTexture(textureDetails.texture.getId(), GL_COLOR_ATTACHMENT0);
        FramebufferException.checkFramebufferError();
        Framebuffer.unbind();
    }
    
    public void prepForDraw() {
        frameBuffer.bind();
        frameBuffer.getViewport().set();
    }
    
    public void finishDrawing(Viewport restoreViewport) {
        Framebuffer.unbind();
        restoreViewport.set();
    }

    @Override
    public void dispose() {
        super.dispose();
        frameBuffer.delete();
    }
}
