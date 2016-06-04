package com.quew8.gutils.opengl.texture;

import static com.quew8.gutils.opengl.OpenGL.*;
import java.util.HashMap;

public class TextureParams {
    private final HashMap<Integer, Integer> params = new HashMap<>();
    private boolean generateMipmaps;

    public TextureParams() {
        params.put(GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        params.put(GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        this.generateMipmaps = false;
    }
    
    public TextureParams setParam(int pname, int param) {
        this.params.put(pname, param);
        return this;
    }
    
    public TextureParams setGenerateMipmaps(boolean generateMipmaps) {
        this.generateMipmaps = generateMipmaps;
        return this;
    }
    
    public void setAllParams(int target) {
        params.keySet().stream().forEach((param) -> {
            glTexParameteri(target, param, params.get(param));
        });
        if(generateMipmaps) {
            glGenerateMipmap(target);
        }
    }
}
