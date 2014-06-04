package com.quew8.gutils.opengl.texture;

import com.quew8.gutils.opengl.GObject;
import static com.quew8.gutils.opengl.OpenGL.*;

/**
 * 
 * @author Quew8
 */
public class TextureObj extends GObject {
    private final int target;
    
    public TextureObj(int target) {
        super(genTextureId());
        this.target = target;
    }
    
    public void bind() {
        glBindTexture(target, getId());
    }
    
    @Override
    public void delete() {
        glDeleteTextures(getIdBuffer());
    }
    
    private static int genTextureId() {
        glGenTextures(idBuff);
        return idBuff.get(0);
    } 
}
