package com.quew8.gutils.desktop.opengl.services;

import java.nio.IntBuffer;

/**
 *
 * @author Quew8
 */
public class NoFramebufferServiceImpl implements FramebufferServiceImpl {
    public static final NoFramebufferServiceImpl INSTANCE = new NoFramebufferServiceImpl();

    @Override
    public void glBindFramebuffer_P(int target, int framebuffer) {
        throw new UnsupportedOperationException("There is no support for framebuffers in this implementation");
    }

    @Override
    public void glBindRenderbuffer_P(int target, int renderbuffer) {
        throw new UnsupportedOperationException("There is no support for framebuffers in this implementation");
    }

    @Override
    public int glCheckFramebufferStatus_P(int target) {
        throw new UnsupportedOperationException("There is no support for framebuffers in this implementation");
    }

    @Override
    public void glDeleteFramebuffers_P(IntBuffer buffers) {
        throw new UnsupportedOperationException("There is no support for framebuffers in this implementation");
    }

    @Override
    public void glDeleteRenderbuffers_P(IntBuffer buffers) {
        throw new UnsupportedOperationException("There is no support for framebuffers in this implementation");
    }

    @Override
    public void glFramebufferRenderbuffer_P(int target, int attachment, int renderbufferTarget, int renderbuffer) {
        throw new UnsupportedOperationException("There is no support for framebuffers in this implementation");
    }

    @Override
    public void glFramebufferTexture2D_P(int target, int attachment, int texTarget, int texture, int level) {
        throw new UnsupportedOperationException("There is no support for framebuffers in this implementation");
    }

    @Override
    public void glGenFramebuffers_P(IntBuffer buffers) {
        throw new UnsupportedOperationException("There is no support for framebuffers in this implementation");
    }

    @Override
    public void glGenRenderbuffers_P(IntBuffer buffers) {
        throw new UnsupportedOperationException("There is no support for framebuffers in this implementation");
    }

    @Override
    public void glGetFramebufferAttachmentParameteriv_P(int target, int attatchment, int pname, IntBuffer params) {
        throw new UnsupportedOperationException("There is no support for framebuffers in this implementation");
    }

    @Override
    public void glGetRenderbufferParameteriv_P(int target, int pname, IntBuffer params) {
        throw new UnsupportedOperationException("There is no support for framebuffers in this implementation");
    }

    @Override
    public boolean glIsFramebuffer_P(int framebuffer) {
        throw new UnsupportedOperationException("There is no support for framebuffers in this implementation");
    }

    @Override
    public boolean glIsRenderbuffer_P(int renderbuffer) {
        throw new UnsupportedOperationException("There is no support for framebuffers in this implementation");
    }

    @Override
    public void glRenderbufferStorage_P(int target, int internalFormat, int width, int height) {
        throw new UnsupportedOperationException("There is no support for framebuffers in this implementation");
    }

    @Override
    public boolean isApplicable() {
        return true;
    }

    @Override
    public int getPrecedence() {
        return -1;
    }
    
}
