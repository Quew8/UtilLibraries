package com.quew8.gutils.desktop.control;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCharCallback;

/**
 *
 * @author Quew8
 */
public interface IConsole {
    
    public void charKey(char c);
    
    public void backspaceKey();
    
    public void deleteKey();
    
    public void returnKey();
    
    public void leftKey();
    
    public void rightKey();
    
    public void upKey();
    
    public void downKey();
    
    /**
     * 
     */
    public static class TextInputControl extends Control {
        protected IConsole console;
        
        public TextInputControl(int key, IConsole console) {
            super(key);
            this.console = console;
        }
    }
    
    /**
     * 
     */
    public static class CharTextInputControl extends TextInputControl {
        private final char letter;
        private final char altLetter;
        
        public CharTextInputControl(int key, char letter, char altLetter, IConsole console) {
            super(key, console);
            this.letter = letter;
            this.altLetter = altLetter;
        }

        @Override
        public void onPressed(WindowInputHandler handler, int mods) {
            console.charKey(
                    (mods & GLFW.GLFW_MOD_SHIFT) != 0
                    ? altLetter
                    : letter);
        }

        @Override
        public void onRepeat(WindowInputHandler handler, int mods) {
            console.charKey(
                    (mods & GLFW.GLFW_MOD_SHIFT) != 0
                    ? altLetter
                    : letter);
        }
    }
    
    public static class ConsoleCharCallback extends GLFWCharCallback {

        @Override
        public void invoke(long window, int codepoint) {
            System.out.println(codepoint + " " + ((char)codepoint) + " " + Character.getName(codepoint));
        }
        
    }
}
