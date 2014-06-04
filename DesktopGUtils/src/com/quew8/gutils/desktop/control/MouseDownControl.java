package com.quew8.gutils.desktop.control;

/**
 *
 * @author Quew8
 */
public class MouseDownControl extends AbstractControl {
    public MouseDownControl(int key) {
        super(key);
        ControlSet.addMouseRegister(key);
    }
    
    protected void checkKeys() {
        if(ControlSet.checkMouseRegister(key)) {
            onIsPressed();
        } else {
            onIsReleased();
        }
    }
    
    public void onIsPressed() {}
    
    public void onIsReleased() {}
}
