package com.quew8.codegen;

import java.lang.reflect.Array;

/**
 *
 * @author Quew8
 */
public abstract class CodeGenUtils {

    protected CodeGenUtils() {
        
    }
    
    public static String getList(String[] strings, String separator) {
        if(strings.length > 0) {
            String list = strings[0];
            for(int i = 1; i < strings.length; i++) {
                list += separator + strings[i];
            }
            return list;
        } else {
            return "";
        }
    }
    
    public static String getList(Element[] elements, String separator) {
        String[] strings = new String[elements.length];
        for(int i = 0; i < strings.length; i++) {
            strings[i] = elements[i].getCode();
        }
        return getList(strings, separator);
    }
    
    public static String getCommaSeperatedList(Element[] elements) {
        return getList(elements, ", ");
    }
    
    public static String getCommaSeperatedList(String[] strings) {
        return getList(strings, ", ");
    }
    
    public static String getNewlineList(Element[] elements) {
        return getList(elements, "\n");
    }
    
    public static String getNewlineList(String[] strings) {
        return getList(strings, "\n");
    }
    
    public static String getLineSeperatedList(Element[] elements) {
        return getList(elements, "\n\n");
    }
    
    public static String getLineSeperatedList(String[] strings) {
        return getList(strings, "\n\n");
    }
    
    public static String[] splitCommaSeparatedList(String list) {
        return list.split("[\\s]*,[\\s]*");
    }
    
    public static String shiftRight(String code) {
        String[] lines = code.split("\n");
        String newCode = "    " + lines[0];
        for(int i = 1; i < lines.length; i++) {
            newCode += "\n    " + lines[i];
        }
        return newCode;
    }
    
    public static String shiftLeft(String code) {
        String[] lines = code.split("\n");
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
    
    public static <T extends Element> T getElement(java.lang.Class<T> clazz, String code) {
        try {
            T t = clazz.newInstance();
            t.setOverrideCode(code);
            return t;
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    @SuppressWarnings("unchecked")
    public static <T extends Element> T[] getElements(java.lang.Class<T> clazz, String... codes) {
        T[] list = (T[]) Array.newInstance(clazz, codes.length);
        for(int i = 0; i < codes.length; i++) {
            list[i] = getElement(clazz, codes[i]);
        }
        return list;
    }
    
    @SuppressWarnings("unchecked")
    public static <T extends Element> T[] combineElements(java.lang.Class<T> clazz, T[]... lists) {
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
    
    public static Construction getConstruction() {
        return new Construction();
    }
    
    public static class Construction {
        private String construction = "";
        
        public Construction addNoGap(Element... es) {
            return add("", es);
        }
        
        public Construction addNoGap(String... ss) {
            return add("", ss);
        }
        
        public Construction addNoGap(boolean b, Element... es) {
            return b ? addNoGap(es) : this;
        }
        
        public Construction addNoGap(boolean b, String... ss) {
            return b ? addNoGap(ss) : this;
        }
        
        public Construction add(Element... es) {
            return add(" ", es);
        }
        
        public Construction add(String... ss) {
            return add(" ", ss);
        }
        
        public Construction add(boolean b, Element... es) {
            return b ? add(es) : this;
        }
        
        public Construction add(boolean b, String... ss) {
            return b ? add(ss) : this;
        }
        
        public Construction addNewline(Element... es) {
            return add("\n", es);
        }
        
        public Construction addNewline(String... ss) {
            return add("\n", ss);
        }
        
        public Construction addNewline(boolean b, Element... es) {
            return b ? addNewline(es) : this;
        }
        
        public Construction addNewline(boolean b, String... ss) {
            return b ? addNewline(ss) : this;
        }
        
        public Construction addLineSeparated(Element... es) {
            return add("\n\n", es);
        }
        
        public Construction addLineSeparated(String... ss) {
            return add("\n\n", ss);
        }
        
        public Construction addLineSeparated(boolean b, Element... es) {
            return b ? addLineSeparated(es) : this;
        }
        
        public Construction addLineSeparated(boolean b, String... ss) {
            return b ? addLineSeparated(ss) : this;
        }
        
        public Construction add(String separator, Element[] es) {
            for(Element e: es) {
                addWithSeparator(e.getCode(), separator);
            }
            return this;
        }
        
        public Construction add(String separator, String[] ss) {
            for(String s: ss) {
                addWithSeparator(s, separator);
            }
            return this;
        }
        
        public Construction addWithSeparator(String s, String separator) {
            if(!s.isEmpty()) {
                construction += construction.isEmpty() ? s : separator + s;
            }
            return this;
        }
        
        public String get() {
            return construction;
        }
    }
}
