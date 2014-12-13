package com.quew8.gutils.opengl.texture;

import com.quew8.gutils.BufferUtils;
import com.quew8.gutils.Colour;
import com.quew8.gutils.PlatformUtils;
import com.quew8.gutils.debug.DebugLogger;
import com.quew8.gutils.debug.LogLevel;
import com.quew8.gutils.debug.LogOutput;
import static com.quew8.gutils.opengl.OpenGL.*;
import com.quew8.gutils.threads.ThreadUtils;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.HashMap;

/**
 * 
 * @author Quew8
 *
 */
public class TextureUtils {
    public static final int COLOUR_TEXTURE_LENGTH = 16;
    public static final int COLOUR_TEXTURE_AREA = COLOUR_TEXTURE_LENGTH * COLOUR_TEXTURE_LENGTH;
    public static final int COLOUR_TEXTURE_SIZE = COLOUR_TEXTURE_AREA * 4;
    public static final String TEXTURE_UTILS_LOG = "TEXTURE_UTILS";
    static {
        DebugLogger.registerLog(TEXTURE_UTILS_LOG, LogLevel.VERBOSE, LogOutput.FILE);
    }
    
    private TextureUtils() {
        
    }
    
    /**
     * Fills a texture as a texture sheet with an array of images, calculating
     * optimal number of columns and rows for a minimum texture size. Images are
     * loaded in column-major order.
     * 
     * @param texture
     * @param destFormat
     * @param params
     * @param imageLoaders
     * @param cellWidth
     * @param cellHeight
     * @param borderSize
     * @return An integer array containing {nColumns, nRows, texWidth, texHeight}
     */
    protected static int[] fillTextureSheet(TextureObj texture, int destFormat, TextureParams params, ImageLoader[] imageLoaders, 
    		int cellWidth, int cellHeight, int borderSize) {
    	
    	int sx = cellWidth + borderSize;
    	int sy = cellHeight + borderSize;
        int nColumns = imageLoaders.length;
        int nRows = 1;
        int bestTexWidth = get2Fold(sx*nColumns);
        int bestTexHeight = get2Fold(sy);
        for(int i = 2; i <= imageLoaders.length; i++) {
            if(imageLoaders.length % i == 0) {
                int width = imageLoaders.length / i;
                int tempTexWidth = get2Fold(width * sx);
                int tempTexHeight = get2Fold(i * sy);
                if(tempTexWidth*tempTexHeight < bestTexWidth*bestTexHeight 
                        || (tempTexWidth*tempTexHeight == bestTexWidth*bestTexHeight && width + i < nColumns+nRows)) {
                    nColumns = width;
                    nRows = i;
                    bestTexWidth = tempTexWidth;
                    bestTexHeight = tempTexHeight;
                }
            }
        }
        ThreadUtils.executeTask(new TextureSheetLoaderTask(cellWidth, cellHeight, borderSize, nColumns, nRows), 
                new TextureLoadVariables(imageLoaders, bestTexWidth, bestTexHeight, texture, destFormat, params));
        return new int[]{nColumns, nRows, bestTexWidth, bestTexHeight};
    }
    
    /**
     * Creates a texture sheet from imageLoaders, calculating optimal number of 
     * columns and rows for a minimum texture size. Images are loaded in 
     * column-major order.
     * 
     * @param imageLoaders
     * @param destFormat
     * @param params
     * @param imgWidth
     * @param imgHeight
     * @param borderSize
     * @return
     */
    public static TextureSheetDetails createTextureSheet(ImageLoader[] imageLoaders, 
            int destFormat, TextureParams params, int imgWidth, int imgHeight, 
            int borderSize) {
    	
        TextureObj texture = new TextureObj(GL_TEXTURE_2D);
        int[] gs = fillTextureSheet(texture, destFormat, params, imageLoaders, imgWidth, imgHeight, borderSize);
        return new TextureSheetDetails(texture, gs[0], gs[1], imgWidth, imgHeight, gs[2], gs[3], borderSize);
    }
    
    /**
     * Creates a texture sheet from imageLoaders, calculating optimal number of 
     * columns and rows for a minimum texture size. Images are loaded in 
     * column-major order.
     * 
     * @param imageLoaders
     * @param destFormat
     * @param params
     * @param borderSize
     * @return
     */
    public static TextureSheetDetails createTextureSheet(ImageLoader[] imageLoaders, int destFormat, TextureParams params, 
    		int borderSize) {
        
    	LoadedImage image0 = imageLoaders[0].getLoadedImage();
    	imageLoaders[0] = new PreLoadedImageLoader(image0);
        return createTextureSheet(imageLoaders, destFormat, params, image0.getWidth(), image0.getHeight(), borderSize);
    }
    
    /**
     * Creates a texture sheet from imageLoaders, calculating optimal number of 
     * columns and rows for a minimum texture size. Images are loaded in 
     * column-major order.
     * 
     * @param details
     * @param imgWidth
     * @param imgHeight
     * @param gridWidth
     * @param gridHeight
     * @param borderSize
     * @return
     */
    public static TextureSheetDetails createTextureSheet(TextureDetails details, 
            int imgWidth, int imgHeight, int gridWidth, int gridHeight, int borderSize) {
        
        TextureSheetDetails sheetDetails = new TextureSheetDetails(details, imgWidth, imgHeight, gridWidth, gridHeight, borderSize);
        return sheetDetails;
    }
    
    /**
     * 
     * @param texture
     * @param destFormat
     * @param params
     * @param imgLoader
     * @param imgWidth
     * @param imgHeight
     * @return
     */
    protected static int[] fillTexture(TextureObj texture, int destFormat, TextureParams params, ImageLoader imgLoader, 
    		int imgWidth, int imgHeight) {
    	
        int texWidth = get2Fold(imgWidth);
        int texHeight = get2Fold(imgHeight);
        ThreadUtils.executeTask(
        		new TextureLoaderTask(),
                new TextureLoadVariables(new ImageLoader[]{imgLoader}, texWidth, texHeight, texture, destFormat, params)
                );
        return new int[]{texWidth, texHeight};
    }
    
    /**
     * 
     * @param imgLoader
     * @param destFormat
     * @param params
     * @param imgWidth
     * @param imgHeight
     * @return
     */
    public static TextureDetails createTexture(ImageLoader imgLoader, int destFormat, TextureParams params, 
    		int imgWidth, int imgHeight) {
        
        TextureObj texture = new TextureObj(GL_TEXTURE_2D);
        int[] texDims = fillTexture(texture, destFormat, params, imgLoader, imgWidth, imgHeight);
        return new TextureDetails(texture, imgWidth, imgHeight, texDims[0], texDims[1]);
    }
    
    /**
     * 
     * @param imgLoader
     * @param destFormat
     * @param params
     * @return
     */
    public static TextureDetails createTexture(ImageLoader imgLoader, int destFormat, TextureParams params) {
    	LoadedImage image = imgLoader.getLoadedImage();
        return createTexture(new PreLoadedImageLoader(image), destFormat, params, image.getWidth(), image.getHeight());
    }
    
    /**
     * 
     * @param imgLoader
     * @param params
     * @param imgWidth
     * @param imgHeight
     * @return 
     */
    public static TextureDetails createAlphaMaskTexture(ImageLoader imgLoader, TextureParams params, 
    		int imgWidth, int imgHeight) {
        
        return createTexture(imgLoader, GL_RED, params, imgWidth, imgHeight);
    }
    
    /**
     * 
     * @param imgLoader
     * @param params
     * @return 
     */
    public static TextureDetails createAlphaMaskTexture(ImageLoader imgLoader, TextureParams params) {
        return createTexture(imgLoader, GL_RED, params);
    }
    
    /**
     * 
     * @param texture
     * @param c
     */
    protected static void fillColourTexture(TextureObj texture, Colour c) {
    	ByteBuffer bb = BufferUtils.createByteBuffer(COLOUR_TEXTURE_SIZE);
        Colour.ByteColour bcolour = c.new ByteColour();
        for(int h = 0; h < COLOUR_TEXTURE_LENGTH; h++) {
            for(int w = 0; w < COLOUR_TEXTURE_LENGTH; w++) {
            	bb.put(bcolour.getRed());
            	bb.put(bcolour.getGreen());
            	bb.put(bcolour.getBlue());
            	bb.put(bcolour.getAlpha());
            }
        }
        bb.flip();
        
        texture.bind();
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, COLOUR_TEXTURE_LENGTH, COLOUR_TEXTURE_LENGTH, 0, GL_RGBA, GL_UNSIGNED_BYTE, bb);
    }
    
    /**
     * 
     * @param c
     * @return
     */
    public static TextureDetails createColourTexture(Colour c) {
        TextureObj tex = new TextureObj(GL_TEXTURE_2D);
        
        fillColourTexture(tex, c);
        
        return new TextureDetails(tex, 
                COLOUR_TEXTURE_LENGTH, COLOUR_TEXTURE_LENGTH, 
                COLOUR_TEXTURE_LENGTH, COLOUR_TEXTURE_LENGTH
                );
    }
    
    public static TextureDetails createFontTexture(Font font, int imgWidth, int imgHeight, HashMap<Character, float[]> mapping, TextureParams params) {
        BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setBackground(new Color(255, 255, 255, 0));
        g.clearRect(0, 0, imgWidth, imgHeight);  
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        FontMetrics metrics = g.getFontMetrics();
        
        int texWidth = get2Fold(imgWidth);
        int texHeight = get2Fold(imgHeight);
        
        int rowHeight = 0;
        int positionX = 0;
        int positionY = 1 - (imgWidth / texWidth);
        int charHeight = metrics.getHeight();
        
        for (int index = 0; index < 256; index++) {
            char letter = (char) index;
            int charWidth = metrics.charWidth(letter);
            if(positionX + charWidth > imgWidth) {
                if(positionY + charHeight > imgHeight) {
                    throw new IllegalArgumentException("Texture Size too small to accomadate characters");
                }
                positionX = 0;
                positionY += rowHeight;
                rowHeight = 0;
            }
            if(charHeight > rowHeight) {
                rowHeight = charHeight;
            }
            mapping.put(letter, new float[]{
                (float) positionX / texWidth, 
                (float) positionY / texHeight, 
                (float) ( positionX + charWidth ) / texWidth, 
                (float) ( positionY + charHeight ) / texHeight
            });
            System.out.println(letter + " :: " + mapping.get(letter)[0] + " " + mapping.get(letter)[1] + " " + mapping.get(letter)[2] + " " + mapping.get(letter)[3]);
            g.drawString(String.valueOf(letter), positionX, positionY);
            positionX += charWidth;
        }
        
        ByteBuffer data = convertImageData(img, texWidth, texHeight, true, 4, glAlphaColourModel);
        TextureObj tex = new TextureObj(GL_TEXTURE_2D);
        tex.bind();
        params.setAll(GL_TEXTURE_2D);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RED, texWidth, texHeight, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
        params.run();
        
        return new TextureDetails(tex, imgWidth, imgHeight, texWidth, texHeight);
    }
    private static final ColorModel glAlphaColourModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),
			new int[] {8,8,8,8},
			true,
			false,
			ComponentColorModel.TRANSLUCENT,
			DataBuffer.TYPE_BYTE);
    
    private static ByteBuffer convertImageData(BufferedImage img, 
            int texWidth, int texHeight, boolean flip, 
            int rasterBands, ColorModel colorModel) {
        
        WritableRaster raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, texWidth, texHeight, rasterBands, null);
        BufferedImage texImage = new BufferedImage(colorModel, raster, false, null);

        Graphics2D g = texImage.createGraphics();
        if(flip) {
            g.translate(texWidth/2, texHeight/2);
            g.scale(1, -1);
            g.translate(-texWidth/2, -texHeight/2);
        }
        g.setColor(new java.awt.Color(0f, 0f, 0f, 0f));
        g.fillRect(0, 0, texWidth, texHeight);
        g.drawImage(img, 0, 0, null);
        
        byte[] data = ((DataBufferByte) texImage.getRaster().getDataBuffer()).getData();
        return BufferUtils.createByteBuffer(data);
    }
    
    /**
     * 
     * @param texture
     * @param width
     * @param height
     * @param destFormat
     * @param texParams
     */
    protected static void fillEmptyTexture(TextureObj texture, int width, int height, int destFormat, TextureParams texParams) {
        texture.bind();
        for(int i = 0; i < texParams.getNParams(); i++) {
            glTexParameteri(GL_TEXTURE_2D, texParams.getPName(i), texParams.getParam(i));
        }
        
        ByteBuffer bb = BufferUtils.createByteBuffer(width * height * 16);

        glTexImage2D(
                GL_TEXTURE_2D, 
                0, 
                destFormat,
                width, 
                height, 
                0,  
                GL_RGBA,
                GL_UNSIGNED_BYTE,
                bb);
        DebugLogger.log(TEXTURE_UTILS_LOG, "DestFormat: " + destFormat + " : " + com.quew8.gutils.opengl.OpenGLUtils.toOpenGLString(destFormat));
    }
    
    /**
     * 
     * @param width
     * @param height
     * @param destFormat
     * @param texParams
     * @return
     */
    public static TextureDetails createEmptyTexture(int width, int height, int destFormat, TextureParams texParams) {
        TextureObj tex = new TextureObj(GL_TEXTURE_2D);
        int texWidth = get2Fold(width);
        int texHeight = get2Fold(height);
        fillEmptyTexture(tex, texWidth, texHeight, destFormat, texParams);
        return new TextureDetails(tex, width, height, texWidth, texHeight);
    }
    
    /**
     * 
     * @param width
     * @param height
     * @return
     */
    public static TextureObj createEmptyDepthTexture(int width, int height) {
        TextureObj tex = new TextureObj(GL_TEXTURE_2D);
        tex.bind();
        
        int texWidth = get2Fold(width);
        int texHeight = get2Fold(height);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        
        FloatBuffer fb = BufferUtils.createFloatBuffer(texWidth * texHeight);
        
        glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, texWidth, texHeight, 0,
                      GL_DEPTH_COMPONENT,
                      GL_FLOAT,
                      fb);
        return tex;
    }
    
    /**
     * 
     * @param i
     * @return 
     */
    public static int get2Fold(int i) {
        int k = 2;
        while (k < i) {
            k *= 2;
        }
        return k;
    }
    
    /**
     * 
     * @param is
     * @param flip
     * @return
     */
    public static ImageLoader getImageLoader(InputStream is, boolean flip) {
    	return new StreamImageLoader(is, flip);
    }
    
    /**
     * 
     * @param iss
     * @param flip
     * @return
     */
    public static ImageLoader[] getImageLoaders(InputStream[] iss, boolean flip) {
    	ImageLoader[] loaders = new ImageLoader[iss.length];
    	for(int i = 0; i < loaders.length; i++) {
            loaders[i] = getImageLoader(iss[i], flip);
    	}
    	return loaders;
    }
    
    /**
     * 
     * @param img
     * @return 
     */
    public static ImageLoader getImageLoader(LoadedImage img) {
    	return new PreLoadedImageLoader(img);
    }
    
    /**
     * 
     * @param imgs
     * @return
     */
    public static ImageLoader[] getImageLoaders(LoadedImage[] imgs) {
    	ImageLoader[] loaders = new ImageLoader[imgs.length];
    	for(int i = 0; i < loaders.length; i++) {
    		loaders[i] = getImageLoader(imgs[i]);
    	}
    	return loaders;
    }
    
    /**
     * 
     * @param in
     * @param flip
     * @return 
     */
    public static LoadedImage loadImage(InputStream in, boolean flip) {
        return PlatformUtils.loadImage(in, flip);
    }
}
