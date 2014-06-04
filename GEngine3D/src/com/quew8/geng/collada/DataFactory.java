package com.quew8.geng.collada;

import com.quew8.geng.geometry.TextureArea;
import com.quew8.gmath.Matrix;
import com.quew8.gutils.ArrayUtils;
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author Quew8
 * @param <T>
 * @param <K>
 * @param <S>
 * @param <U>
 */
public abstract class DataFactory<T, K, S, U> {
    private final Comparator<T> meshDataComparator;
    private final Comparator<K> skinDataComparator;
    
    public DataFactory(Comparator<T> meshDataComparator, Comparator<K> skinDataComparator) {
        this.meshDataComparator = meshDataComparator;
        this.skinDataComparator = skinDataComparator;
    }
    
    public DataFactory() {
        this.meshDataComparator = new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                return equalsMeshData(o1, o2) ? 0 : -1;
            }
        };
        this.skinDataComparator = new Comparator<K>() {
            @Override
            public int compare(K o1, K o2) {
                return equalsSkinData(o1, o2) ? 0 : -1;
            }
        };
    }
    
    public final S constructMesh(DataInput dataIn, TextureArea textureArea) {
        int[] vCount = dataIn.getVCount();
        int nVertices = ArrayUtils.sumArray(vCount);
        ArrayList<T> data = constructVertexData(nVertices, dataIn);
        int[] indices = new int[nVertices];
        for(int i = 0; i < nVertices; i++) {
            indices[i] = i;
        }
        cleanup(data, indices, meshDataComparator);
        return constructMesh(data, vCount, indices, textureArea);
    }
    
    public final U constructSkin(DataInput vertexIn, DataInput weightIn, ColladaSkeleton colladaSkeleton, TextureArea textureArea) {
        int[] vCount = vertexIn.getVCount();
        int[] wVCount = weightIn.getVCount();
        int nVertices = ArrayUtils.sumArray(vCount);
        int nWeights = ArrayUtils.sumArray(wVCount);
        ArrayList<K> data = constructSkinVertexData(nVertices, vertexIn, nWeights, weightIn, wVCount, colladaSkeleton);
        int[] indices = new int[nVertices];
        for(int i = 0; i < nVertices; i++) {
            indices[i] = i;
        }
        cleanup(data, indices, skinDataComparator);
        return constructSkin(data, vCount, indices, textureArea, colladaSkeleton);
    }
    
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
    
    public boolean equalsMeshData(T t1, T t2) {
        throw new UnsupportedOperationException("Method is not Supported");
    }
    
    public boolean equalsSkinData(K k1, K k2) {
        throw new UnsupportedOperationException("Method is not Supported");
    }
    
    protected abstract ArrayList<T> constructVertexData(int nVertices, DataInput dataIn);
    
    protected abstract ArrayList<K> constructSkinVertexData(int nVertices, DataInput vertexIn, 
            int nWeights, DataInput weightIn, int[] wVCount, ColladaSkeleton skeleton);
    
    protected abstract S constructMesh(ArrayList<T> data, int[] vCount, int[] indices, 
            TextureArea textureArea);
    
    protected abstract S transformMesh(S old, Matrix transform);
    
    protected abstract U constructSkin(ArrayList<K> data, int[] vCount, int[] indices, 
            TextureArea textureArea, ColladaSkeleton skeleton);
    
    protected abstract U transformSkin(U old, Matrix transform);
}
