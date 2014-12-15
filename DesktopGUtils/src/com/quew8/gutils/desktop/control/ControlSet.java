package com.quew8.gutils.desktop.control;

import com.quew8.gutils.ArrayUtils;
import java.util.Arrays;
import java.util.HashMap;
import org.lwjgl.glfw.GLFW;

/**
 * 
 * @author Quew8
 */
public class ControlSet {
    private boolean active;
    private final HashMap<Integer, Control> keyControls = new HashMap<>();
    private final HashMap<Integer, Control> mouseControls = new HashMap<>();
    private KeyDownControl[] keyDownControls = new KeyDownControl[0];
    private KeyDownControl[] mouseDownControls = new KeyDownControl[0];
    private MouseMovementControl mouseMovementControl = null;
    
    public ControlSet(boolean active) {
        this.active = active;
    }
    
    public ControlSet() {
        this(true);
    }
    
    public ControlSet addKeyControls(Control... controls) {
        for(Control c: controls) {
            if(keyControls.containsKey(c.getKey())) {
                throw new IllegalArgumentException("Set already contains control of " + c.getKey());
            }
            keyControls.put(c.getKey(), c);
        }
        return this;
    }
    
    public ControlSet setKeyControls(Control... controls) {
        keyControls.clear();
        return addKeyControls(controls);
    }
    
    public ControlSet addKeyDownControls(KeyDownControl... controls) {
        KeyDownControl[] newKeyDownControls = Arrays.copyOf(
                keyDownControls, keyDownControls.length + controls.length
        );
        System.arraycopy(controls, 0, newKeyDownControls, keyDownControls.length, controls.length);
        keyDownControls = newKeyDownControls;
        return this;
    }
    
    public ControlSet setKeyDownControls(KeyDownControl... controls) {
        keyDownControls = controls;
        return this;
    }
    
    public ControlSet addMouseControls(Control... controls) {
        for(Control c: controls) {
            if(mouseControls.containsKey(c.getKey())) {
                throw new IllegalArgumentException("Set already contains control of " + c.getKey());
            }
            mouseControls.put(c.getKey(), c);
        }
        return this;
    }
    
    public ControlSet setMouseControls(Control... controls) {
        mouseControls.clear();
        return addMouseControls(controls);
    }
    
    public ControlSet addMouseDownControls(KeyDownControl... controls) {
        KeyDownControl[] newMouseDownControls = Arrays.copyOf(
                mouseDownControls, mouseDownControls.length + controls.length
        );
        System.arraycopy(controls, 0, newMouseDownControls, mouseDownControls.length, controls.length);
        mouseDownControls = newMouseDownControls;
        return this;
    }
    
    public ControlSet setMouseDownControls(KeyDownControl... controls) {
        mouseDownControls = controls;
        return this;
    }
    
    public ControlSet setMouseMovementControl(MouseMovementControl control) {
        this.mouseMovementControl = control;
        return this;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public boolean isActive() {
        return active;
    }
    
    protected boolean hasKeyControls() {
        return !keyControls.isEmpty();
    }
    
    protected boolean hasMouseControls() {
        return !mouseControls.isEmpty();
    }
    
    protected boolean hasMouseMovementControl() {
        return mouseMovementControl != null;
    }
    
    public void checkKeys(ControlEventSet events) {
        if(isActive()) {
            for(int i = 0; i < events.getLength(); i++) {
                if(keyControlAction(events.getHandler(), events.get(i).getControl(), events.get(i).getState(), events.get(i).getMods())) {
                    events.get(i).consume();
                }
            }
            for(int i = 0; i < keyDownControls.length; i++) {
                if(events.getHandler().getKey(keyDownControls[i].getKey()) == GLFW.GLFW_PRESS) {
                    keyDownControls[i].onIsPressed(events.getHandler());
                } else {
                    keyDownControls[i].onIsReleased(events.getHandler());
                }
            }
        }
    }
    
    public void checkMouse(ControlEventSet events) {
        if(isActive()) {
            for(int i = 0; i < events.getLength(); i++) {
                if(events.get(i).isMouseMovement()) {
                    if(mouseMovementControl != null) {
                        mouseMovementControl.movement(
                                events.getHandler().getMouseX(), 
                                events.getHandler().getMouseY(), 
                                events.getHandler().getMouseDX(), 
                                events.getHandler().getMouseDY()
                        );
                        events.get(i).consume();
                        events.getHandler().resetMouseMovement();
                    }
                } else {
                    if(mouseControlAction(events.getHandler(), events.get(i).getControl(), events.get(i).getState(), events.get(i).getMods())) {
                        events.get(i).consume();
                    }
                }
            }
            for(int i = 0; i < mouseDownControls.length; i++) {
                if(events.getHandler().getMouseButton(mouseDownControls[i].getKey()) == GLFW.GLFW_PRESS) {
                    mouseDownControls[i].onIsPressed(events.getHandler());
                } else {
                    mouseDownControls[i].onIsReleased(events.getHandler());
                }
            }
        }
    }
    
    protected boolean keyControlAction(WindowInputHandler handler, int key, int action, int mods) {
        Control c;
        if((c = keyControls.get(key)) != null) {
            c.onAction(handler, action, mods);
            return true;
        }
        return false;
    }
    
    protected boolean mouseControlAction(WindowInputHandler handler, int button, int action, int mods) {
        Control c;
        if((c = mouseControls.get(button)) != null) {
            c.onAction(handler, action, mods);
            return true;
        }
        return false;
    }
    
    protected boolean mouseMovementAction(WindowInputHandler handler, 
            double xPos, double yPos, double dx, double dy) {
        
        if(mouseMovementControl != null) {
            mouseMovementControl.movement(xPos, yPos, dx, dy);
            return true;
        }
        return false;
    }
    
    public static void grabMouse(boolean grab) {
        //Mouse.setGrabbed(grab);
        throw new UnsupportedOperationException("");
    }
    
    public static void fixMousePosition(int x, int y) {
        /*mouseFixed = true;
        mouseX = x;
        mouseY = y;*/
        throw new UnsupportedOperationException("");
    }
    
    public static void unfixMousePosition() {
        //mouseFixed = false;
        throw new UnsupportedOperationException("");
    }
    
    /**
     * Creates controls for all used keys except 2, 3, grave and apostrophe.
     * 
     * @param console
     * @return 
     */
    private static Control[] createGeneralTextInputControls(IConsole console) {
        return new Control[]{
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_A, 'a', 'A', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_B, 'b', 'B', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_C, 'c', 'C', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_D, 'd', 'D', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_E, 'e', 'E', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_F, 'f', 'F', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_G, 'g', 'G', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_H, 'h', 'H', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_I, 'i', 'I', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_J, 'j', 'J', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_K, 'k', 'K', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_L, 'l', 'L', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_M, 'm', 'M', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_N, 'n', 'N', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_O, 'o', 'O', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_P, 'p', 'P', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_Q, 'q', 'Q', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_R, 'r', 'R', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_S, 's', 'S', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_T, 't', 'T', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_U, 'u', 'U', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_V, 'v', 'V', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_W, 'w', 'W', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_X, 'x', 'X', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_Y, 'y', 'Y', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_Z, 'z', 'Z', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_SPACE, ' ', ' ', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_0, '0', ')', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_1, '1', '!', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_4, '4', '$', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_5, '5', '%', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_6, '6', '^', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_7, '7', '&', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_8, '8', '*', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_9, '9', '(', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_KP_0, '0', '0', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_KP_1, '1', '1', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_KP_2, '2', '2', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_KP_3, '3', '3', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_KP_4, '4', '4', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_KP_5, '5', '5', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_KP_6, '6', '6', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_KP_7, '7', '7', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_KP_8, '8', '8', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_KP_9, '9', '9', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_MINUS, '-', '_', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_EQUAL, '=', '+', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_PERIOD, '.', '>', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_COMMA, ',', '<', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_LEFT_BRACKET, '[', '{', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_RIGHT_BRACKET, ']', '}', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_SEMICOLON, ';', ':', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_SLASH, '/', '?', console),
            new IConsole.CharTextInputControl(GLFW.GLFW_KEY_BACKSLASH, '\\', '|', console),
            new IConsole.TextInputControl(GLFW.GLFW_KEY_ENTER, console) {
                @Override
                public void onPressed(WindowInputHandler handler, int mods) {
                    console.returnKey();
                }
            },
            new IConsole.TextInputControl(GLFW.GLFW_KEY_BACKSPACE, console) {
                @Override
                public void onPressed(WindowInputHandler handler, int mods) {
                    console.backspaceKey();
                }
            },
            new IConsole.TextInputControl(GLFW.GLFW_KEY_DELETE, console) {
                @Override
                public void onPressed(WindowInputHandler handler, int mods) {
                    console.deleteKey();
                }
            },
            new IConsole.TextInputControl(GLFW.GLFW_KEY_LEFT, console) {
                @Override
                public void onPressed(WindowInputHandler handler, int mods) {
                    console.leftKey();
                }
            },
            new IConsole.TextInputControl(GLFW.GLFW_KEY_RIGHT, console) {
                @Override
                public void onPressed(WindowInputHandler handler, int mods) {
                    console.rightKey();
                }
            },
            new IConsole.TextInputControl(GLFW.GLFW_KEY_UP, console) {
                @Override
                public void onPressed(WindowInputHandler handler, int mods) {
                    console.upKey();
                }
            },
            new IConsole.TextInputControl(GLFW.GLFW_KEY_DOWN, console) {
                @Override
                public void onPressed(WindowInputHandler handler, int mods) {
                    console.downKey();
                }
            },
        };
    }
    
    public static Control[] createTextInputControls(IConsole console) {
        return ArrayUtils.concatVariableLengthArrays(new Control[][]{
            createGeneralTextInputControls(console),
            new Control[] {
                new IConsole.CharTextInputControl(GLFW.GLFW_KEY_2, '2', '"', console),
                new IConsole.CharTextInputControl(GLFW.GLFW_KEY_3, '3', '£', console),
                new IConsole.CharTextInputControl(GLFW.GLFW_KEY_GRAVE_ACCENT, '\'', '@', console),
                new IConsole.CharTextInputControl(GLFW.GLFW_KEY_APOSTROPHE, '#', '~', console),
            }
        });
    }
    
    public static Control[] createAmericanTextInputControls(IConsole console) {
        /*return ArrayUtils.concatVariableLengthArrays(new Control[][]{
            createGeneralTextInputControls(console),
            new Control[] {
                new IConsole.CharTextInputControl(Keyboard.KEY_2, '2', '@', console),
                new IConsole.CharTextInputControl(Keyboard.KEY_3, '3', '#', console),
                new IConsole.CharTextInputControl(Keyboard.KEY_APOSTROPHE, '\'', '"', console),
            }
        });*/
        throw new UnsupportedOperationException("");
    }
}