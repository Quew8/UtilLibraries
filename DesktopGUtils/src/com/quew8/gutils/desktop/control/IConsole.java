package com.quew8.gutils.desktop.control;

import org.lwjgl.input.Keyboard;

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
    
    public static class TextInputControl extends Control {
        protected IConsole console;
        
        public TextInputControl(int key, IConsole console) {
            super(key);
            this.console = console;
        }
    }
    
    public static class CharTextInputControl extends TextInputControl {
        private final char letter;
        private final char altLetter;
        
        public CharTextInputControl(int key, char letter, char altLetter, IConsole console) {
            super(key, console);
            this.letter = letter;
            this.altLetter = altLetter;
        }

        @Override
        public void onPressed() {
            console.charKey(
                    ControlSet.checkKeyRegister(Keyboard.KEY_LSHIFT)
                    ? altLetter
                    : letter);
        }
    }
}
