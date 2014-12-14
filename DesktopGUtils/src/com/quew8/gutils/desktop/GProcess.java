package com.quew8.gutils.desktop;

import com.quew8.gutils.Clock;
import com.quew8.gutils.debug.DebugLogger;
import com.quew8.gutils.debug.LogLevel;
import com.quew8.gutils.desktop.windowing.Window;
import com.quew8.gutils.desktop.windowing.Window.WindowParams;
import com.quew8.gutils.opengl.Viewport;
import java.net.URL;

/**
 *
 * @author Quew8
 */
public abstract class GProcess {
    private final boolean debug;
    private DebugView debugView = null;
    private final Window window;
    
    public GProcess(boolean debug, WindowParams params, URL[] services) {
        this.debug = debug;
        this.window = new Window(params, services);
    }
    
    public GProcess(WindowParams params, URL[] services) {
        this(false, params, services);
    }
    
    public GProcess(boolean debug, WindowParams params) {
        this(debug, params, new URL[]{});
    }
    
    public GProcess(WindowParams params) {
        this(false, params);
    }
    
    public final void play() {
        if(debug) {
            DebugLogger.broadcast(LogLevel.VERBOSE, "Init Beginning");
        }
        init();
        Clock.begin();
        if(debug) {
            debugLoop();
            DebugLogger.broadcast(LogLevel.VERBOSE, "Deinit Beginning");
        } else {
            loop();
        }
        deinit();
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
                if(debugView != null) {
                    debugView.resize(window.getViewport());
                }
                resize(window.getViewport());
            }
            DebugLogger.broadcast(LogLevel.VERBOSE, "Update Beginning");
            if(debugView != null) {
                debugView.update();
            } else {
                update();
            }
            DebugLogger.broadcast(LogLevel.VERBOSE, "Render Beginning");
            if(debugView != null) {
                debugView.render();
            } else {
                render();
            }
            window.endOfFrame();
        }
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
    
    public void setDebugView(DebugView debugView) {
        if(this.debugView != null) {
            this.debugView.deinit();
        }
        this.debugView = debugView;
        if(debugView != null) {
            debugView.init();
            debugView.resize(window.getViewport());
        }
    }
    
    public static interface DebugView {
        void init();
        void resize(Viewport viewport);
        void update();
        void render();
        void deinit();
    }
}
