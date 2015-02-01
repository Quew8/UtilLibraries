package com.quew8.geng3d.models;

import com.quew8.geng.geometry.Image;
import com.quew8.geng3d.models.collada.ColladaSkeleton;
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
public abstract class SkinDataFactory<T, S> {
    private final Comparator<S> skinDataComparator;
    
    public SkinDataFactory(Comparator<S> skinDataComparator) {
        this.skinDataComparator = skinDataComparator;
    }
    
    public SkinDataFactory() {
        this.skinDataComparator = new Comparator<S>() {
            @Override
            public int compare(S o1, S o2) {
                return equalsSkinData(o1, o2) ? 0 : -1;
            }
        };
    }
    
    public final T constructSkin(int[] indices, DataInput vertexIn, DataInput weightIn, ColladaSkeleton colladaSkeleton, Image img) {
        int[] vCount = vertexIn.getVCount();
        int[] wVCount = weightIn.getVCount();
        int nVertices = ArrayUtils.sumArray(vCount);
        int nWeights = ArrayUtils.sumArray(wVCount);
        ArrayList<S> data = constructSkinVertexData(nVertices, vertexIn, nWeights, weightIn, wVCount, colladaSkeleton);
        cleanup(data, indices, skinDataComparator);
        return constructSkin(data, vCount, indices, img, colladaSkeleton);
    }
    
    public final T constructSkin(DataInput dataIn, DataInput weightIn, ColladaSkeleton colladaSkeleton, Image img) {
        int[] vCount = dataIn.getVCount();
        int nVertices = ArrayUtils.sumArray(vCount);
        int[] indices = new int[nVertices];
        for(int i = 0; i < nVertices; i++) {
            indices[i] = i;
        }
        return constructSkin(indices, dataIn, weightIn, colladaSkeleton, img);
    }
    
    public boolean equalsSkinData(S s1, S s2) {
        throw new UnsupportedOperationException("Method is not Supported");
    }
    
    protected abstract ArrayList<S> constructSkinVertexData(int nVertices, DataInput vertexIn, 
            int nWeights, DataInput weightIn, int[] wVCount, ColladaSkeleton skeleton);
    
    protected abstract T constructSkin(ArrayList<S> data, int[] vCount, int[] indices, 
            Image textureArea, ColladaSkeleton skeleton);
    
    protected abstract T transformSkin(T old, Matrix transform);
    
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
