package com.quew8.gutils.debug;

/**
 *
 * @author Quew8
 */
public interface DebugInterface {
    String debugGetName();
    String debugGetValue(String param) throws DebugException;
    void debugSetValue(String param, String... value) throws DebugException;
    DebugInterface debugGetObj(String obj) throws DebugException;
    void debugOnChangeObj(String obj) throws DebugException;
    String[] debugGetParams();
    String[] debugGetObjects();
    
    public static void debugEnsureLength(String[] values, int length) throws DebugException {
        if(values.length != length) {
            throw new DebugException("Requires " + length + " parameters, found " + values.length);
        }
    }
    
    public static boolean debugGetBoolean(String value) throws DebugException {
        if(value.equalsIgnoreCase("true") || value.equalsIgnoreCase("t")) {
            return true;
        } else if(value.equalsIgnoreCase("false") || value.equalsIgnoreCase("f")) {
            return false;
        } else {
            throw new DebugException("Couldn't parse \"" + value + "\" as boolean");
        }
    }
    
    public static float debugGetFloat(String value) throws DebugException {
        try {
            return Float.parseFloat(value);
        } catch(NumberFormatException ex) {
            throw new DebugException("Couldn't parse \"" + value + "\" as float");
        }
    }
    
    public static int debugGetInt(String value) throws DebugException {
        try {
            return Integer.parseInt(value);
        } catch(NumberFormatException ex) {
            throw new DebugException("Couldn't parse \"" + value + "\" as integer");
        }
    }
}
