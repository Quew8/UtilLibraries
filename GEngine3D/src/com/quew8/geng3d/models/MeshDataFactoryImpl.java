package com.quew8.geng3d.models;

import com.quew8.geng3d.geometry.Mesh3D;
import com.quew8.geng.geometry.Image;
import com.quew8.geng3d.geometry.Vertex3D;
import com.quew8.gmath.Matrix;
import com.quew8.gmath.Vector;
import com.quew8.gmath.Vector2;
import static com.quew8.gutils.opengl.OpenGL.GL_TRIANGLES;
import java.util.ArrayList;

/**
 *
 * @author Quew8
 */
public class MeshDataFactoryImpl extends MeshDataFactory<Mesh3D, Vertex3D> {
    public static final MeshDataFactoryImpl INSTANCE = new MeshDataFactoryImpl();
    
    private MeshDataFactoryImpl() {
        
    }
    
    @Override
    public ArrayList<Vertex3D> constructVertexData(int nVertices, DataInput dataIn) {
        ArrayList<Vertex3D> vertices = new ArrayList<Vertex3D>();
        float[] posData = new float[3 * nVertices];
        float[] normData = new float[3 * nVertices];
        float[] texData = new float[2 * nVertices];
        dataIn.putData(SemanticSet.VERTEX_0, posData, 0, 0, nVertices);
        dataIn.putData(SemanticSet.NORMAL_0, normData, 0, 0, nVertices);
        dataIn.putData(SemanticSet.TEXCOORD_0, texData, 0, 0, nVertices);
        for(int i = 0, posPos = 0, normPos = 0, texPos = 0; i < nVertices; i++) {
            vertices.add(new Vertex3D(
                    new Vector(posData[posPos++], posData[posPos++], posData[posPos++]),
                    new Vector(normData[normPos++], normData[normPos++], normData[normPos++]),
                    new Vector2(texData[texPos++], texData[texPos++])
            ));
        }
        return vertices;
    }
    
    @Override
    protected Mesh3D constructMesh(ArrayList<Vertex3D> data, int[] vCount, int[] indices, Image textureArea) {
        for(int i: vCount) {
            if(i != 3) {
                throw new RuntimeException("Only Triangle Based Meshes are Supported");
            }
        }
        Mesh3D m = new Mesh3D(
                data.toArray(new Vertex3D[data.size()]), 
                indices,
                GL_TRIANGLES
        );
        return m.transform(textureArea);
    }

    @Override
    protected Mesh3D transformMesh(Mesh3D old, Matrix transform) {
        return old.transform(transform, false);
    }
    
    @Override
    public boolean equalsMeshData(Vertex3D t1, Vertex3D t2) {
        return t1.equals(t2);
    }
    
}
