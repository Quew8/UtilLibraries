package com.quew8.gutils.desktop.windowing;

import com.quew8.gutils.PlatformBackend;
import com.quew8.gutils.debug.DebugLogger;
import com.quew8.gutils.desktop.DesktopBackend;
import com.quew8.gutils.opengl.OpenGL;
import static com.quew8.gutils.opengl.OpenGL.GL_FALSE;
import static com.quew8.gutils.opengl.OpenGL.GL_TRUE;
import com.quew8.gutils.opengl.Viewport;
import java.net.URL;
import java.nio.ByteBuffer;
import static org.lwjgl.glfw.Callbacks.errorCallbackPrint;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GLContext;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * 
 * @author Quew8
 */
public class Window extends Surface {
    private static int nWindows = 0;
    private static GLFWErrorCallback errorCallback;
    public final static String INIT_LOG = "INIT";
    
    private GLFWFramebufferSizeCallback framebufferSizeCallback = null;
    private GLFWKeyCallback keyCallback = null;
    private final long window;
    private final boolean isFullscreen;
    
    public Window(String title, int width, int height, int majorVersion, 
            int minorVersion, int redBits, int greenBits, int blueBits, 
            int alphaBits, int depthBits, int stencilBits, boolean forwardCompatible, 
            boolean coreProfile, boolean compatibilityProfile, boolean fullscreen, 
            boolean resizable, URL[] serviceLocations) {
        
        super(new Viewport(width, height), -1);
        this.window = createWindow(
                title, 
                width, height, 
                majorVersion, minorVersion,
                redBits, greenBits, blueBits, alphaBits,
                depthBits, stencilBits,
                forwardCompatible, coreProfile, compatibilityProfile,
                fullscreen, resizable
        );
        this.isFullscreen = fullscreen;
        setFramebufferSizeCallback(new FramebufferResizeCallback());
        GLContext.createFromCurrent();
        int oglVersion = ( ( majorVersion * 10) + minorVersion ) * 10;
        int glslVersion = DesktopBackend.getGLSLVersionForOGL(oglVersion);
        PlatformBackend.setBackend(new DesktopBackend(oglVersion, glslVersion, serviceLocations));
        DebugLogger.v(INIT_LOG, "Vendor: " + OpenGL.glGetString(OpenGL.GL_VENDOR));
        DebugLogger.v(INIT_LOG, "Renderer: " + OpenGL.glGetString(OpenGL.GL_RENDERER));
        DebugLogger.v(INIT_LOG, "Version: " + OpenGL.glGetString(OpenGL.GL_VERSION));
        DebugLogger.v(INIT_LOG, "GLSL Version: " + OpenGL.glGetString(OpenGL.GL_SHADING_LANGUAGE_VERSION));
    }
    
    public Window(String title, int width, int height, int majorVersion, 
            int minorVersion, int redBits, int greenBits, int blueBits, 
            int alphaBits, int depthBits, int stencilBits, boolean forwardCompatible, 
            boolean coreProfile, boolean compatibilityProfile, boolean fullscreen, 
            boolean resizable) {
        
        this(
                title, 
                width, height, 
                majorVersion, minorVersion,
                redBits, greenBits, blueBits, alphaBits,
                depthBits, stencilBits,
                forwardCompatible, coreProfile, compatibilityProfile,
                fullscreen, resizable,
                new URL[]{}
        );
    }
    
    public Window(WindowParams params, URL[] serviceLocations) {
        this(
                params.title, 
                params.width, params.height, 
                params.glMajorVersion, params.glMinorVersion,
                params.redBits, params.greenBits, params.blueBits,
                params.alphaBits, params.depthBits, params.stencilBits,
                params.fowardCompatible, 
                params.coreProfile, params.compatibilityProfile,
                params.fullscreen,
                params.resizable, 
                serviceLocations
        );
    }
    
    public Window(WindowParams params) {
        this(params, new URL[]{});
    }
    
    @Override
    public void endOfFrame(int newFps) {
        if(glfwWindowShouldClose(window) == GL_TRUE) {
            requestClose();
        }
        glfwSwapBuffers(window);
        glfwPollEvents();
    }
    
    public void toggleFullscreen() {
        throw new UnsupportedOperationException("Fullscreen toggling is currently not supported.");
    }
    
    public boolean isFullscreen() {
        return isFullscreen;
    }

    @Override
    public void close() {
        glfwDestroyWindow(window);
        if(keyCallback != null) {
            keyCallback.release();
        }
        if(framebufferSizeCallback != null) {
            framebufferSizeCallback.release();
        }
        unregisterWindow();
    }
    
    public long getAddress() {
        return window;
    }
    
    public boolean setKeyCallback(GLFWKeyCallback keyCallback) {
        this.keyCallback = keyCallback;
        keyCallback = glfwSetKeyCallback(window, keyCallback);
        if(keyCallback != null) {
            keyCallback.release();
            return true;
        } else {
            return false;
        }
    }
    
    private boolean setFramebufferSizeCallback(GLFWFramebufferSizeCallback framebufferSizeCallback) {
        this.framebufferSizeCallback = framebufferSizeCallback;
        framebufferSizeCallback = glfwSetFramebufferSizeCallback(window, framebufferSizeCallback);
        if(framebufferSizeCallback != null) {
            framebufferSizeCallback.release();
            return true;
        } else {
            return false;
        }
    }
    
    private static void registerWindow() {
        if(nWindows == 0) {
            glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));
            if(glfwInit() != OpenGL.GL_TRUE) {
                throw new IllegalStateException("Failed to initialize GLFW");
            }
        }
        nWindows++;
    }
    
    private static void unregisterWindow() {
        nWindows--;
        if(nWindows == 0) {
            glfwTerminate();
            errorCallback.release();
        }
    }
    
    private static long createWindow(String title, int width, int height,
            int majorVersion, int minorVersion, int redBits, int greenBits, 
            int blueBits, int alphaBits, int depthBits, int stencilBits, 
            boolean forwardCompatible, boolean coreProfile, 
            boolean compatibilityProfile, boolean fullscreen, 
            boolean resizable) {
        
        if(coreProfile && compatibilityProfile) {
            throw new IllegalArgumentException("Cannot be core and compatibilty profile");
        }
        registerWindow();
        
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, 
                resizable ? 
                        GL_TRUE : 
                        GL_FALSE
        );
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, majorVersion);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, minorVersion);
        glfwWindowHint(GLFW_RED_BITS, redBits);
        glfwWindowHint(GLFW_GREEN_BITS, greenBits);
        glfwWindowHint(GLFW_BLUE_BITS, blueBits);
        glfwWindowHint(GLFW_ALPHA_BITS, alphaBits);
        glfwWindowHint(GLFW_DEPTH_BITS, depthBits);
        glfwWindowHint(GLFW_STENCIL_BITS, stencilBits);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, 
                forwardCompatible ? 
                        GL_TRUE : 
                        GL_FALSE
        );
        glfwWindowHint(GLFW_OPENGL_PROFILE, 
                coreProfile ? 
                        GLFW_OPENGL_CORE_PROFILE : 
                        compatibilityProfile ?
                                GLFW_OPENGL_COMPAT_PROFILE :
                                GLFW_OPENGL_ANY_PROFILE
        );
        long window = glfwCreateWindow(
                width, height, title, 
                fullscreen ? 
                        glfwGetPrimaryMonitor() :
                        NULL, 
                NULL
        );
        if(window == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window");
        }
        if(!fullscreen) {
            ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(
                window,
                (GLFWvidmode.width(vidmode) - width) / 2,
                (GLFWvidmode.height(vidmode) - height) / 2
            );
        }
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);
        return window;
    }
    
    private class FramebufferResizeCallback extends GLFWFramebufferSizeCallback {

        @Override
        public void invoke(long window, int width, int height) {
            onResize(width, height);
        }
        
    }
    
    public static class WindowParams {
        private String title = "Quew8 Game";
        private int width = 800, height = 600;
        private int glMajorVersion = 1, glMinorVersion = 0;
        private int redBits = 8, greenBits = 8, blueBits = 8;
        private int alphaBits = 8, depthBits = 24, stencilBits = 8;
        private boolean fowardCompatible = false;
        private boolean coreProfile = false;
        private boolean compatibilityProfile = false;
        private boolean fullscreen = false;
        private boolean resizable = false;
        
        public WindowParams setTitle(String title) {
            this.title = title;
            return this;
        }
        
        public WindowParams setDimensions(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }
        
        public WindowParams setOpenGLVersion(int majorVer, int minorVer) {
            this.glMajorVersion = majorVer;
            this.glMinorVersion = minorVer;
            return this;
        }
        
        public WindowParams setColourBits(int red, int green, int blue) {
            this.redBits = red;
            this.greenBits = green;
            this.blueBits = blue;
            return this;
        }
        
        public WindowParams setAlphaBits(int alpha) {
            this.alphaBits = alpha;
            return this;
        }
        
        public WindowParams setDepthBits(int depth) {
            this.depthBits = depth;
            return this;
        }
        
        public WindowParams setStencilBits(int stencil) {
            this.stencilBits = stencil;
            return this;
        }
        
        public WindowParams setForwardCompatbile(boolean fowardCompatible) {
            this.fowardCompatible = fowardCompatible;
            return this;
        }
        
        public WindowParams setCoreProfile(boolean core) {
            this.coreProfile = core;
            this.compatibilityProfile = !core;
            return this;
        }
        
        public WindowParams setCompatibiltyProfile(boolean compatibility) {
            this.compatibilityProfile = compatibility;
            this.coreProfile = !compatibility;
            return this;
        }
        
        public WindowParams setFullscreen(boolean fullscreen) {
            this.fullscreen = fullscreen;
            return this;
        }
        
        public WindowParams setResizable(boolean resizable) {
            this.resizable = resizable;
            return this;
        }
    }
}
