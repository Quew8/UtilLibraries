package com.quew8.gutils.desktop.control;

import com.quew8.gutils.BufferUtils;
import com.quew8.gutils.desktop.windowing.Window;
import java.nio.ByteBuffer;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

/**
 *
 * @author Quew8
 */
public class WindowInputHandler {
    private ControlSet[] controls = new ControlSet[0];
    private final ControlEventSet keyControlSet = new ControlEventSet(this);
    private final ControlEventSet mouseControlSet = new ControlEventSet(this);
    private Window window;
    private boolean immediateMode = true;
    private double mouseX, mouseY, mouseDX = 0, mouseDY = 0;
    
    public WindowInputHandler setControls(ControlSet... controls) {
        this.controls = controls;
        return this;
    }
    
    public WindowInputHandler setWindow(Window window) {
        if(this.window != null) {
            this.window.setKeyCallback(null);
        }
        this.window = window;
        if(window != null) {
            ByteBuffer buf = BufferUtils.createByteBuffer(16);
            buf.limit(8);
            ByteBuffer bufX = buf.slice();
            buf.limit(16);
            buf.position(8);
            ByteBuffer bufY = buf.slice();
            window.getCursorPos(bufX.asDoubleBuffer(), bufY.asDoubleBuffer());
            mouseX = bufX.getDouble();
            mouseY = window.getViewport().getHeight() - bufY.getDouble();
            setImmediateMode(immediateMode);
        }
        return this;
    }
    
    public WindowInputHandler setImmediateMode(boolean immediateMode) {
        this.immediateMode = immediateMode;
        if(immediateMode) {
            keyControlSet.clear();
            mouseControlSet.clear();
            if(window != null) {
                window.setKeyCallback(new ImmediateKeyCallback());
                window.setMouseButtonCallback(new ImmediateMouseCallback());
                window.setCursorPosCallback(new ImmediateCursorPosCallback());
            }
        } else {
            mouseDX = 0;
            mouseDY = 0;
            if(window != null) {
                window.setKeyCallback(new BufferedKeyCallback());
                window.setMouseButtonCallback(new BufferedMouseCallback());
                window.setCursorPosCallback(new BufferedCursorPosCallback());
            }
        }
        return this;
    }
    
    public int getKey(int key) {
        return window.getKey(key);
    }

    public int getMouseButton(int button) {
        return window.getMouseButton(button);
    }

    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public double getMouseDX() {
        return mouseDX;
    }

    public double getMouseDY() {
        return mouseDY;
    }
    
    public void resetMouseMovement() {
        mouseDX = 0;
        mouseDY = 0;
    }
    
    public void checkControls() {
        for(ControlSet cs: controls) {
            cs.checkKeys(keyControlSet);
            cs.checkMouse(mouseControlSet);
        }
        keyControlSet.clear();
        mouseControlSet.clear();
    }
    
    private class ImmediateKeyCallback extends GLFWKeyCallback {
        
        @Override
        public void invoke(long window, int key, int scancode, int action, int mods) {
            for(ControlSet cs: controls) {
                if(cs.isActive() && cs.keyControlAction(WindowInputHandler.this, key, action, mods)) {
                    return;
                }
            }
        }
        
    }
    
    private class BufferedKeyCallback extends GLFWKeyCallback {
        
        @Override
        public void invoke(long window, int key, int scancode, int action, int mods) {
            keyControlSet.add(new ControlEvent(key, action, mods));
        }
        
    }
    
    private class ImmediateMouseCallback extends GLFWMouseButtonCallback {
        
        @Override
        public void invoke(long window, int button, int action, int mods) {
            for(ControlSet cs: controls) {
                if(cs.isActive() && cs.mouseControlAction(WindowInputHandler.this, button, action, mods)) {
                    return;
                }
            }
        }
        
    }
    
    private class BufferedMouseCallback extends GLFWMouseButtonCallback {
        
        @Override
        public void invoke(long window, int button, int action, int mods) {
            mouseControlSet.add(new ControlEvent(button, action, mods));
        }
        
    }
    
    private class ImmediateCursorPosCallback extends GLFWCursorPosCallback {
        
        @Override
        public void invoke(long window, double xpos, double ypos) {
            ypos = WindowInputHandler.this.window.getViewport().getHeight() - ypos;
            mouseDX = xpos - mouseX;
            mouseDX = ypos - mouseY;
            mouseX = xpos;
            mouseY = ypos;
            for(ControlSet cs: controls) {
                if(cs.isActive() && cs.mouseMovementAction(WindowInputHandler.this, mouseX, mouseY, mouseDX, mouseDY)) {
                    return;
                }
            }
        }
        
    }
    
    private class BufferedCursorPosCallback extends GLFWCursorPosCallback {
        
        @Override
        public void invoke(long window, double xpos, double ypos) {
            if(mouseDX == 0 && mouseDY == 0) {
                mouseControlSet.add(ControlEvent.MOUSE_MOVEMENT_EVENT);
            }
            ypos = WindowInputHandler.this.window.getViewport().getHeight() - ypos;
            mouseDX += xpos - mouseX;
            mouseDX += ypos - mouseY;
            mouseX = xpos;
            mouseY = ypos;
            
        }
        
    }
}
