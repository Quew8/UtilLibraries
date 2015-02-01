package com.quew8.geng3d.models.obj.parser;

import com.quew8.geng.geometry.Image;
import com.quew8.geng3d.models.DataInput;
import com.quew8.geng3d.models.MeshDataFactory;
import com.quew8.geng3d.models.SemanticSet;
import com.quew8.gmath.Matrix;
import com.quew8.gmath.Vector;
import com.quew8.gmath.Vector2;
import com.quew8.gmath.Vector4;
import com.quew8.gutils.ArrayUtils;

/**
 *
 * @author Quew8
 */
public class WavefrontObject {
    private final String name;
    private final Vector4[] positions;
    private final Vector[] normals;
    private final Vector2[] texCoords;
    private final int[][] vertices;
    private final int[][] faces;

    public WavefrontObject(String name, Vector4[] positions, Vector[] normals, Vector2[] texCoords, int[][] vertices, int[][] faces) {
        this.name = name;
        this.positions = positions;
        this.normals = normals;
        this.texCoords = texCoords;
        this.vertices = vertices;
        this.faces = faces;
    }

    public String getName() {
        return name;
    }
    
    public <T> T getGeometry(MeshDataFactory<T, ?> dataFactory, Image img) {
        int[] flatIndices = ArrayUtils.concatVariableLengthArrays(faces);
        return dataFactory.constructMesh(flatIndices, new ObjectDataInput(), img);
    }
    
    @Override
    public String toString() {
        String s = "WavefrontObject: " + name + "\n====Positions====\n";
        for(Vector4 v: positions) {
            s += v + "\n";
        }
        s += "====Tex Coords====\n";
        for(Vector2 v: texCoords) {
            s += v + "\n";
        }
        s += "====Normals====\n";
        for(Vector v: normals) {
            s += v + "\n";
        }
        s += "====Vertices====\n";
        for(int[] v: vertices) {
            s += "{" + v[0] + ", " + v[1] + ", " + v[2] + "}\n";
        }
        s += "====Faces====\n";
        for(int[] f: faces) {
            s += "{" + f[0];
            for(int i = 1; i < f.length; i++) {
                s += ", " + f[i];
            }
            s += "}\n";
        }
        return s;
    }
    
    private class ObjectDataInput implements DataInput {

        @Override
        public int[] getVCount() {
            return ArrayUtils.arrayOfLengths(faces);
        }

        @Override
        public void putData(SemanticSet source, float[] dest, int offset, int initialIndex, int n) {
            if(initialIndex + n >= vertices.length) {
                throw new IndexOutOfBoundsException("Requested " + n + " vertices from " + initialIndex + " wheras only " + vertices.length + " vertices exist");
            }
            int destPos = offset;
            if(source.equals(SemanticSet.VERTEX_0)) {
                for(int i = 0; i < n; i++) {
                    Vector4 pos = positions[vertices[initialIndex + i][0]];
                    dest[destPos++] = pos.getX();
                    dest[destPos++] = pos.getY();
                    dest[destPos++] = pos.getZ();
                }
            } else if(source.equals(SemanticSet.TEXCOORD_0)) {
                for(int i = 0; i < n; i++) {
                    if(vertices[initialIndex + i][1] == -1) {
                        throw new IllegalArgumentException("Vertex " + (initialIndex + i) + " doesn't contain tex coords");
                    }
                    Vector2 texCoord = texCoords[vertices[initialIndex + i][1]];
                    dest[destPos++] = texCoord.getX();
                    dest[destPos++] = texCoord.getY();
                }
            } else if(source.equals(SemanticSet.NORMAL_0)) {
                for(int i = 0; i < n; i++) {
                    if(vertices[initialIndex + i][2] == -1) {
                        throw new IllegalArgumentException("Vertex " + (initialIndex + i) + " doesn't contain normals");
                    }
                    Vector normal = normals[vertices[initialIndex + i][2]];
                    dest[destPos++] = normal.getX();
                    dest[destPos++] = normal.getY();
                    dest[destPos++] = normal.getZ();
                }
            } else {
                throw new IllegalArgumentException("Requested Nonexistent Semantic: " + source);
            }
        }

        @Override
        public void putData(SemanticSet source, String[] dest, int offset, int initialIndex, int n) {
            throw new IllegalArgumentException("Requested Nonexistent Semantic: " + source);
        }

        @Override
        public void putData(SemanticSet source, Matrix[] dest, int offset, int initialIndex, int n) {
            throw new IllegalArgumentException("Requested Nonexistent Semantic: " + source);
        }
    }
}
