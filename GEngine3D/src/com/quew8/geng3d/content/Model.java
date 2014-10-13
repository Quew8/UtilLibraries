package com.quew8.geng3d.content;

import com.quew8.geng.geometry.Mesh;
import com.quew8.gutils.opengl.texture.LoadedImage;

/**
 *
 * @author Quew8
 */
public class Model {
    private final Mesh mesh;
    private final LoadedImage img;

    public Model(Mesh mesh, LoadedImage img) {
        this.mesh = mesh;
        this.img = img;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public LoadedImage getImage() {
        return img;
    }
    
}
