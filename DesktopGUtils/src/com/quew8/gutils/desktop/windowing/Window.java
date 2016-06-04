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
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWCharCallback;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Callback;
import static org.lwjgl.system.MemoryUtil.NULL;

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
    private final GLCapabilities context;
    private long currentCursor = NULL;
    private final boolean isFullscreen;
    
    public Window(WindowParams windowParams) {
        
        super(new Viewport(0, 0), -1);
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
        IntBuffer widthBuf = BufferUtils.createIntBuffer(1), heightBuf = BufferUtils.createIntBuffer(1);
        glfwGetWindowSize(this.address, widthBuf, heightBuf);
        onResize(widthBuf.get(), heightBuf.get());
        this.isFullscreen = windowParams.fullscreen;
        this.context = GL.createCapabilities();
        int oglVersion = getOpenGLVersion(context);
        int glslVersion = DesktopBackend.getGLSLVersionForOGL(oglVersion);
        this.contextBackend = DesktopBackend.getContextBackend(oglVersion, glslVersion);
        if(PlatformBackend.isInitizlized()) {
            if(PlatformBackend.debug) {
                ((DesktopBackend)((DebugBackend)PlatformBackend.backend).getImplementation()).switchContextBackend(contextBackend);
            } else {
                ((DesktopBackend)PlatformBackend.backend).switchContextBackend(contextBackend);
            }
        } else {
            PlatformBackend.setBackend(new DesktopBackend(contextBackend));
        }
        setFramebufferSizeCallback(new FramebufferResizeCallback());
        DebugLogger.v(INIT_LOG, "Vendor: " + OpenGL.glGetString(OpenGL.GL_VENDOR));
        DebugLogger.v(INIT_LOG, "Renderer: " + OpenGL.glGetString(OpenGL.GL_RENDERER));
        DebugLogger.v(INIT_LOG, "Version: " + OpenGL.glGetString(OpenGL.GL_VERSION));
        DebugLogger.v(INIT_LOG, "GLSL Version: " + OpenGL.glGetString(OpenGL.GL_SHADING_LANGUAGE_VERSION));
    }
    
    @Override
    public void endOfFrame(int newFps) {
        if(glfwWindowShouldClose(address)) {
            requestClose();
        }
        glfwSwapBuffers(address);
        glfwPollEvents();
    }
    
    public void setStandardCursor(int type) {
        setCursor(glfwCreateStandardCursor(type));
    }
    
    public boolean isDefaultCursor() {
        return currentCursor == NULL;
    }
    
    public void setDefaultCursor() {
        setCursor(NULL);
    }
    
    private void setCursor(long cursor) {
        glfwSetCursor(address, cursor);
        if(currentCursor != NULL) {
            glfwDestroyCursor(currentCursor);
        }
        currentCursor = cursor;
    }
    
    public void setMouseGrabbed(boolean grabbed) {
        glfwSetInputMode(address, GLFW_CURSOR, grabbed ? GLFW_CURSOR_DISABLED : GLFW_CURSOR_NORMAL);
    }
    
    public boolean isMouseGrabbed() {
        return glfwGetInputMode(address, GLFW_CURSOR) == GLFW_CURSOR_DISABLED;
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
            keyCallback.free();
        }
        if(charCallback != null) {
            charCallback.free();
        }
        if(mouseButtonCallback != null) {
            mouseButtonCallback.free();
        }
        if(cursorPosCallback != null) {
            cursorPosCallback.free();
        }
        if(framebufferSizeCallback != null) {
            framebufferSizeCallback.free();
        }
        unregisterWindow();
    }
    
    public void setWindowPosRelative(int xPos, int yPos) {
        if(isFullscreen) {
            throw new IllegalStateException("Cannot reposition fullscreen window.");
        } else {
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            IntBuffer width = BufferUtils.createIntBuffer(1);
            IntBuffer height = BufferUtils.createIntBuffer(1);
            glfwGetWindowSize(address, width, height);
            glfwSetWindowPos(
                address,
                ((vidmode.width() - width.get()) / 2) + xPos,
                ((vidmode.height() - height.get()) / 2) + yPos
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
        if(PlatformBackend.debug) {
            ((DesktopBackend)((DebugBackend)PlatformBackend.backend).getImplementation()).switchContextBackend(contextBackend);
        } else {
            ((DesktopBackend)PlatformBackend.backend).switchContextBackend(contextBackend);
        }
    }
    
    private static void registerWindow() {
        if(nWindows == 0) {
            glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
            if(!glfwInit()) {
                throw new IllegalStateException("Failed to initialize GLFW");
            }
        }
        nWindows++;
    }
    
    private static void unregisterWindow() {
        nWindows--;
        if(nWindows == 0) {
            glfwTerminate();
            errorCallback.free();
        }
    }
    
    private static boolean releaseOldCallback(Callback closure) {
        if(closure != null) {
            closure.free();
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
        glfwWindowHint(GLFW_RESIZABLE, resizable ? GL_TRUE : GL_FALSE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, majorVersion);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, minorVersion);
        glfwWindowHint(GLFW_RED_BITS, redBits);
        glfwWindowHint(GLFW_GREEN_BITS, greenBits);
        glfwWindowHint(GLFW_BLUE_BITS, blueBits);
        glfwWindowHint(GLFW_ALPHA_BITS, alphaBits);
        glfwWindowHint(GLFW_DEPTH_BITS, depthBits);
        glfwWindowHint(GLFW_STENCIL_BITS, stencilBits);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, forwardCompatible ? GL_TRUE : GL_FALSE);
        glfwWindowHint(GLFW_OPENGL_PROFILE, 
                coreProfile ? 
                        GLFW_OPENGL_CORE_PROFILE : 
                        compatibilityProfile ?
                                GLFW_OPENGL_COMPAT_PROFILE :
                                GLFW_OPENGL_ANY_PROFILE
        );
        long primaryMonitor = glfwGetPrimaryMonitor();
        long monitor = NULL;
        int windowXRes = width, windowYRes = height;
        if(fullscreen) {
            monitor = primaryMonitor;
            GLFWVidMode vidMode = glfwGetVideoMode(monitor);
            windowXRes = vidMode.width();
            windowYRes = vidMode.height();
        }
        long window = glfwCreateWindow(windowXRes, windowYRes, title, monitor, shareWith);
        if(window == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window");
        }
        if(!fullscreen) {
            if(centreWindow) {
                GLFWVidMode vidmode = glfwGetVideoMode(primaryMonitor);
                glfwSetWindowPos(
                    window,
                    ((vidmode.width() - width) / 2) + windowX,
                    ((vidmode.height() - height) / 2) + windowY
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
    
    public static int getOpenGLVersion(GLCapabilities capabilities) {
        if(capabilities.OpenGL45) {
            return 450;
        } else if(capabilities.OpenGL44) {
            return 440;
        } else if(capabilities.OpenGL43) {
            return 430;
        } else if(capabilities.OpenGL42) {
            return 420;
        } else if(capabilities.OpenGL41) {
            return 410;
        } else if(capabilities.OpenGL40) {
            return 400;
        } else if(capabilities.OpenGL33) {
            return 330;
        } else if(capabilities.OpenGL32) {
            return 320;
        } else if(capabilities.OpenGL31) {
            return 310;
        } else if(capabilities.OpenGL30) {
            return 300;
        } else if(capabilities.OpenGL21) {
            return 210;
        } else if(capabilities.OpenGL20) {
            return 200;
        } else if(capabilities.OpenGL15) {
            return 150;
        } else if(capabilities.OpenGL14) {
            return 140;
        } else if(capabilities.OpenGL13) {
            return 130;
        } else if(capabilities.OpenGL12) {
            return 120;
        } else if(capabilities.OpenGL11) {
            return 110;
        }
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
