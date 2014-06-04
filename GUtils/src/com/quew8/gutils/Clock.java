package com.quew8.gutils;

/**
 * 
 * @author Quew8
 */
public class Clock {
    private static long lastFrame;
    private static int delta;
    
    private Clock() {
        
    }
    
    public static void begin() {
        lastFrame = getTime();
    }
    
    public static void makeDelta() {
        long time = getTime();
        delta = (int) (time - lastFrame);
        lastFrame = time;
    }
    
    public static int getDelta() {
        return delta;
    }
    
    public static long getTime() {
    	return PlatformBackend.backend.getTimeMillis_P();
    }
}
