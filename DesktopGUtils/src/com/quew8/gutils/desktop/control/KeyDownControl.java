package com.quew8.gutils.desktop.control;

import com.quew8.gutils.desktop.windowing.Window;
import org.lwjgl.glfw.GLFW;

/**
 *
 * @author Quew8
 */
public class KeyDownControl extends AbstractControl {
    
    public KeyDownControl(int key) {
        super(key);
    }

    protected void checkKeys(Window window) {
        if(getKeyState(window) == GLFW.GLFW_PRESS) {
            onIsPressed();
        } else {
            System.out.println(getKeyState(window));
            onIsReleased();
        }
    }

    public void onIsPressed() {}

    public void onIsReleased() {}
}
