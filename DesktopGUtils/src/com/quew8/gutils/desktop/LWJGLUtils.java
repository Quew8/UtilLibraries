package com.quew8.gutils.desktop;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/**
 *
 * @author Quew8
 */
public class LWJGLUtils {

    private LWJGLUtils() {
        
    }
    
    public static String displayModesToString() {
        try {
            String text = "";
            DisplayMode[] available = Display.getAvailableDisplayModes();
            for (DisplayMode dp : available) {
                text += dp.toString() + (dp.isFullscreenCapable() ? " fullscreen\n" : " no fullscreen\n");
            }
            return text;
        } catch (LWJGLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static DisplayMode getDisplayMode(int width, int height) throws LWJGLException {
        DisplayMode dp = getDisplayMode(width, height, true);
        if(dp == null) {
            return getDisplayMode(width, height, false);
        }
        return dp;
    }
    
    public static DisplayMode getDisplayMode(int width, int height, boolean fullscreen) throws LWJGLException {
        DisplayMode ddp = Display.getDesktopDisplayMode();
        DisplayMode cdp = null;
        DisplayMode[] available = Display.getAvailableDisplayModes();
        for(DisplayMode dp: available) {
            if(dp.getWidth() == width && dp.getHeight() == height) {
                if(dp.isFullscreenCapable() || !fullscreen) {
                    cdp = dp;
                    if(cdp.getBitsPerPixel() == ddp.getBitsPerPixel() && cdp.getFrequency() == ddp.getFrequency()) {
                        return cdp;
                    }
                }
            }
        }
        return cdp;
    }
    
    public static DisplayMode getDesktopDisplayMode() {
        return Display.getDesktopDisplayMode();
    }
}
