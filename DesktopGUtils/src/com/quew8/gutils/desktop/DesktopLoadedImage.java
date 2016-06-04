package com.quew8.gutils.desktop;

import com.quew8.gutils.BufferUtils;
import com.quew8.gutils.opengl.texture.LoadedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.stb.STBImage;

/**
 *
 * @author Quew8
 */
public class DesktopLoadedImage implements LoadedImage {
    private final ByteBuffer loadedImage;
    private ByteBuffer imgData = null;
    private final int width;
    private final int height;
    private final int components;
    //private final BufferedImage image;
    private final boolean flip; 
    
    public DesktopLoadedImage(ByteBuffer loadedImage, boolean flip) {
        this.loadedImage = loadedImage;
        this.flip = flip;
        IntBuffer widthBuf = BufferUtils.createIntBuffer(1);
        IntBuffer heightBuf = BufferUtils.createIntBuffer(1);
        IntBuffer compBuf = BufferUtils.createIntBuffer(1);
        STBImage.stbi_info_from_memory(loadedImage, widthBuf, heightBuf, compBuf);
        this.width = widthBuf.get(0);
        this.height = heightBuf.get(0);
        this.components = compBuf.get(0);
    }
    
    public DesktopLoadedImage(InputStream in, boolean flip) {
        this(loadInputStream(in), flip);
    }
    
    @Override
    public int getWidth() {
        return width;
    }
    
    @Override
    public int getHeight() {
        return height;
    }
    
    @Override
    public boolean hasAlpha() {
        return components == 4;
    }
    
    @Override
    public void unload() {
        if(imgData != null) {
            STBImage.stbi_image_free(imgData);
            imgData = null;
        }
    }
    
    protected ByteBuffer getImageData(int texWidth, int texHeight) {
        if(texWidth != width || texHeight != height) {
            throw new UnsupportedOperationException();
        }
        return getImageData();
    }
    
    protected ByteBuffer getImageData() {
        if(imgData == null) {
            STBImage.stbi_set_flip_vertically_on_load(flip ? 1 : 0);
            IntBuffer widthBuf = BufferUtils.createIntBuffer(1);
            IntBuffer heightBuf = BufferUtils.createIntBuffer(1);
            IntBuffer compBuf = BufferUtils.createIntBuffer(1);
            imgData = STBImage.stbi_load_from_memory(loadedImage, widthBuf, heightBuf, compBuf, components);
            if(imgData == null) {
                throw new RuntimeException("Image Loading Failed: " + STBImage.stbi_failure_reason());
            }
        }
        return imgData;
    }
    
    protected int getFormat() {
        return GLTexFormat.getFormat(components).getGLEnum();
    }
    
    private static ByteBuffer loadInputStream(InputStream in) {
        ByteBuffer data;
        try(InputStream is = in) {
            data = BufferUtils.createByteBuffer(is.available());
            byte[] buf = new byte[1028];
            int n;
            while((n = is.read(buf)) != -1) {
                if(data.remaining() < n) {
                    ByteBuffer newdata = data = org.lwjgl.BufferUtils.createByteBuffer(data.position() + n + is.available());
                    newdata.put(data);
                    data = newdata;
                }
                data.put(buf, 0, n);
            }
            data.flip();
        } catch(IOException ex) {
            throw new RuntimeException(ex);
        }
        return data;
    }
}
