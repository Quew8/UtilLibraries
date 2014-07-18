package com.quew8.gutils.desktop;

import com.quew8.gutils.Clock;
import com.quew8.gutils.debug.DebugLogger;
import com.quew8.gutils.debug.LogLevel;
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
        DebugLogger.broadcast(LogLevel.VERBOSE, "Init Beginning");
        init();
        Clock.begin();
        while (window.remainOpen()) {
            DebugLogger.broadcast(LogLevel.VERBOSE, "Loop Beginning");
            Clock.makeDelta();
            if(window.isResized()) {
                DebugLogger.broadcast(LogLevel.VERBOSE, "Resize Beginning");
                resize(window.getViewport());
            }
            DebugLogger.broadcast(LogLevel.VERBOSE, "Update Beginning");
            update();
            DebugLogger.broadcast(LogLevel.VERBOSE, "Render Beginning");
            render();
            window.endOfFrame();
        }
        DebugLogger.broadcast(LogLevel.VERBOSE, "Deinit Beginning");
        deinit();
    }
    
    protected abstract void init();
    protected abstract void resize(Viewport viewport);
    protected abstract void update();
    protected abstract void render();
    protected abstract void deinit();

    public void toggleFullscreen() {
        window.toggleFullscreen();
    }

    public boolean isFullscreen() {
        return window.isFullscreen();
    }
    
    public Viewport getViewport() {
        return window.getViewport();
    }
    
    public void requestClose() {
        window.requestClose();
    }
}
