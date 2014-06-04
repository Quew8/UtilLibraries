package com.quew8.gutils.desktop.control;

/**
 *
 * @author Quew8
 */
class AbstractControl {
    protected final int key;

    protected AbstractControl(int key) {
        this.key = key;
    }

    public int getMouseX() {
        return ControlSet.mouseX;
    }

    public int getMouseY() {
        return ControlSet.mouseY;
    }

    public int getMouseDX() {
        return ControlSet.mouseDX;
    }

    public int getMouseDY() {
        return ControlSet.mouseDY;
    }
}
