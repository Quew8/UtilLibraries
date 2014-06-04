package com.quew8.gutils.desktop.windowing;

import com.quew8.gutils.opengl.Viewport;
import com.quew8.gutils.desktop.LWJGLUtils;
import java.awt.Canvas;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/**
 * 
 * @author Quew8
 */
public class EmbeddableSurface extends Canvas implements ISurface {
    private Surface surface;
    
    public EmbeddableSurface(final DisplayMode displayMode, int fps) {
        surface = new Surface(new Viewport(displayMode.getWidth(), displayMode.getHeight()), fps);
        addComponentListener(new ComponentAdapter(){
            @Override
            public void componentResized(ComponentEvent e) {
                EmbeddableSurface.this.onResize(getWidth(), getHeight());
                super.componentResized(e);
            }
        });
        setIgnoreRepaint(true);
        setFocusable(true);
        setSize(displayMode.getWidth(), displayMode.getHeight());
    }
    
    public EmbeddableSurface(int width, int height, int fps) throws LWJGLException {
        this(LWJGLUtils.getDisplayMode(width, height), fps);
    }
    
    public EmbeddableSurface(int fps) throws LWJGLException {
        this(Display.getDesktopDisplayMode(), fps);
    }
    
    @Override
    public void endOfFrame(int newFps) {
        surface.endOfFrame(newFps);
    }

    @Override
    public void endOfFrame() {
        surface.endOfFrame();
    }

    @Override
    public void onResize(int newWidth, int newHeight) {
        surface.onResize(newWidth, newHeight);
    }

    @Override
    public void close() {
        surface.close();
    }

    @Override
    public void requestClose() {
        surface.requestClose();
    }

    @Override
    public boolean remainOpen() {
        return surface.remainOpen();
    }

    @Override
    public boolean isResized() {
        return surface.isResized();
    }

    @Override
    public Viewport getViewport() {
        return surface.getViewport();
    }
    
    public void visible() throws LWJGLException {
        requestFocus();
        Display.setParent(this);
        Display.create();
    }
}
