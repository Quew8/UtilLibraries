package com.quew8.gutils.desktop.opengl.services;

import com.quew8.gutils.services.ServiceImpl;
import java.nio.IntBuffer;

/**
 *
 * @author Quew8
 */
public interface FramebufferServiceImpl extends ServiceImpl {

    public void glBindFramebuffer_P(int target, int framebuffer);

    public void glBindRenderbuffer_P(int target, int renderbuffer);

    public int glCheckFramebufferStatus_P(int target);

    public void glDeleteFramebuffers_P(IntBuffer buffers);

    public void glDeleteRenderbuffers_P(IntBuffer buffers);

    public void glFramebufferRenderbuffer_P(int target, int attachment, int renderbufferTarget, int renderbuffer);

    public void glFramebufferTexture2D_P(int target, int attachment, int texTarget, int texture, int level);

    public void glGenFramebuffers_P(IntBuffer buffers);
    
    public void glGenRenderbuffers_P(IntBuffer buffers);

    public void glGetFramebufferAttachmentParameteriv_P(int target, int attatchment, int pname, IntBuffer params);

    public void glGetRenderbufferParameteriv_P(int target, int pname, IntBuffer params);

    public boolean glIsFramebuffer_P(int framebuffer);

    public boolean glIsRenderbuffer_P(int renderbuffer);

    public void glRenderbufferStorage_P(int target, int internalFormat, int width, int height);
}
