package com.quew8.gutils.opengl;

import com.quew8.gutils.BufferUtils;
import java.nio.IntBuffer;

/**
 * 
 * @author Quew8
 */
public abstract class GObject {
    public static final IntBuffer idBuff = BufferUtils.createIntBuffer(1);
    
    private final int id;
    
    public GObject(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public IntBuffer getIdBuffer() {
        idBuff.put(getId());
        idBuff.flip();
        return idBuff;
    }
    
    public abstract void delete();
}
