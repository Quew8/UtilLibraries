package com.quew8.gutils.desktop.control;

/**
 *
 * @author Quew8
 */
public class KeyDownControl extends AbstractControl {
    
    public KeyDownControl(int key) {
        super(key);
        ControlSet.addKeyRegister(key);
    }

    protected void checkKeys() {
        if(ControlSet.checkKeyRegister(key)) {
            onIsPressed();
        } else {
            onIsReleased();
        }
    }

    public void onIsPressed() {}

    public void onIsReleased() {}
}
