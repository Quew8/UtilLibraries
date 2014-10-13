package com.quew8.gutils.desktop;

import com.quew8.gutils.Clock;
import com.quew8.gutils.desktop.windowing.ISurface;
import com.quew8.gutils.desktop.windowing.Window;
import com.quew8.gutils.opengl.Viewport;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import org.lwjgl.LWJGLException;

/**
 *
 * @author Quew8
 */
public class ProcessManager {
    private final ISurface surface;
    private final ArrayList<Process> processes = new ArrayList<Process>();
    
    public ProcessManager(Window.WindowParams params, Process[] processes) throws LWJGLException {
        this.surface = new Window(params, new URL[]{});
        this.processes.addAll(Arrays.asList(processes));
    }
    
    public final void play() {
        for(Process p: processes) {
            p.init();
        }
        Clock.begin();
        loop();
        for(Process p: processes) {
            p.deinit();
        }
    }
    
    private void loop() {
        while(surface.remainOpen()) {
            Clock.makeDelta();
            if(surface.isResized()) {
                //resize(surface.getViewport());
            }
            for(int i = 0; i < processes.size(); i++) {
                processes.get(i).onMadeCurrent();
                processes.get(i).update();
                processes.get(i).render();
                processes.get(i).onUnmadeCurrent();
            }
            surface.endOfFrame();
        }
    }
    
    /*private void debugLoop() {
        while(surface.remainOpen()) {
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
    }*/
    
    /*private void init() {
        
    }*/
    
    /*protected abstract void resize(Viewport viewport);
    protected abstract void update();
    protected abstract void render();*/
    //protected abstract void deinit();

    /*public void toggleFullscreen() {
        surface.toggleFullscreen();
    }

    public boolean isFullscreen() {
        return surface.isFullscreen();
    }*/
    
    public Viewport getViewport() {
        return surface.getViewport();
    }
    
    public void requestClose() {
        surface.requestClose();
    }
}
