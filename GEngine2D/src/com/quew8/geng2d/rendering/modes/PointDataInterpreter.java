package com.quew8.geng2d.rendering.modes;

import com.quew8.geng.geometry.Param;
import com.quew8.geng.rendering.modes.FixedSizeDataInterpreter;
import com.quew8.geng2d.geometry.Vertex2D;
import static com.quew8.gutils.opengl.OpenGL.GL_POINTS;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public class PointDataInterpreter extends FixedSizeDataInterpreter<Vertex2D, Vertex2D> {
    private final Param[] params;

    public PointDataInterpreter(Param... params) {
        this.params = params;
    }
    
    @Override
    public Vertex2D[] toPositions(Vertex2D t) {
        return new Vertex2D[]{t};
    }

    @Override
    public void addVertexData(ByteBuffer to, Vertex2D t, Vertex2D[] positions) {
        addVertexData(to, t);
    }

    @Override
    public void addVertexData(ByteBuffer to, Vertex2D t) {
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
        return GL_POINTS;
    }
}
