package com.quew8.gutils.opengl.texture;

import com.quew8.gutils.BufferUtils;
import com.quew8.gutils.Colour;
import com.quew8.gutils.debug.DebugLogger;
import com.quew8.gutils.debug.LogLevel;
import com.quew8.gutils.debug.LogOutput;
import com.quew8.gutils.opengl.GLException;
import com.quew8.gutils.threads.ThreadUtils;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import static com.quew8.gutils.opengl.OpenGL.*;

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
     * 
     * @param texture
     * @param destFormat
     * @param params
     * @param imageLoaders
     * @param cellWidth
     * @param cellHeight
     * @param borderSize
     * @return
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
                int width = imageLoaders.length / 2;
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
     * 
     * @param imageLoaders
     * @param destFormat
     * @param params
     * @param imgWidth
     * @param imgHeight
     * @param borderSize
     * @return
     */
    public static TextureSheetDetails createTextureSheet(ImageLoader[] imageLoaders, int destFormat, 
    		TextureParams params, int imgWidth, int imgHeight, int borderSize) {
    	
        TextureObj texture = new TextureObj(GL_TEXTURE_2D);
        int[] gs = fillTextureSheet(texture, destFormat, params, imageLoaders, imgWidth, imgHeight, borderSize);
        return new TextureSheetDetails(texture, gs[0], gs[1], imgWidth, imgHeight, gs[2], gs[3], borderSize);
    }
    
    /**
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
     * @param refs
     * @return 
     */
    public static String getSpriteSheetName(String[] refs) {
        String n = refs[0];
        for(int i = 1; i < refs.length; i++) {
            n += "||" + refs[i];
        }
        return n;
    } 
    
    /**
     * 
     * @param index
     * @param width
     * @param height
     * @return
     */
    public static int[] getPositionInSheet(int index, int width, int height) {
        int y = index % height;
        int x = (index - y) / height;
        return new int[]{x, y};
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
     * @param texture
     * @param imgIn 
     * @param image 
     * @param imgWidth 
     * @param imgHeight 
     * @param flipped
     * @param resolution
     * @return 
     * @throws IOException 
     */
    /*protected static int[] fillAlphaMaskTexture(TextureObj texture, InputStream imgIn,
            BufferedImage image, int imgWidth, int imgHeight, boolean flipped, 
            int resolution) throws IOException {
        
        int texWidth = get2Fold(imgWidth);
        int texHeight = get2Fold(imgHeight);
        
        ThreadUtils.executeTask(new TextureLoaderTask<InputStream, TextureLoadVariables<InputStream>>() {
            @Override
            public ByteBuffer makeTextureBuffer(TextureLoadVariables<InputStream> input) throws IOException {
                if(input.image == null) {
                    input.image = loadImage(input.resourceIdentifier);
                }
                return convertAlphaMaskImageData(input.image, input.flipped);
            }
            @Override
            public GLTexFormat getSrcFormat(TextureLoadVariables<InputStream> input) throws IOException {
                if(!input.image.getColorModel().hasAlpha()) {
                    throw new IOException("Source image requires an alpha channel");
                }
                return GLTexFormat.DEPTH;
            }
            @Override
            public GLTexFormat getDestFormat(TextureLoadVariables<InputStream> input) {
                return GLTexFormat.DEPTH;
            }
        },
                new TextureLoadVariables<>(
                imgIn, image, texWidth, texHeight, flipped, resolution, texture
                )
                );
        return new int[]{texWidth, texHeight};
    }*/
    
    /**
     * 
     * @param imgIn
     * @param image
     * @param imgWidth
     * @param imgHeight
     * @param flipped
     * @param resolution
     * @return
     * @throws IOException 
     */
    /*public static TextureDetails createAlphaMaskTexture(InputStream imgIn, 
            BufferedImage image, int imgWidth, int imgHeight, boolean flipped, 
            int resolution) throws IOException {
        
        TextureObj texture = new TextureObj(GL_TEXTURE_2D);
        
        int[] texDims = fillAlphaMaskTexture(texture, imgIn, image, imgWidth, imgHeight, flipped, resolution);
        return new TextureDetails(texture, imgWidth, imgHeight, texDims[0], texDims[1]);
    }*/
    
    /**
     * 
     * @param image
     * @param flipped
     * @param resolution
     * @return
     * @throws IOException 
     */
    /*public static TextureDetails createAlphaMaskTexture(BufferedImage image, 
            boolean flipped, int resolution) throws IOException {
        
        return createAlphaMaskTexture(null, image, image.getWidth(), image.getHeight(), flipped, resolution);
    }*/
    
    /**
     * 
     * @param imgIn
     * @param imgWidth
     * @param imgHeight
     * @param flipped
     * @param resolution
     * @return
     * @throws IOException 
     */
    /*public static TextureDetails createAlphaMaskTexture(InputStream imgIn,
            int imgWidth, int imgHeight, boolean flipped, int resolution) throws IOException {
        
        return createAlphaMaskTexture(imgIn, null, imgWidth, imgHeight, flipped, resolution);
    }*/
    
    /**
     * 
     * @param imgIn
     * @param flipped
     * @param resolution
     * @return
     * @throws IOException 
     */
    /*public static TextureDetails createAlphaMaskTexture(InputStream imgIn, 
            boolean flipped, int resolution) throws IOException {
        
        BufferedImage image = loadImage(imgIn);
        return createAlphaMaskTexture(image, flipped, resolution);
    }*/
    
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
        GLException.checkGLError();
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
            GLException.checkGLError();
        }
        
        ByteBuffer bb = BufferUtils.createByteBuffer(width * height * 16);

        GLException.checkGLError();
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
        GLException.checkGLError();
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
        
        GLException.checkGLError();
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
}
