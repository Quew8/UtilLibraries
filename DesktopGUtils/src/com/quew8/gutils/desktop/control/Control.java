package com.quew8.gutils.desktop.control;

import org.lwjgl.glfw.GLFW;

/**
 *
 * @author Quew8
 */
public class Control {
    private final int key;
    
    public Control(int key) {
        this.key = key;
    }
    
    protected void onAction(WindowInputHandler handler, int action, int mods) {
        switch(action) {
            case GLFW.GLFW_PRESS: onPressed(handler, mods); break;
            case GLFW.GLFW_REPEAT: onRepeat(handler, mods); break;
            case GLFW.GLFW_RELEASE: onReleased(handler, mods); break;
            default: throw new IllegalArgumentException();
        }
    }
    
    protected void onPressed(WindowInputHandler handler, int mods) {}
    protected void onRepeat(WindowInputHandler handler, int mods) {}
    protected void onReleased(WindowInputHandler handler, int mods) {}

    protected int getKey() {
        return key;
    }
}
