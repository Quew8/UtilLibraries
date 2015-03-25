package com.quew8.geng2d.rendering.modes;

import com.quew8.geng.geometry.Param;
import com.quew8.geng.rendering.modes.FixedSizeDataInterpreter;
import com.quew8.geng.rendering.modes.FixedSizeDataInterpreterImpl;
import com.quew8.geng.rendering.modes.GeometricDataInterpreter;
import com.quew8.geng2d.geometry.Polygon;
import com.quew8.geng2d.geometry.Vertex2D;
import static com.quew8.gutils.opengl.OpenGL.GL_LINES;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public class PolygonOutlineDataInterpreter implements GeometricDataInterpreter<Polygon, Vertex2D> {
    private final Param[] params;

    public PolygonOutlineDataInterpreter(Param... params) {
        this.params = params;
    }
    
    @Override
    public Vertex2D[] toPositions(Polygon t) {
        return t.vertices;
    }

    @Override
    public void addVertexData(ByteBuffer to, Polygon t, Vertex2D[] positions) {
        for(int i = 0; i < positions.length; i++) {
            for(int j = 0; j < params.length; j++) {
                positions[i].addData(to, params[j]);
            }
        }
    }

    @Override
    public void addVertexData(ByteBuffer to, Polygon t) {
        addVertexData(to, t, t.vertices);
    }

    @Override
    public int getNVertices(Polygon t) {
        return t.vertices.length;
    }

    @Override
    public int getNBytes(Polygon t) {
        return params.length * 4 * getNVertices(t);
    }

    @Override
    public int getMode(Polygon t) {
        return GL_LINES;
    }

    @Override
    public int[] getIndices(Polygon t) {
        return createPolygonIndices(getNVertices(t));
    }
    
    private static int[] createPolygonIndices(int nVertices) {
        int[] indices = new int[nVertices * 2];
        indices[0] = nVertices - 1;
        indices[1] = 0;
        for(int i = 0, pos = 2; i < nVertices - 1;) {
            indices[pos++] = i;
            indices[pos++] = ++i;
        }
        return indices;
    }
    
    public static FixedSizeDataInterpreter<Polygon, Vertex2D> getFixedSize(int nVertices, Param... params) {
        return new FixedSizeDataInterpreterImpl<Polygon, Vertex2D>(
                new PolygonOutlineDataInterpreter(params),
                new Polygon(new Vertex2D[nVertices])
        );
    }
}
