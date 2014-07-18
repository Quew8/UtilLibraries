package com.quew8.gutils.desktop.windowing;

import com.quew8.gutils.opengl.Viewport;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.PixelFormat;

/**
 * 
 * @author Quew8
 */
public class Surface implements ISurface {
    private boolean remainOpen = true;
    private boolean isResized = true;
    private Viewport viewport;
    private final int fps;
    
    public Surface(Viewport viewport, int fps) {
        this.viewport = viewport;
        this.fps = fps;
    }
    
    @Override
    public void endOfFrame(int newFps) {
        Display.update();
        Display.sync(newFps);
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
    public void close() {
        Display.destroy();
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
    
    public static PixelFormat getPixelFormat() {
        return new PixelFormat();
    }
    
    public static PixelFormat getPixelFormat(int alpha, int depth, int stencil) {
        return new PixelFormat(alpha, depth, stencil);
    }
    
    public static ContextAttribs getContextAttribs() {
        return new ContextAttribs();
    }
    
    public static ContextAttribs getContextAttribs(int major, int minor) {
        return new ContextAttribs(major, minor);
    }
}
