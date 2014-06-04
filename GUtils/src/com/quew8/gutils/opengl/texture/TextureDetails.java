package com.quew8.gutils.opengl.texture;

/**
 *
 */
public class TextureDetails {
    public final TextureObj texture;
    public final int texWidth;
    public final int texHeight;
    public final int usedWidth;
    public final int usedHeight;

    protected TextureDetails(TextureObj texture, int usedWidth, int usedHeight, int texWidth, int texHeight) {
        this.texture = texture;
        this.usedWidth = usedWidth;
        this.usedHeight = usedHeight;
        this.texWidth = texWidth;
        this.texHeight = texHeight;
    }
    
}
