package com.quew8.geng.rendering;

import com.quew8.geng.geometry.Texture;
import java.util.Arrays;

/**
 *
 * @author Quew8
 * @param <T>
 * @param <S>
 */
public class ObjGroup<T, S> {
    private final T[] handles;
    private final S renderMode;
    private final Texture texture;
    private final int offset;
    
    public ObjGroup(T[] handles, S renderMode, Texture texture, int offset) {
        this.handles = handles;
        this.renderMode = renderMode;
        this.texture = texture;
        this.offset = offset;
    }
    
    public T getHandle(int i) {
        return handles[i];
    }
    
    public int getNHandles() {
        return handles.length;
    }
    
    public S getRenderMode() {
        return renderMode;
    }

    public Texture getTexture() {
        return texture;
    }

    public int getOffset() {
        return offset;
    }

    @Override
    public String toString() {
        return "ObjGroup{" + "handles=" + Arrays.toString(handles) + ", renderMode=" + renderMode + ", texture=" + texture + ", offset=" + offset + '}';
    }
    
}
