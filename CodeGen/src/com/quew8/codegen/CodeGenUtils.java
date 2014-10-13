package com.quew8.codegen;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 *
 * @author Quew8
 */
public abstract class CodeGenUtils {

    protected CodeGenUtils() {
        
    }
    
    public static String shiftRight(String code) {
        if(code.isEmpty()) {
            return code;
        }
        String[] lines = splitLines(code);
        String newCode = "    " + lines[0];
        for(int i = 1; i < lines.length; i++) {
            newCode += "\n";
            if(!lines[i].isEmpty()) {
                newCode += "    " + lines[i];
            }
        }
        return newCode;
    }
    
    public static String shiftLeft(String code) {
        if(code.isEmpty()) {
            return code;
        }
        String[] lines = splitLines(code);
        String newCode = "    " + lines[0];
        for(int i = 1; i < lines.length; i++) {
            newCode += "\n" + shiftLineLeft(lines[i]);
        }
        return newCode;
    }
    
    public static String shiftLineLeft(String line) {
        if(line.charAt(0) == ' ') {
            if(line.charAt(1) == ' ') {
                if(line.charAt(2) == ' ') {
                    if(line.charAt(3) == ' ') {
                        return line.substring(4);
                    } else {
                        return line.substring(3);
                    }
                } else {
                    return line.substring(2);
                }
            } else {
                return line.substring(1);
            }
        } else {
            return line;
        }
    }
    
    private static String[] splitLines(String s) {
        ArrayList<String> lines = new ArrayList<String>();
        int lastIndex = 0;
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == '\n') {
                lines.add(s.substring(lastIndex, i));
                lastIndex = i+1;
            }
        }
        if(lastIndex != s.length()) {
            lines.add(s.substring(lastIndex));
        }
        return lines.toArray(new String[lines.size()]);
    }
    
    public static <T extends Element<?, T>> T getElement(java.lang.Class<? extends T> clazz, String code) {
        try {
            return (T) clazz.newInstance().setDefinition(code);
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    @SuppressWarnings("unchecked")
    public static <T extends Element<?, T>> T[] getElements(java.lang.Class<T> clazz, String... codes) {
        T[] list = (T[]) Array.newInstance(clazz, codes.length);
        for(int i = 0; i < codes.length; i++) {
            list[i] = getElement(clazz, codes[i]);
        }
        return list;
    }
    
    @SuppressWarnings("unchecked")
    public static <T extends Element<?, T>> T[] combineElements(java.lang.Class<T> clazz, T[]... lists) {
        int size = getSize(lists);
        if(size > 0) {
            T[] compoundList = (T[]) Array.newInstance(clazz, size);
            for(int i = 0, pos = 0; i < lists.length; i++) {
                if(lists[i] != null) {
                    System.arraycopy(lists[i], 0, compoundList, pos, lists[i].length);
                    pos += lists[i].length;
                }
            }
            return compoundList;
        } else {
            return null;
        }
    }
    
    public static int getSize(Element[]... elements) {
        int size = 0;
        for(Element[] es: elements) {
            if(es != null) {
                size += es.length;
            }
        }
        return size;
    }
}
