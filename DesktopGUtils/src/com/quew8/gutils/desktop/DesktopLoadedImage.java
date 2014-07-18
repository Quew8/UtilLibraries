package com.quew8.gutils.desktop;

import com.quew8.gutils.BufferUtils;
import com.quew8.gutils.opengl.texture.LoadedImage;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;

/**
 *
 * @author Quew8
 */
public class DesktopLoadedImage implements LoadedImage {
    private final BufferedImage image;
    private final boolean flip; 
    
    public DesktopLoadedImage(BufferedImage img, boolean flip) {
        this.image = img;
        this.flip = flip;
    }
    
    public DesktopLoadedImage(InputStream in, boolean flip) {
        this(loadImage(in), flip);
    }
    
    @Override
    public int getWidth() {
        return image.getWidth();
    }
    
    @Override
    public int getHeight() {
        return image.getHeight();
    }
    
    @Override
    public boolean hasAlpha() {
        return image.getColorModel().hasAlpha();
    }
    
    @Override
    public void unload() {
        
    }
    
    protected BufferedImage getImage() {
        return image;
    }
    
    protected int getFormat() {
        return GLTexFormat.getFormat(hasAlpha()).getGLEnum();
    }
    
    protected ByteBuffer getData(int texWidth, int texHeight) {
        return convertImageData(this, texWidth, texHeight, flip);
    }
    
    protected ByteBuffer getData() {
        return getData(getWidth(), getHeight());
    }
    
    protected static ByteBuffer convertImageData(DesktopLoadedImage img, 
            int texWidth, int texHeight, boolean flipped) {
        
    	GLTexFormat format = GLTexFormat.getFormat(img.hasAlpha());
        return convertImageData(
                img, 
                texWidth, 
                texHeight, 
                flipped, 
                format.getNComponents(),
                format.getColorModel()
                );
    }
    
    protected static ByteBuffer convertImageData(DesktopLoadedImage img, 
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
        g.drawImage(img.getImage(), 0, 0, null);
        
        byte[] data = ((DataBufferByte) texImage.getRaster().getDataBuffer()).getData();
        return BufferUtils.createByteBuffer(data);
    }
    
    public static BufferedImage loadImage(InputStream imgIn) {
        try {
            return ImageIO.read(imgIn);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
