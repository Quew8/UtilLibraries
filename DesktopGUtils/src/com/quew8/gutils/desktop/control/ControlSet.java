package com.quew8.gutils.desktop.control;

import com.quew8.gutils.desktop.windowing.Window;
import java.util.HashMap;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
//import org.lwjgl.input.Keyboard;
//import org.lwjgl.input.Mouse;

/**
 * 
 * @author Quew8
 */
public class ControlSet {
    private Window window;
    private boolean immediateMode = true;
    //private final Control[] keyControls;
    private final HashMap<Integer, Control> keyControls = new HashMap<>();
    private final KeyDownControl[] keyDownControls;
    private final MouseMovementControl mouseMovementControl;
    private final Control[] mouseControls;
    private final MouseDownControl[] mouseDownControls;
    private final ControlEventSet controlSet = new ControlEventSet();
    private static boolean mouseFixed = false;
    protected static int mouseX, mouseY, mouseDX = 0, mouseDY = 0;
    
    public ControlSet(Window window, Control[] keyControls, KeyDownControl[] keyDownControls, 
            MouseMovementControl mouseMovementControl, Control[] mouseControls, 
            MouseDownControl[] mouseDownControls) {
        
        this.window = window;
        if(keyControls != null) {
            for(Control c: keyControls) {
                this.keyControls.put(c.key, c);
            }
        }
        this.keyDownControls = keyDownControls == null ? new KeyDownControl[0] : keyDownControls;
        this.mouseMovementControl = mouseMovementControl == null ? new MouseMovementControl() : mouseMovementControl;
        this.mouseControls = mouseControls == null ? new Control[0] : mouseControls;
        this.mouseDownControls = mouseDownControls == null ? new MouseDownControl[0] : mouseDownControls;
        if(window != null) {
            if(immediateMode) {
                window.setKeyCallback(new ImmediateKeyCallback());
            } else {
                throw new UnsupportedOperationException("");
            }
        }
    }
    
    public ControlSet(Window window, Control[] keyControls, KeyDownControl[] keyDownControls) {
        this(window, keyControls, keyDownControls, null, null, null);
    }
    
    public ControlSet(Window window, MouseMovementControl mouseMovementControl, 
            Control[] mouseControls, MouseDownControl[] mouseDownControls) {
        
        this(window, null, null, mouseMovementControl, mouseControls, mouseDownControls);
    }
    
    public void setWindow(Window window) {
        if(this.window != null) {
            this.window.setKeyCallback(null);
        }
        this.window = window;
        if(window != null) {
            if(immediateMode) {
                window.setKeyCallback(new ImmediateKeyCallback());
            } else {
                controlSet.clear();
                window.setKeyCallback(new BufferedKeyCallback());
            }
        }
    }
    
    public void setImmediateMode(boolean immediateMode) {
        this.immediateMode = immediateMode;
        if(immediateMode) {
            window.setKeyCallback(new ImmediateKeyCallback());
        } else {
            controlSet.clear();
            window.setKeyCallback(new BufferedKeyCallback());
        }
    }
    
    public void checkKeys() {
        /*java.lang.reflect.Field[] fields = Keyboard.class.getFields();
        for(KeyEvent ke: ks.events) {
            if(ke.state) {
                for (java.lang.reflect.Field f : fields) {
                    try {
                        if (java.lang.reflect.Modifier.isStatic(f.getModifiers()) && f.getInt(null) == ke.key) {
                            DebugLogger.log("CONTROL INPUT", f.getName() + " " + ke.key);
                        }
                    } catch (IllegalArgumentException | IllegalAccessException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }*/
        /*outerLoop:
        for(int i = 0; i < keyControls.length; i++) {
            for(int k = 0; k < ks.getLength(); k++) {
                if(keyControls[i].isKey(ks.getControl(k))) {
                    keyControls[i].key(ks.getState(k));
                    ks.consume(k);
                    break outerLoop;
                }
            }
        }*/
        if(!immediateMode) {
            for(int i = 0; i < controlSet.getLength(); i++) {
                keyControls.get(controlSet.getControl(i)).onAction(controlSet.getState(i), controlSet.getMods(i));
            }
            controlSet.clear();
        }
        for(int i = 0; i < keyDownControls.length; i++) {
            if(GLFW.glfwGetKey(window.getAddress(), keyDownControls[i].key) == GLFW.GLFW_PRESS) {
                keyDownControls[i].onIsPressed();
            } else {
                keyDownControls[i].onIsReleased();
            }
            //keyDownControls[i].checkKeys(window);
        }
    }
    
    public void checkMouse(ControlEventSet ks) {
        throw new UnsupportedOperationException();
        /*outerLoop:
        for(int i = 0; i < mouseControls.length; i++) {
            for(int k = 0; k < ks.getLength(); k++) {
                if(mouseControls[i].isKey(ks.getControl(k))) {
                    mouseControls[i].key(ks.getState(k));
                    ks.consume(k);
                    break outerLoop;
                }
            }
        }
        for(int i = 0; i < mouseDownControls.length; i++) {
            mouseDownControls[i].checkKeys();
        }
        if(mouseDX != 0 && mouseDY != 0) {
            mouseMovementControl.movement(mouseX, mouseY, mouseDX, mouseDY);
        }*/
    }
    
    public static ControlEventSet createKeySet() {
        /*ArrayList<ControlEvent> events = new ArrayList<>();
        while(Keyboard.next()) {
            events.add(new ControlEvent(Keyboard.getEventKey(), Keyboard.getEventKeyState()));
            if(keyRegisters.containsKey(Keyboard.getEventKey())) {
                keyRegisters.put(Keyboard.getEventKey(), Keyboard.getEventKeyState());
            }
        }
        return new ControlEventSet(events.toArray(new ControlEvent[events.size()]));*/
        throw new UnsupportedOperationException("");
    }
    
    public static ControlEventSet createMouseSet() {
        /*mouseDX = 0;
        mouseDY = 0;
        ArrayList<ControlEvent> events = new ArrayList<>();
        while(Mouse.next()) {
            int dx = Mouse.getEventDX();
            int dy = Mouse.getEventDY();
            if((dx | dy) == 0) {
                events.add(new ControlEvent(Mouse.getEventButton(), Mouse.getEventButtonState()));
                if(mouseRegisters.containsKey(Mouse.getEventButton())) {
                    mouseRegisters.put(Mouse.getEventButton(), Mouse.getEventButtonState());
                }
            } else {
                mouseDX += Mouse.getEventDX();
                mouseDY += Mouse.getEventDY();
            }
        }
        if(!mouseFixed) {
            mouseX = Mouse.getX();
            mouseY = Mouse.getY();
        } else {
            Mouse.setCursorPosition(mouseX, mouseY);
        }
        return new ControlEventSet(events.toArray(new ControlEvent[events.size()]));*/
        throw new UnsupportedOperationException("");
    }
    
    /*public static void addMouseRegister(int key) {
        if(mouseRegisters.containsKey(key)) {
            return;
        }
        mouseRegisters.put(key, false);
    }
    
    public static void grabMouse(boolean grab) {
        //Mouse.setGrabbed(grab);
        throw new UnsupportedOperationException("");
    }
    
    public static boolean checkMouseRegister(int key) {
        return mouseRegisters.get(key);
    }*/
    
    public static void fixMousePosition(int x, int y) {
        mouseFixed = true;
        mouseX = x;
        mouseY = y;
    }
    
    public static void unfixMousePosition() {
        mouseFixed = false;
    }
    
    /**
     * Creates controls for all used keys except 2, 3, grave and apostrophe.
     * 
     * @param console
     * @return 
     */
    private static Control[] createGeneralTextInputControls(IConsole console) {
        /*return new Control[]{
            new IConsole.CharTextInputControl(Keyboard.KEY_A, 'a', 'A', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_B, 'b', 'B', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_C, 'c', 'C', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_D, 'd', 'D', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_E, 'e', 'E', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_F, 'f', 'F', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_G, 'g', 'G', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_H, 'h', 'H', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_I, 'i', 'I', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_J, 'j', 'J', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_K, 'k', 'K', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_L, 'l', 'L', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_M, 'm', 'M', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_N, 'n', 'N', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_O, 'o', 'O', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_P, 'p', 'P', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_Q, 'q', 'Q', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_R, 'r', 'R', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_S, 's', 'S', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_T, 't', 'T', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_U, 'u', 'U', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_V, 'v', 'V', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_W, 'w', 'W', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_X, 'x', 'X', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_Y, 'y', 'Y', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_Z, 'z', 'Z', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_SPACE, ' ', ' ', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_0, '0', ')', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_1, '1', '!', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_4, '4', '$', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_5, '5', '%', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_6, '6', '^', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_7, '7', '&', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_8, '8', '*', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_9, '9', '(', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_NUMPAD0, '0', '0', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_NUMPAD1, '1', '1', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_NUMPAD2, '2', '2', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_NUMPAD3, '3', '3', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_NUMPAD4, '4', '4', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_NUMPAD5, '5', '5', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_NUMPAD6, '6', '6', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_NUMPAD7, '7', '7', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_NUMPAD8, '8', '8', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_NUMPAD9, '9', '9', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_MINUS, '-', '_', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_EQUALS, '=', '+', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_PERIOD, '.', '>', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_COMMA, ',', '<', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_LBRACKET, '[', '{', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_RBRACKET, ']', '}', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_SEMICOLON, ';', ':', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_SLASH, '/', '?', console),
            new IConsole.CharTextInputControl(Keyboard.KEY_BACKSLASH, '\\', '|', console),
            new IConsole.TextInputControl(Keyboard.KEY_RETURN, console) {
                @Override
                public void onPressed() {
                    console.returnKey();
                }
            },
            new IConsole.TextInputControl(Keyboard.KEY_BACK, console) {
                @Override
                public void onPressed() {
                    console.backspaceKey();
                }
            },
            new IConsole.TextInputControl(Keyboard.KEY_DELETE, console) {
                @Override
                public void onPressed() {
                    console.deleteKey();
                }
            },
            new IConsole.TextInputControl(Keyboard.KEY_LEFT, console) {
                @Override
                public void onPressed() {
                    console.leftKey();
                }
            },
            new IConsole.TextInputControl(Keyboard.KEY_RIGHT, console) {
                @Override
                public void onPressed() {
                    console.rightKey();
                }
            },
            new IConsole.TextInputControl(Keyboard.KEY_UP, console) {
                @Override
                public void onPressed() {
                    console.upKey();
                }
            },
            new IConsole.TextInputControl(Keyboard.KEY_DOWN, console) {
                @Override
                public void onPressed() {
                    console.downKey();
                }
            },
        };*/
        throw new UnsupportedOperationException("");
    }
    
    public static Control[] createTextInputControls(IConsole console) {
        /*return ArrayUtils.concatVariableLengthArrays(new Control[][]{
            createGeneralTextInputControls(console),
            new Control[] {
                new IConsole.CharTextInputControl(Keyboard.KEY_2, '2', '"', console),
                new IConsole.CharTextInputControl(Keyboard.KEY_3, '3', '£', console),
                new IConsole.CharTextInputControl(Keyboard.KEY_GRAVE, '\'', '@', console),
                new IConsole.CharTextInputControl(Keyboard.KEY_APOSTROPHE, '#', '~', console),
            }
        });*/
        throw new UnsupportedOperationException("");
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
    
    private class ImmediateKeyCallback extends GLFWKeyCallback {
        
        @Override
        public void invoke(long window, int key, int scancode, int action, int mods) {
            if(keyControls.containsKey(key)) {
                keyControls.get(key).onAction(action, mods);
            }
        }
        
    }
    
    private class BufferedKeyCallback extends GLFWKeyCallback {
        
        @Override
        public void invoke(long window, int key, int scancode, int action, int mods) {
            if(keyControls.containsKey(key)) {
                controlSet.add(new ControlEvent(key, action, mods));
            }
        }
        
    }
}