package com.quew8.gutils.desktop.windowing;

import com.quew8.gutils.BufferUtils;
import com.quew8.gutils.DebugBackend;
import com.quew8.gutils.PlatformBackend;
import com.quew8.gutils.debug.DebugLogger;
import com.quew8.gutils.desktop.DesktopBackend;
import com.quew8.gutils.desktop.DesktopBackend.DesktopContextBackend;
import com.quew8.gutils.opengl.OpenGL;
import static com.quew8.gutils.opengl.OpenGL.GL_FALSE;
import static com.quew8.gutils.opengl.OpenGL.GL_TRUE;
import com.quew8.gutils.opengl.Viewport;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import static org.lwjgl.glfw.Callbacks.errorCallbackPrint;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWCharCallback;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLContext;
import static org.lwjgl.system.MemoryUtil.NULL;
import org.lwjgl.system.libffi.Closure;

/**
 * 
 * @author Quew8
 */
public class Window extends Surface {
    private static int nWindows = 0;
    private static GLFWErrorCallback errorCallback;
    public final static String INIT_LOG = "INIT";
    
    private GLFWKeyCallback keyCallback = null;
    private GLFWCharCallback charCallback = null;
    private GLFWMouseButtonCallback mouseButtonCallback = null;
    private GLFWCursorPosCallback cursorPosCallback = null;
    private GLFWFramebufferSizeCallback framebufferSizeCallback = null;
    private final long address;
    private final DesktopContextBackend contextBackend;
    private final GLContext context;
    private final boolean isFullscreen;
    
    public Window(WindowParams windowParams, URL[] serviceLocations) {
        
        super(new Viewport(windowParams.width, windowParams.height), -1);
        this.address = createWindow(
                windowParams.title, 
                windowParams.width, windowParams.height, 
                windowParams.glMajorVersion, windowParams.glMinorVersion,
                windowParams.redBits, windowParams.greenBits, windowParams.blueBits, 
                windowParams.alphaBits, windowParams.depthBits, windowParams.stencilBits,
                windowParams.forwardCompatible, 
                windowParams.coreProfile, windowParams.compatibilityProfile,
                windowParams.fullscreen, windowParams.resizable, 
                windowParams.shareWith != null ? windowParams.shareWith.address : NULL,
                windowParams.centreWindow, windowParams.windowX, windowParams.windowY
        );
        this.isFullscreen = windowParams.fullscreen;
        this.context = GLContext.createFromCurrent();
        setFramebufferSizeCallback(new FramebufferResizeCallback());
        int oglVersion = getOpenGLVersion(context);
        int glslVersion = DesktopBackend.getGLSLVersionForOGL(oglVersion);
        this.contextBackend = DesktopBackend.getContextBackend(oglVersion, glslVersion, serviceLocations);
        if(PlatformBackend.isInitizlized()) {
            if(PlatformBackend.debug) {
                ((DesktopBackend)((DebugBackend)PlatformBackend.backend).getImplementation()).switchContextBackend(contextBackend);
            } else {
                ((DesktopBackend)PlatformBackend.backend).switchContextBackend(contextBackend);
            }
        } else {
            PlatformBackend.setBackend(new DesktopBackend(contextBackend));
        }
        DebugLogger.v(INIT_LOG, "Vendor: " + OpenGL.glGetString(OpenGL.GL_VENDOR));
        DebugLogger.v(INIT_LOG, "Renderer: " + OpenGL.glGetString(OpenGL.GL_RENDERER));
        DebugLogger.v(INIT_LOG, "Version: " + OpenGL.glGetString(OpenGL.GL_VERSION));
        DebugLogger.v(INIT_LOG, "GLSL Version: " + OpenGL.glGetString(OpenGL.GL_SHADING_LANGUAGE_VERSION));
    }
    
    public Window(WindowParams params) {
        this(params, new URL[]{});
    }
    
    @Override
    public void endOfFrame(int newFps) {
        if(glfwWindowShouldClose(address) == GL_TRUE) {
            requestClose();
        }
        glfwSwapBuffers(address);
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
        glfwDestroyWindow(address);
        if(keyCallback != null) {
            keyCallback.release();
        }
        if(charCallback != null) {
            charCallback.release();
        }
        if(mouseButtonCallback != null) {
            mouseButtonCallback.release();
        }
        if(cursorPosCallback != null) {
            cursorPosCallback.release();
        }
        if(framebufferSizeCallback != null) {
            framebufferSizeCallback.release();
        }
        unregisterWindow();
    }
    
    public void setWindowPosRelative(int xPos, int yPos) {
        if(isFullscreen) {
            throw new IllegalStateException("Cannot reposition fullscreen window.");
        } else {
            ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            ByteBuffer size = BufferUtils.createByteBuffer(8);
            glfwGetWindowSize(address, BufferUtils.getSlice(size, 0, 4), BufferUtils.getSlice(size, 4, 8));
            glfwSetWindowPos(
                address,
                ((GLFWvidmode.width(vidmode) - size.getInt()) / 2) + xPos,
                ((GLFWvidmode.height(vidmode) - size.getInt()) / 2) + yPos
            );
        }
    }
    
    public void setWindowPos(int xpos, int ypos) {
        if(isFullscreen) {
            throw new IllegalStateException("Cannot reposition fullscreen window.");
        } else {
            glfwSetWindowPos(address, xpos, ypos);
        }
    }
    
    public boolean setKeyCallback(GLFWKeyCallback keyCallback) {
        this.keyCallback = keyCallback;
        return releaseOldCallback(glfwSetKeyCallback(address, keyCallback));
    }
    
    public boolean setCharCallback(GLFWCharCallback charCallback) {
        this.charCallback = charCallback;
        return releaseOldCallback(glfwSetCharCallback(address, charCallback));
    }
    
    public boolean setMouseButtonCallback(GLFWMouseButtonCallback mouseButtonCallback) {
        this.mouseButtonCallback = mouseButtonCallback;
        return releaseOldCallback(glfwSetMouseButtonCallback(address, mouseButtonCallback));
    }
    
    public boolean setCursorPosCallback(GLFWCursorPosCallback cursorPosCallback) {
        this.cursorPosCallback = cursorPosCallback;
        return releaseOldCallback(glfwSetCursorPosCallback(address, cursorPosCallback));
    }
    
    private boolean setFramebufferSizeCallback(GLFWFramebufferSizeCallback framebufferSizeCallback) {
        this.framebufferSizeCallback = framebufferSizeCallback;
        return releaseOldCallback(glfwSetFramebufferSizeCallback(address, framebufferSizeCallback));
    }
    
    public int getKey(int key) {
        return glfwGetKey(address, key);
    }
    
    public int getMouseButton(int button) {
        return glfwGetMouseButton(address, button);
    }
    
    public void getCursorPos(DoubleBuffer xPos, DoubleBuffer yPos) {
        glfwGetCursorPos(address, xPos, yPos);
    }
    
    public void makeCurrent() {
        glfwMakeContextCurrent(address);
	GL.setCurrent(context);
        if(PlatformBackend.debug) {
            ((DesktopBackend)((DebugBackend)PlatformBackend.backend).getImplementation()).switchContextBackend(contextBackend);
        } else {
            ((DesktopBackend)PlatformBackend.backend).switchContextBackend(contextBackend);
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
    
    private static boolean releaseOldCallback(Closure closure) {
        if(closure != null) {
            closure.release();
            return true;
        } else {
            return false;
        }
    }
    
    private static long createWindow(String title, int width, int height,
            int majorVersion, int minorVersion, int redBits, int greenBits, 
            int blueBits, int alphaBits, int depthBits, int stencilBits, 
            boolean forwardCompatible, boolean coreProfile, 
            boolean compatibilityProfile, boolean fullscreen, boolean resizable, 
            long shareWith, boolean centreWindow, int windowX, int windowY) {
        
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
                shareWith
        );
        if(window == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window");
        }
        if(!fullscreen) {
            if(centreWindow) {
                ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
                glfwSetWindowPos(
                    window,
                    ((GLFWvidmode.width(vidmode) - width) / 2) + windowX,
                    ((GLFWvidmode.height(vidmode) - height) / 2) + windowY
                );
            } else {
                glfwSetWindowPos(window, windowX, windowY);
            }
        }
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);
        return window;
    }
    
    public static int getOpenGLVersion(GLContext context) {
        if(context.getCapabilities().OpenGL45)
            return 450;
        if(context.getCapabilities().OpenGL44)
            return 440;
        if(context.getCapabilities().OpenGL43)
            return 430;
        if(context.getCapabilities().OpenGL42)
            return 420;
        if(context.getCapabilities().OpenGL41)
            return 410;
        if(context.getCapabilities().OpenGL40)
            return 400;
        if(context.getCapabilities().OpenGL33)
            return 330;
        if(context.getCapabilities().OpenGL32)
            return 320;
        if(context.getCapabilities().OpenGL31)
            return 310;
        if(context.getCapabilities().OpenGL30)
            return 300;
        if(context.getCapabilities().OpenGL21)
            return 210;
        if(context.getCapabilities().OpenGL20)
            return 200;
        if(context.getCapabilities().OpenGL15)
            return 150;
        if(context.getCapabilities().OpenGL14)
            return 140;
        if(context.getCapabilities().OpenGL13)
            return 130;
        if(context.getCapabilities().OpenGL12)
            return 120;
        if(context.getCapabilities().OpenGL11)
            return 110;
        throw new IllegalStateException("No OpenGL Version Supported");
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
        private boolean forwardCompatible = false;
        private boolean coreProfile = false;
        private boolean compatibilityProfile = false;
        private boolean fullscreen = false;
        private boolean resizable = false;
        private Window shareWith = null;
        private boolean centreWindow = true;
        private int windowX = 0, windowY = 0;
        
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
        
        public WindowParams setForwardCompatbile(boolean forwardCompatible) {
            this.forwardCompatible = forwardCompatible;
            return this;
        }
        
        public WindowParams setCoreProfile(boolean core) {
            this.coreProfile = core;
            this.compatibilityProfile = !core;
            return this;
        }
        
        public WindowParams setCompatibilityProfile(boolean compatibility) {
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
        
        public WindowParams setShareWith(Window shareWith) {
            this.shareWith = shareWith;
            return this;
        }
        
        public WindowParams setCentreWindow(boolean centreWindow) {
            this.centreWindow = centreWindow;
            return this;
        }
        
        public WindowParams setWindowPos(int windowX, int windowY) {
            this.windowX = windowX;
            this.windowY = windowY;
            return this;
        }
    }
}
