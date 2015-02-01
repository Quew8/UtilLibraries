package com.quew8.geng3d.models;

import com.quew8.geng.geometry.Image;
import com.quew8.gmath.Matrix;
import com.quew8.gutils.ArrayUtils;
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author Quew8
 * @param <T>
 * @param <S>
 */
public abstract class MeshDataFactory<T, S> {
    private final Comparator<S> meshDataComparator;
    
    public MeshDataFactory(Comparator<S> meshDataComparator) {
        this.meshDataComparator = meshDataComparator;
    }
    
    public MeshDataFactory() {
        this.meshDataComparator = new Comparator<S>() {
            @Override
            public int compare(S o1, S o2) {
                return equalsMeshData(o1, o2) ? 0 : -1;
            }
        };
    }
    
    public final T constructMesh(int[] indices, DataInput dataIn, Image img) {
        int[] vCount = dataIn.getVCount();
        int nVertices = ArrayUtils.sumArray(vCount);
        ArrayList<S> data = constructVertexData(nVertices, dataIn);
        cleanup(data, indices, meshDataComparator);
        return constructMesh(data, vCount, indices, img);
    }
    
    public final T constructMesh(DataInput dataIn, Image img) {
        int[] vCount = dataIn.getVCount();
        int nVertices = ArrayUtils.sumArray(vCount);
        int[] indices = new int[nVertices];
        for(int i = 0; i < nVertices; i++) {
            indices[i] = i;
        }
        return constructMesh(indices, dataIn, img);
    }
    
    public boolean equalsMeshData(S s1, S s2) {
        throw new UnsupportedOperationException("Method is not Supported");
    }
    
    protected abstract ArrayList<S> constructVertexData(int nVertices, DataInput dataIn);
    
    protected abstract T constructMesh(ArrayList<S> data, int[] vCount, int[] indices, 
            Image textureArea);
    
    protected abstract T transformMesh(T old, Matrix transform);
    
    private static <T> void cleanup(ArrayList<T> data, int[] indices, Comparator<T> comparator) {
        for(int i = 0; i < data.size(); i++) {
            for(int k = i+1; k < data.size(); k++) {
                if(comparator.compare(data.get(i), data.get(k)) == 0) {
                    data.remove(k);
                    for(int j = 0; j < indices.length; j++) {
                        if(indices[j] == k) {
                            indices[j] = i;
                        } else if(indices[j] > k) {
                            indices[j] = indices[j] - 1;
                        }
                    }
                }
            }
        }
    }
}
