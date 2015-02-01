package com.quew8.geng3d.models;

import com.quew8.geng.geometry.Joint;
import com.quew8.geng3d.geometry.Mesh3D;
import com.quew8.geng3d.geometry.Skeleton;
import com.quew8.geng3d.geometry.Skin;
import com.quew8.geng.geometry.Image;
import com.quew8.geng3d.geometry.Vertex3D;
import com.quew8.geng3d.geometry.WeightedVertex;
import com.quew8.geng3d.models.collada.ColladaJoint;
import com.quew8.geng3d.models.collada.ColladaSkeleton;
import com.quew8.gmath.Matrix;
import com.quew8.gmath.Vector;
import com.quew8.gmath.Vector2;
import static com.quew8.gutils.opengl.OpenGL.GL_TRIANGLES;
import java.util.ArrayList;

/**
 *
 * @author Quew8
 */
public class SkinDataFactoryImpl extends SkinDataFactory<Skin, WeightedVertex> {
    public static final SkinDataFactoryImpl INSTANCE = new SkinDataFactoryImpl();
    
    private SkinDataFactoryImpl() {
        
    }
    
    private ArrayList<Vertex3D> constructVertexData(int nVertices, DataInput dataIn) {
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
    protected ArrayList<WeightedVertex> constructSkinVertexData(int nVertices, DataInput vertexIn, 
            int nWeights, DataInput weightIn, int[] wVCount, ColladaSkeleton skeleton) {
        
        ArrayList<Vertex3D> vertices = constructVertexData(nVertices, vertexIn);
        ArrayList<WeightedVertex> wVertices = new ArrayList<WeightedVertex>();
        int nJoints = skeleton.getNJoints();
        for(Vertex3D v : vertices) {
            wVertices.add(new WeightedVertex(v, new float[nJoints]));
        }
        String[] jointNames = new String[nWeights];
        weightIn.putData(SemanticSet.JOINT_0, jointNames, 0, 0, nWeights);
        float[] weights = new float[nWeights];
        weightIn.putData(SemanticSet.WEIGHT_0, weights, 0, 0, nWeights);
        int jointNamePos = 0;
        int weightPos = 0;
        for(int i = 0; i < wVCount.length; i++) {
            for(int j = 0; j < wVCount[i]; j++) {
                wVertices.get(i).setWeight(
                        skeleton.findJoint(jointNames[jointNamePos++]).getIndex(), 
                        weights[weightPos++]
                );
            }
        }
        for(int i = 0; i < nWeights; i++) {
            wVertices.get(i).setWeight(
                    skeleton.findJoint(jointNames[i]).getIndex(), 
                    weights[i]
            );
        }
        return wVertices;
    }
    
    @Override
    protected Skin constructSkin(ArrayList<WeightedVertex> data, int[] vCount, int[] indices, Image textureArea, ColladaSkeleton colladaSkeleton) {
        for(int i: vCount) {
            if(i != 3) {
                throw new RuntimeException("Only Triangle Based Skins are Supported");
            }
        }
        ColladaJoint[] children = colladaSkeleton.getChildren();
        Joint[] joints = new Joint[children.length];
        for(int i = 0; i < children.length; i++) {
            joints[i] = toJoint(children[i]);
        }
        Skeleton s = new Skeleton(colladaSkeleton.getBindShapeMatrix(), joints, colladaSkeleton.getNJoints());
        
        return new Skin(
                data.toArray(new WeightedVertex[data.size()]),
                indices,
                GL_TRIANGLES,
                s
        ).transform(textureArea);
    }
    
    private static Joint toJoint(ColladaJoint j) {
        ColladaJoint[] children = j.getChildren();
        Joint[] joints = new Joint[children.length];
        for(int i = 0; i < children.length; i++) {
            joints[i] = toJoint(children[i]);
        }
        return new Joint(j.getIndex(), j.getInvBindMatrix(), j.getJointMatrix(), joints);
    }

    @Override
    protected Skin transformSkin(Skin old, Matrix transform) {
        return old.transform(transform, false);
    }

    @Override
    public boolean equalsSkinData(WeightedVertex t1, WeightedVertex t2) {
        return t1.equals(t2);
    }
    
}
