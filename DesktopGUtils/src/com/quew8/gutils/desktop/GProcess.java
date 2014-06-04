package com.quew8.gutils.desktop;

import com.quew8.gutils.Clock;
import com.quew8.gutils.desktop.windowing.Window;
import com.quew8.gutils.desktop.windowing.Window.WindowParams;
import com.quew8.gutils.opengl.Viewport;
import java.net.URL;
import org.lwjgl.LWJGLException;

/**
 *
 * @author Quew8
 */
public abstract class GProcess {
    private final Window window;
    
    public GProcess(WindowParams params, URL[] services) throws LWJGLException {
        this.window = new Window(params, services);
    } 
    
    public final void play() {
        init();
        Clock.begin();
        while (window.remainOpen()) {
            Clock.makeDelta();
            if(window.isResized()) {
                resize(window.getViewport());
            }
            update();
            render();
            window.endOfFrame();
        }
        deinit();
    }
    
    protected abstract void init();
    protected abstract void resize(Viewport viewport);
    protected abstract void update();
    protected abstract void render();
    protected abstract void deinit();

    protected void toggleFullscreen() {
        window.toggleFullscreen();
    }

    protected boolean isFullscreen() {
        return window.isFullscreen();
    }
    
    protected Viewport getViewport() {
        return window.getViewport();
    }
    
    protected void requestClose() {
        window.requestClose();
    }
}
