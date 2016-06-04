package com.quew8.gutils.desktop;

import com.quew8.gutils.Clock;
import com.quew8.gutils.debug.DebugLogger;
import com.quew8.gutils.debug.LogLevel;
import com.quew8.gutils.desktop.windowing.Window;
import com.quew8.gutils.desktop.windowing.Window.WindowParams;
import com.quew8.gutils.opengl.Viewport;

/**
 *
 * @author Quew8
 */
public abstract class GProcess {
    private final boolean debug;
    private final Window window;
    
    public GProcess(boolean debug, WindowParams params) {
        this.debug = debug;
        this.window = new Window(params);
    }
    
    public GProcess(WindowParams params) {
        this(false, params);
    }
    
    public final void play() {
        if(debug) {
            DebugLogger.broadcast(LogLevel.VERBOSE, "Init Beginning");
        }
        onMadeCurrent();
        init();
        Clock.begin();
        if(debug) {
            debugLoop();
            DebugLogger.broadcast(LogLevel.VERBOSE, "Deinit Beginning");
        } else {
            loop();
        }
        deinit();
        onUnmadeCurrent();
        window.close();
    }
    
    private void loop() {
        while(window.remainOpen()) {
            Clock.makeDelta();
            if(window.isResized()) {
                resize(window.getViewport());
            }
            update();
            render();
            window.endOfFrame();
        }
    }
    
    private void debugLoop() {
        while(window.remainOpen()) {
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
    }
    
    protected void onMadeCurrent() {
        window.makeCurrent();
    }
    
    protected void onUnmadeCurrent() {
        
    }
    
    protected abstract void init();
    protected abstract void resize(Viewport viewport);
    protected abstract void update();
    protected abstract void render();
    protected abstract void deinit();

    public Window getWindow() {
        return window;
    }
    
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
