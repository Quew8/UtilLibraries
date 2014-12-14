package com.quew8.gutils.desktop.control;

import com.quew8.gutils.desktop.windowing.Window;
import org.lwjgl.glfw.GLFW;

/**
 *
 * @author Quew8
 */
public class Control extends AbstractControl {
    
    public Control(int key) {
        super(key);
    }
    
    /*protected boolean isKey(int key) {
        return this.key == key;
    }*/
    
    public void onAction(int action, int mods) {
        switch(action) {
            case GLFW.GLFW_PRESS: onPressed(mods); break;
            case GLFW.GLFW_REPEAT: onRepeat(mods); break;
            case GLFW.GLFW_RELEASE: onReleased(mods); break;
            default: throw new IllegalArgumentException();
        }
    }
    
    public void onPressed(int mods) {}
    public void onRepeat(int mods) {}
    public void onReleased(int mods) {}
}
