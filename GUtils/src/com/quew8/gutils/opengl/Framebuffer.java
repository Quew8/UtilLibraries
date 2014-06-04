package com.quew8.gutils.opengl;

import static com.quew8.gutils.opengl.OpenGL.*;

/**
 * 
 * @author Quew8
 */
public class Framebuffer extends GObject {
    private final Viewport viewport;
    
    public Framebuffer(int width, int height) {
        super(genId());
        this.viewport = new Viewport(width, height);
        FramebufferException.checkFramebufferError();
    }
    
    public void bind() {
        glBindFramebuffer(GL_FRAMEBUFFER, getId());
    }
    
    @Override
    public void delete() {
        glDeleteFramebuffers(getIdBuffer());
    }
    
    public static void unbind() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }
    
    public Viewport getViewport() {
        return viewport;
    }
    
    public void attatchTexture(int textureId, int attatchment) {
        glFramebufferTexture2D(GL_FRAMEBUFFER, attatchment, GL_TEXTURE_2D, textureId, 0);
        FramebufferException.checkFramebufferError();
    }
    
    public void attatchRenderBuffer(int renderBufferId, int attatchment) {
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, attatchment, GL_RENDERBUFFER, renderBufferId);
        FramebufferException.checkFramebufferError();
    }
    
    public void createAndAttatchRenderBuffer(int format, int attatchment) {
        RenderBuffer renderBuffer = new RenderBuffer(format, viewport.getWidth(), viewport.getHeight());
        attatchRenderBuffer(renderBuffer.getId(), attatchment);
    }
    
    public static int genId() {
        glGenFramebuffers(idBuff);
        return idBuff.get(0);
    }
    
    /**
     * 
     */
    public static class RenderBuffer extends GObject {
        
        public RenderBuffer(int format, int width, int height) {
            super(genRenderBufferId());
            glBindRenderbuffer(GL_RENDERBUFFER, getId());
            glRenderbufferStorage(GL_RENDERBUFFER, format, width, height);
        }
        
        public void bind() {
            glBindRenderbuffer(GL_RENDERBUFFER, getId());
        }
        
        @Override
        public void delete() {
            glDeleteRenderbuffers(getIdBuffer());
        }
        
        public static int genRenderBufferId() {
            glGenRenderbuffers(idBuff);
            return idBuff.get(0);
        }
    }
    
    /**
     * 
     */
    public static class FramebufferException extends GLException {
        
        public FramebufferException(int code) {
            super(OpenGLUtils.toOpenGLString(code));
        }
        
        public static void checkFramebufferError() {
            int errorCode = glCheckFramebufferStatus(GL_FRAMEBUFFER);
            if(errorCode != GL_FRAMEBUFFER_COMPLETE) {
                throw new FramebufferException(errorCode);
            } else {
                checkGLError();
            }
        }
    }
}
