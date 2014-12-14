package com.quew8.gutils.desktop.opengl.services;

import java.nio.IntBuffer;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;

/**
 *
 * @author Quew8
 */
public class DefaultFramebufferServiceImpl implements FramebufferServiceImpl {
    public static final DefaultFramebufferServiceImpl INSTANCE = new DefaultFramebufferServiceImpl();
    
    @Override
    public boolean isApplicable() {
        //#TODO Work out this.
        return true;//GLContext.getCapabilities().OpenGL30;
    }
    
    @Override
    public int getPrecedence() {
        return 1;
    }

    @Override
    public void glBindFramebuffer_P(int target, int framebuffer) {
        GL30.glBindFramebuffer(target, framebuffer);
    }

    @Override
    public void glBindRenderbuffer_P(int target, int renderbuffer) {
        GL30.glBindRenderbuffer(target, renderbuffer);
    }

    @Override
    public int glCheckFramebufferStatus_P(int target) {
        return GL30.glCheckFramebufferStatus(target);
    }

    @Override
    public void glDeleteFramebuffers_P(IntBuffer buffers) {
        GL30.glDeleteFramebuffers(buffers);
    }

    @Override
    public void glDeleteRenderbuffers_P(IntBuffer buffers) {
        GL30.glDeleteRenderbuffers(buffers);
    }

    @Override
    public void glFramebufferRenderbuffer_P(int target, int attachment, int renderbufferTarget, int renderbuffer) {
        GL30.glFramebufferRenderbuffer(target, attachment, renderbufferTarget, renderbuffer);
    }

    @Override
    public void glFramebufferTexture2D_P(int target, int attachment, int texTarget, int texture, int level) {
        GL30.glFramebufferTexture2D(target, attachment, texTarget, texture, level);
    }

    @Override
    public void glGenFramebuffers_P(IntBuffer buffers) {
        GL30.glGenFramebuffers(buffers);
    }

    @Override
    public void glGenRenderbuffers_P(IntBuffer buffers) {
        GL30.glGenRenderbuffers(buffers);
    }

    @Override
    public void glGetFramebufferAttachmentParameteriv_P(int target, int attatchment, int pname, IntBuffer params) {
        GL30.glGetFramebufferAttachmentParameter(target, attatchment, pname, params);
    }

    @Override
    public void glGetRenderbufferParameteriv_P(int target, int pname, IntBuffer params) {
        GL30.glGetRenderbufferParameter(target, pname, params);
    }

    @Override
    public boolean glIsFramebuffer_P(int framebuffer) {
        return GL30.glIsFramebuffer(framebuffer);
    }

    @Override
    public boolean glIsRenderbuffer_P(int renderbuffer) {
        return GL30.glIsRenderbuffer(renderbuffer);
    }

    @Override
    public void glRenderbufferStorage_P(int target, int internalFormat, int width, int height) {
        GL30.glRenderbufferStorage(target, internalFormat, width, height);
    }
    
}
