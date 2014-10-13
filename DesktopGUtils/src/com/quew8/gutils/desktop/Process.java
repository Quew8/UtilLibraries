package com.quew8.gutils.desktop;

import com.quew8.gutils.opengl.Viewport;

/**
 *
 * @author Quew8
 */
public abstract class Process {
    private Viewport viewport;
    
    public abstract void init();
    public abstract void onMadeCurrent();
    public abstract void onResize();
    public abstract void render();
    public abstract void update();
    public abstract void onUnmadeCurrent();
    public abstract void deinit();
    
    protected void setViewport(Viewport viewport) {
        this.viewport = viewport;
        onResize();
    }
    
    public Viewport getViewport() {
        return viewport;
    }
}
