package com.quew8.geng.rendering.modes;

import com.quew8.geng.geometry.Mesh;
import com.quew8.geng.geometry.Vertex;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public class Mesh3DInterpreter extends MeshInterpreter {
    public static Mesh3DInterpreter INSTANCE = new Mesh3DInterpreter();
    
    private Mesh3DInterpreter() {
        
    }
    
    @Override
    public ByteBuffer toVertexData(Mesh[] meshes) {
        return MeshInterpreter.toVertexData(meshes, new int[]{
            Vertex.X, Vertex.Y, Vertex.Z, 
            Vertex.NX, Vertex.NY, Vertex.NZ, 
            Vertex.TX, Vertex.TY
        });
    }
}
