package com.quew8.gutils;

import com.quew8.gutils.debug.LogLevel;
import com.quew8.gutils.debug.LogStream;
import com.quew8.gutils.opengl.texture.LoadedImage;
import java.io.File;
import java.io.InputStream;

/**
 *
 * @author Quew8
 */
public class PlatformUtils {
    
    private PlatformUtils() {
        
    }
    
    public static boolean isInitialized() {
        return PlatformBackend.initialized;
    }
    
    public static File getLogFile(String filename) {
        return PlatformBackend.backend.getLogFile_P(filename);
    }
    
    public static LogStream getLogStream(LogLevel level) {
        return PlatformBackend.backend.getLogStream_P(level);
    }
    
    public static LoadedImage loadImage(InputStream is, boolean flip) {
        return PlatformBackend.backend.loadImage_P(is, flip);
    }
    
    public static void fillTexture(LoadedImage img, int destFormat, int texWidth, int texHeight) {
    	PlatformBackend.backend.fillTexture_P(img, destFormat, texWidth, texHeight);
    }
    
    public static void fillSubTexture(int xPos, int yPos, LoadedImage img) {
    	PlatformBackend.backend.fillSubTexture_P(xPos, yPos, img);
    }
    
    public static int getPlatformConstant() {
        return PlatformBackend.backend.getPlatformConstant();
    }
}
