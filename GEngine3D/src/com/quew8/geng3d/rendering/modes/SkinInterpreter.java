package com.quew8.geng.rendering.modes;

import com.quew8.geng.geometry.Skin;
import com.quew8.geng.geometry.WeightedVertex;
import com.quew8.gutils.BufferUtils;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public class SkinInterpreter extends GeometricObjectInterpreter<Skin, WeightedVertex> {
    public static SkinInterpreter INSTANCE = new SkinInterpreter();
    
    private SkinInterpreter() {
        
    }
    
    @Override
    public ByteBuffer toVertexData(Skin[] skins) {
        int length = 0;
        WeightedVertex[][] vertices = new WeightedVertex[skins.length][];
        for(int i = 0; i < vertices.length; i++) {
            vertices[i] = skins[i].getVertices();
            length += skins[i].getVerticesLength();
        }
        length *= WeightedVertex.WEIGHTED_VERTEX_BYTE_SIZE;
        ByteBuffer bb = BufferUtils.createByteBuffer(length);
        for(int i = 0; i < vertices.length; i++) {
            for(int j = 0; j < vertices[i].length; j++) {
                vertices[i][j].appendData(bb);
            }
        }
        bb.flip();
        return bb;
    }
    
}
