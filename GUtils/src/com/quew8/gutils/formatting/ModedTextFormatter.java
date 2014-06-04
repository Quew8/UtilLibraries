package com.quew8.gutils.formatting;

import com.quew8.gutils.formatting.TextFormatter;

/**
 *
 * @param <T> 
 * @author Quew8
 */
public class ModedTextFormatter<T extends Enum<T>> extends TextFormatter {
    private final T[] modes;
    private int modeIndex = 0;
    
    public ModedTextFormatter(T[] modes) {
        this.modes = modes;
    }
    
    public ModedTextFormatter(Class<T> clazz) {
        this(clazz.getEnumConstants());
    }
    
    public T getMode() {
        return modes[modeIndex];
    }
    
    public void setMode(int index) {
        modeIndex = index;
    }
}
