package com.quew8.geng.rendering.modes;

import com.quew8.geng.geometry.Mesh;
import com.quew8.geng.geometry.Vertex;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public class Mesh2DInterpreter extends MeshInterpreter {
    public static Mesh2DInterpreter INSTANCE = new Mesh2DInterpreter();
    
    private Mesh2DInterpreter() {
        
    }
    
    @Override
    public ByteBuffer toVertexData(Mesh[] meshes) {
        return MeshInterpreter.toVertexData(meshes, new int[]{Vertex.X, Vertex.Y, Vertex.TX, Vertex.TY});
    }
}
