package com.quew8.geng3d.rendering.modes;

import com.quew8.geng.geometry.Param;
import com.quew8.geng.rendering.modes.FixedSizeDataInterpreter;
import com.quew8.geng3d.geometry.Vertex3D;
import com.quew8.gutils.opengl.OpenGL;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public class PointDataInterpreter extends FixedSizeDataInterpreter<Vertex3D, Vertex3D> {
    private final Param[] params;

    public PointDataInterpreter(Param... params) {
        this.params = params;
    }
    
    @Override
    public Vertex3D[] toPositions(Vertex3D t) {
        return new Vertex3D[]{t};
    }

    @Override
    public void addVertexData(ByteBuffer to, Vertex3D t, Vertex3D[] positions) {
        addVertexData(to, t);
    }

    @Override
    public void addVertexData(ByteBuffer to, Vertex3D t) {
        for(int j = 0; j < params.length; j++) {
            t.addData(to, params[j]);
        }
    }

    @Override
    public int getNVertices() {
        return 1;
    }

    @Override
    public int getNBytes() {
        return params.length * 4;
    }

    @Override
    public int[] getIndices() {
        return new int[]{0};
    }

    @Override
    public int getNIndices() {
        return 1;
    }

    @Override
    public int getMode() {
        return OpenGL.GL_POINTS;
    }
    
}
