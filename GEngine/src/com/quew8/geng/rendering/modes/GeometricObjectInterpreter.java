package com.quew8.geng.rendering.modes;

import com.quew8.geng.rendering.modes.interfaces.GeometricDataInterpreter;
import com.quew8.geng.geometry.AbstractVertex;
import com.quew8.geng.geometry.GeometricObject;
import com.quew8.gmath.Vector;
import com.quew8.gutils.ArrayUtils;

/**
 *
 * @author Quew8
 */
abstract class GeometricObjectInterpreter<T extends GeometricObject<T, S>, S extends AbstractVertex<S>> implements GeometricDataInterpreter<T> {
    
    @Override
    public Vector[] toPositions(T[] meshes) {
        int length = meshes[0].getVerticesLength();
        for(int i = 1; i < meshes.length; i++) {
            length += meshes[i].getVerticesLength();
        }
        Vector[] vertices = new Vector[length];
        for(int i = 0, arrayOffset = 0; i < meshes.length; i++) {
            S[] mv = meshes[i].getVertices();
            for(int j = 0; j < mv.length; j++, arrayOffset++) {
                vertices[arrayOffset] = mv[j].getPosition();
            }
        }
        return vertices;
    }
    
    @Override
    public int[][] toIndexData(T[] meshes) {
        int[][] indices = new int[meshes.length][];
        int indexOffset = 0;
        for(int i = 0; i < meshes.length; i++) {
            indices[i] = ArrayUtils.add(meshes[i].getIndices(), indexOffset);
            indexOffset += meshes[i].getMaxIndex();
        }
        return indices;
    }
}
