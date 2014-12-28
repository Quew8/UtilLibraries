package com.quew8.gutils.desktop;

import com.quew8.gutils.BufferUtils;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWvidmode;

public class GLFWUtils {

    private GLFWUtils() {
        
    }
    
    public static String displayModesToString(long monitor) {
        String text = "";
        IntBuffer count = BufferUtils.createIntBuffer(1);
        ByteBuffer vidModes = GLFW.glfwGetVideoModes(monitor, count);
        int n = count.get();
        for(int i = 0; i < n; i++) {
            vidModes.position(i * GLFWvidmode.SIZEOF);
            text += (i != 0 ? ", " : "") + "[" + 
                    GLFWvidmode.width(vidModes) + "x" + 
                    GLFWvidmode.height(vidModes) + "x(" +
                    GLFWvidmode.redBits(vidModes) + "+" +
                    GLFWvidmode.greenBits(vidModes) + "+" +
                    GLFWvidmode.blueBits(vidModes) + ")x" +
                    GLFWvidmode.refreshRate(vidModes) + "]\n";
        }
        return text;
    }
    
    public static String displayModesToString() {
        return displayModesToString(GLFW.glfwGetPrimaryMonitor());
    }
    
    public static ByteBuffer getVideoMode(long monitor, int width, int height) {
        IntBuffer count = BufferUtils.createIntBuffer(1);
        ByteBuffer vidModes = GLFW.glfwGetVideoModes(monitor, count);
        ByteBuffer best = null;
        for(int i = 0; i < count.get(); i++) {
            vidModes.position(i * GLFWvidmode.SIZEOF);
            if(GLFWvidmode.width(vidModes) == width &&
                    GLFWvidmode.height(vidModes) == height) {
                if(best == null) {
                    best = vidModes.slice();
                } else {
                    if(
                            GLFWvidmode.redBits(vidModes) > GLFWvidmode.redBits(best) ||
                            GLFWvidmode.greenBits(vidModes) > GLFWvidmode.greenBits(best) ||
                            GLFWvidmode.blueBits(vidModes) > GLFWvidmode.blueBits(best) ||
                            GLFWvidmode.refreshRate(vidModes) > GLFWvidmode.refreshRate(best)
                            ) {
                        best = vidModes.slice();
                    }
                        
                }
            }
        }
        return best;
    }
    
    public static ByteBuffer getVideoMode(int width, int height) {
        return getVideoMode(GLFW.glfwGetPrimaryMonitor(), width, height);
    }
    
    public static ByteBuffer getCurrentVideoMode(long monitor) {
        return GLFW.glfwGetVideoMode(monitor);
    }
    
    public static ByteBuffer getCurrentVideoMode() {
        return getCurrentVideoMode(GLFW.glfwGetPrimaryMonitor());
    }
}
