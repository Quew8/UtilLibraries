package com.quew8.gutils.desktop;

import static org.lwjgl.opengl.GL11.*;
import java.awt.color.ColorSpace;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;

/**
 * 
 * @author Quew8
 */
class GLTexFormat {
	public static final ColorModel glAlphaColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),
			new int[] {8,8,8,8},
			true,
			false,
			ComponentColorModel.TRANSLUCENT,
			DataBuffer.TYPE_BYTE);
	public static final ColorModel glColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),
			new int[] {8,8,8,0},
			false,
			false,
			ComponentColorModel.OPAQUE,
			DataBuffer.TYPE_BYTE);
	public static final ColorModel glDepthColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_GRAY),
			new int[] {8,0,0,0},
			false,
			false,
			ComponentColorModel.OPAQUE,
			DataBuffer.TYPE_BYTE);
	/*public static final ColorModel glAlphaMaskColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),
			new int[] {0,0,0,8},
			true,
			false,
			ComponentColorModel.TRANSLUCENT,
			DataBuffer.TYPE_BYTE);*/
	
	public static final GLTexFormat 
		RGBA = new GLTexFormat(GL_RGBA, 4, glAlphaColorModel), 
		RGB = new GLTexFormat(GL_RGB, 3, glColorModel),
		DEPTH = new GLTexFormat(GL_DEPTH_COMPONENT, 1, glDepthColorModel);

    private final int glEnum;
    private final int nComponents;
    private final ColorModel colorModel;
    
    private GLTexFormat(int glEnum, int nComponents, ColorModel colorModel) {
        this.glEnum = glEnum;
        this.nComponents = nComponents;
        this.colorModel = colorModel;
    }

    protected int getGLEnum() {
        return glEnum;
    }

    protected int getNComponents() {
    	return nComponents;
    }
    
    protected ColorModel getColorModel() {
    	return colorModel;
    }
    
    public static GLTexFormat getFormat(boolean hasAlpha) {
        return hasAlpha ? RGBA : RGB;
    }
}