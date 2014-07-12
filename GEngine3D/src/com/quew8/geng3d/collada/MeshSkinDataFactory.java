package com.quew8.geng3d.collada;

import com.quew8.geng.geometry.Joint;
import com.quew8.geng.geometry.Mesh;
import com.quew8.geng.geometry.Skeleton;
import com.quew8.geng.geometry.Skin;
import com.quew8.geng.geometry.Image;
import com.quew8.geng.geometry.Vertex;
import com.quew8.geng.geometry.WeightedVertex;
import com.quew8.gmath.Matrix;
import java.util.ArrayList;

/**
 *
 * @author Quew8
 */
public class MeshSkinDataFactory extends DataFactory<Vertex, WeightedVertex, Mesh, Skin> {
    public static final MeshSkinDataFactory INSTANCE = new MeshSkinDataFactory();
    
    private MeshSkinDataFactory() {
        
    }
    
    @Override
    public ArrayList<Vertex> constructVertexData(int nVertices, DataInput dataIn) {
        ArrayList<Vertex> vertices = new ArrayList<Vertex>();
        float[] posData = new float[3 * nVertices];
        float[] normData = new float[3 * nVertices];
        float[] texData = new float[2 * nVertices];
        dataIn.putData(SemanticSet.VERTEX_0, posData, 0, 0, nVertices);
        dataIn.putData(SemanticSet.NORMAL_0, normData, 0, 0, nVertices);
        dataIn.putData(SemanticSet.TEXCOORD_0, texData, 0, 0, nVertices);
        for(int i = 0, posPos = 0, normPos = 0, texPos = 0; i < nVertices; i++) {
            vertices.add(new Vertex(
                    posData[posPos++], posData[posPos++], posData[posPos++],
                    normData[normPos++], normData[normPos++], normData[normPos++],
                    texData[texPos++], texData[texPos++]
            ));
        }
        return vertices;
    }
    
    @Override
    protected ArrayList<WeightedVertex> constructSkinVertexData(int nVertices, DataInput vertexIn, 
            int nWeights, DataInput weightIn, int[] wVCount, ColladaSkeleton skeleton) {
        
        ArrayList<Vertex> vertices = constructVertexData(nVertices, vertexIn);
        ArrayList<WeightedVertex> wVertices = new ArrayList<WeightedVertex>();
        int nJoints = skeleton.getNJoints();
        for(Vertex v : vertices) {
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
    protected Mesh constructMesh(ArrayList<Vertex> data, int[] vCount, int[] indices, Image textureArea) {
        for(int i: vCount) {
            if(i != 3) {
                throw new RuntimeException("Only Triangle Based Meshes are Supported");
            }
        }
        return new Mesh(
                data.toArray(new Vertex[data.size()]), 
                indices, 
                textureArea);
    }

    @Override
    protected Mesh transformMesh(Mesh old, Matrix transform) {
        return old.transform(transform, false);
    }
    
    @Override
    protected Skin constructSkin(ArrayList<WeightedVertex> data, int[] vCount, int[] indices, Image textureArea, ColladaSkeleton colladaSkeleton) {
        for(int i: vCount) {
            if(i != 3) {
                throw new RuntimeException("Only Triangle Based Meshes are Supported");
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
                s,
                textureArea);
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
    public boolean equalsMeshData(Vertex t1, Vertex t2) {
        return 
                t1.getPosition().equals(t2.getPosition())
                &&
                t1.getNormal().equals(t2.getNormal())
                &&
                t1.getTexCoords()[0] == t2.getTexCoords()[0]
                &&
                t1.getTexCoords()[1] == t2.getTexCoords()[1];
    }

    @Override
    public boolean equalsSkinData(WeightedVertex t1, WeightedVertex t2) {
        return 
                t1.getPosition().equals(t2.getPosition())
                &&
                t1.getNormal().equals(t2.getNormal())
                &&
                t1.getTexCoords()[0] == t2.getTexCoords()[0]
                &&
                t1.getTexCoords()[1] == t2.getTexCoords()[1];
    }
    
}
