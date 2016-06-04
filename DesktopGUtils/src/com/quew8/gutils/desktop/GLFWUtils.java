package com.quew8.gutils.desktop;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

public class GLFWUtils {

    private GLFWUtils() {
        
    }
    
    public static String toString(GLFWVidMode vidMode) {
        return "VidMode{" + 
                    vidMode.width() + "x" + 
                    vidMode.height() + "x(" +
                    vidMode.redBits() + "+" +
                    vidMode.greenBits() + "+" +
                    vidMode.blueBits() + ")x" +
                    vidMode.refreshRate() + "}";
    }
    
    public static String displayModesToString(long monitor) {
        String text = "";
        GLFWVidMode.Buffer vidModes = GLFW.glfwGetVideoModes(monitor);
        text += toString(vidModes.get()) + "\n";
        while(vidModes.remaining() > 0) {
            text += ", " + toString(vidModes.get()) + "\n";
        }
        return text;
    }
    
    public static String displayModesToString() {
        return displayModesToString(GLFW.glfwGetPrimaryMonitor());
    }
    
    public static GLFWVidMode getVideoMode(long monitor, int width, int height) {
        GLFWVidMode.Buffer vidModes = GLFW.glfwGetVideoModes(monitor);
        GLFWVidMode best = null;
        while(vidModes.remaining() > 0) {
            GLFWVidMode vidMode = vidModes.get();
            if(vidMode.width() == width &&
                    vidMode.height() == height) {
                if(best == null) {
                    best = vidMode;
                } else {
                    if(
                            vidMode.redBits() > best.redBits() ||
                            vidMode.greenBits() > best.greenBits() ||
                            vidMode.blueBits() > best.blueBits() ||
                            vidMode.refreshRate() > best.refreshRate()
                            ) {
                        best = vidMode;
                    }
                        
                }
            }
        }
        return best;
    }
    
    public static GLFWVidMode getVideoMode(int width, int height) {
        return getVideoMode(GLFW.glfwGetPrimaryMonitor(), width, height);
    }
    
    public static GLFWVidMode getCurrentVideoMode(long monitor) {
        return GLFW.glfwGetVideoMode(monitor);
    }
    
    public static GLFWVidMode getCurrentVideoMode() {
        return getCurrentVideoMode(GLFW.glfwGetPrimaryMonitor());
    }
}
