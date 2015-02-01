package com.quew8.geng3d.rendering;

import com.quew8.geng.geometry.Texture;
import com.quew8.geng.rendering.SpriteBatcher;
import com.quew8.geng.rendering.modes.FixedSizeDataInterpreter;
import com.quew8.geng.rendering.modes.StaticRenderMode;
import com.quew8.geng3d.geometry.Mesh3D;

/**
 *
 * @author Quew8
 */
public class MeshSpriteBatcher extends SpriteBatcher<Mesh3D> {
    private final int nVertices;
    
    public MeshSpriteBatcher(Texture tex, StaticRenderMode renderMode, FixedSizeDataInterpreter<Mesh3D, ?> dataInterpreter, int maxN) {
        super(tex, renderMode, dataInterpreter, maxN);
        this.nVertices = dataInterpreter.getNVertices();
    }
    
    public MeshSpriteBatcher(StaticRenderMode renderMode, FixedSizeDataInterpreter<Mesh3D, ?> dataInterpreter, int maxN) {
        this(null, renderMode, dataInterpreter, maxN);
    }
    
    public void draw(Mesh3D mesh) {
        assert(mesh.getNVertices() == nVertices);
        batch(mesh);
    }
}
