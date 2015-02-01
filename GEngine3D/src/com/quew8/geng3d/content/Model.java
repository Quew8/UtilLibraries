package com.quew8.geng3d.content;

import com.quew8.geng3d.geometry.Mesh3D;
import com.quew8.gutils.opengl.texture.LoadedImage;

/**
 *
 * @author Quew8
 */
public class Model {
    private final Mesh3D mesh;
    private final LoadedImage img;

    public Model(Mesh3D mesh, LoadedImage img) {
        this.mesh = mesh;
        this.img = img;
    }

    public Mesh3D getMesh() {
        return mesh;
    }

    public LoadedImage getImage() {
        return img;
    }
    
}
