package com.quew8.gutils.desktop.windowing;

import com.quew8.gutils.opengl.Viewport;

/**
 * 
 * @author Quew8
 */
public abstract class Surface implements ISurface {
    private boolean remainOpen = true;
    private boolean isResized = true;
    private Viewport viewport;
    private final int fps;
    
    public Surface(Viewport viewport, int fps) {
        this.viewport = viewport;
        this.fps = fps;
    }
    
    @Override
    public void endOfFrame() {
        endOfFrame(fps);
    }
    
    @Override
    public void onResize(int newWidth, int newHeight) {
        viewport = new Viewport(newWidth, newHeight);
        isResized = true;
    }
    
    @Override
    public void requestClose() {
        remainOpen = false;
    }
    
    @Override
    public boolean remainOpen() {
        return remainOpen;
    }
    
    @Override
    public boolean isResized() {
        boolean b = isResized;
        isResized = false;
        return b;
    }
    
    @Override
    public Viewport getViewport() {
        return viewport;
    }
}
