package com.quew8.gutils.desktop.windowing;

import com.quew8.gutils.opengl.Viewport;

/**
 *
 * @author Quew8
 */
public interface ISurface {
    public void endOfFrame(int newFps);
    
    public void endOfFrame();
    
    public void onResize(int newWidth, int newHeight);
    
    public void close();
    
    public void requestClose();
    
    public boolean remainOpen();
    
    public boolean isResized();
    
    public Viewport getViewport();
}
