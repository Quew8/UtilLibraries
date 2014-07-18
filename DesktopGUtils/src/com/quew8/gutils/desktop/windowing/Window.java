package com.quew8.gutils.desktop.windowing;

import com.quew8.gutils.PlatformBackend;
import com.quew8.gutils.debug.DebugLogger;
import com.quew8.gutils.desktop.DesktopBackend;
import com.quew8.gutils.desktop.LWJGLUtils;
import com.quew8.gutils.opengl.OpenGL;
import com.quew8.gutils.opengl.Viewport;
import java.net.URL;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

/**
 * 
 * @author Quew8
 */
public class Window extends Surface {
    public final static String INIT_LOG = "INIT";
    
    public Window(String title, DisplayMode displayMode, PixelFormat pixelFormat, ContextAttribs contextAttribs, int fps, boolean resizable, URL[] serviceLocations) throws LWJGLException {
        super(new Viewport(displayMode.getWidth(), displayMode.getHeight()), fps);
        Display.setDisplayMode(displayMode);
        Display.setTitle(title);
        Display.setResizable(resizable);
        Display.create(pixelFormat, contextAttribs);
        PlatformBackend.setBackend(new DesktopBackend(serviceLocations));
        DebugLogger.v(INIT_LOG, "Vendor: " + OpenGL.glGetString(OpenGL.GL_VENDOR));
        DebugLogger.v(INIT_LOG, "Renderer: " + OpenGL.glGetString(OpenGL.GL_RENDERER));
        DebugLogger.v(INIT_LOG, "Version: " + OpenGL.glGetString(OpenGL.GL_VERSION));
        DebugLogger.v(INIT_LOG, "GLSL Version: " + OpenGL.glGetString(OpenGL.GL_SHADING_LANGUAGE_VERSION));
    }
    
    public Window(WindowParams params, URL[] serviceLocations) throws LWJGLException {
        this(params.title, params.displayMode, params.pixelFormat, params.contextAttribs, params.fps, params.resizable, serviceLocations);
    }
    
    public Window(WindowParams params) throws LWJGLException {
        this(params, new URL[]{});
    }
    
    @Override
    public void endOfFrame(int newFps) {
        if(Display.isCloseRequested()) {
            requestClose();
        }
        if(Display.wasResized()) {
            onResize(Display.getWidth(), Display.getHeight());
        }
        super.endOfFrame(newFps);
    }
    
    public void toggleFullscreen() {
        try {
            Display.setFullscreen(!isFullscreen());
        } catch (LWJGLException ex) {
            System.err.println("Display is not capable of fullscreen mode");
        }
    }
    
    public boolean isFullscreen() {
        return Display.isFullscreen();
    }
    
    public static class WindowParams {
        private String title = "Quew8 Game";
        private DisplayMode displayMode = LWJGLUtils.getDesktopDisplayMode();
        private PixelFormat pixelFormat = Window.getPixelFormat();
        private ContextAttribs contextAttribs = Window.getContextAttribs();
        private int fps = 60;
        private boolean resizable = false;
        
        public WindowParams setTitle(String title) {
            this.title = title;
            return this;
        }
        
        public WindowParams setDisplayMode(DisplayMode displayMode) {
            this.displayMode = displayMode;
            return this;
        }
        
        public WindowParams setPixelFormat(PixelFormat pixelFormat) {
            this.pixelFormat = pixelFormat;
            return this;
        }
        
        public WindowParams setContextAttribs(ContextAttribs contextAttribs) {
            this.contextAttribs = contextAttribs;
            return this;
        }
        
        public WindowParams setFps(int fps) {
            this.fps = fps;
            return this;
        }
        
        public WindowParams setResizable(boolean resizable) {
            this.resizable = resizable;
            return this;
        }
    }
}
