package com.quew8.gutils.opengl;

import java.nio.ByteBuffer;
import static com.quew8.gutils.opengl.OpenGL.*;

/**
 * 
 * @author Quew8
 */
public class GBuffer extends GObject {
    private final int target;
    
    public GBuffer(ByteBuffer data, int target, int usage) {
        super(genId());
        this.target = target;
        glBindBuffer(target, getId());
        glBufferData(target, data, usage);
        glBindBuffer(target, 0);
    }
    
    public void bind() {
        glBindBuffer(target, getId());
    }
    
    public void unbind() {
        glBindBuffer(target, 0);
    }
    
    @Override
    public void delete() {
        glDeleteBuffers(getIdBuffer());
    }
    
    public void setData(ByteBuffer data, int usage) {
        glBufferData(target, data, usage);
    }
    
    public void updateData(int offset, ByteBuffer data) {
        glBufferSubData(target, offset, data);
    }
    
    public int getTarget() {
        return target;
    }
    
    public static void unbind(int target) {
        glBindBuffer(target, 0);
    }
    
    private static int genId() {
        glGenBuffers(idBuff);
        return idBuff.get(0);
    }
}
