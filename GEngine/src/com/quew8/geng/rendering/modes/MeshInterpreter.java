package com.quew8.geng.rendering.modes;

import com.quew8.geng.geometry.Mesh;
import com.quew8.geng.geometry.Vertex;
import com.quew8.gutils.BufferUtils;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public abstract class MeshInterpreter extends GeometricObjectInterpreter<Mesh, Vertex> {
    
    public static ByteBuffer toVertexData(Mesh[] meshes, int[] params) {
        int length = meshes[0].getVerticesLength();
        for(int i = 1; i < meshes.length; i++) {
            length += meshes[i].getVerticesLength();
        }
        length *= params.length;
        
        ByteBuffer bb = BufferUtils.createByteBuffer(length * 4);
        for(int i = 0; i < meshes.length; i++) {
            Vertex[] vertices = meshes[i].getVertices();
            for(int j = 0; j < vertices.length; j++) {
                vertices[j].appendData(bb, params);
            }
        }
        bb.flip();
        return bb;
    }
}
