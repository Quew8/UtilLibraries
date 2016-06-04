package com.quew8.geng3d.models.obj.parser;

import com.quew8.geng3d.models.SemanticSet;
import com.quew8.gmath.*;
import com.quew8.gutils.ArrayUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WavefrontParser {
    private final ArrayList<WavefrontObject> objects = new ArrayList<WavefrontObject>();
    private String currentObject = null;
    private final ArrayList<Vector4> positions = new ArrayList<Vector4>();
    private final ArrayList<Vector> normals = new ArrayList<Vector>();
    private final ArrayList<Vector2> texCoords = new ArrayList<Vector2>();
    private final ArrayList<int[]> vertices = new ArrayList<int[]>();
    private final ArrayList<int[]> faces = new ArrayList<int[]>();

    public void parse(InputStream in) {
        int lineNumber = 0;
        String line = null;
        try(BufferedReader bin = new BufferedReader(new InputStreamReader(in))) {
            while((line = bin.readLine()) != null) {
                if(line.startsWith("#")) {
                    continue;
                }else if(line.startsWith("vt")) {
                    parseTexCoords(line.substring(2));
                } else if(line.startsWith("vn")) {
                    parseNormal(line.substring(2));
                } else if(line.startsWith("v")) {
                    parseVertex(line.substring(1));
                } else if(line.startsWith("f")) {
                    parseFace(line.substring(1));
                } else if(line.startsWith("o")) {
                    if(!faces.isEmpty()) {
                        parseObject();
                    }
                    currentObject = line.substring(2).trim();
                } else if(line.startsWith("s")) {
                    
                } else {
                    throw new InputMismatchException();
                }
                lineNumber++;
            }
        } catch(InputMismatchException ex) {
            throw new RuntimeException("Invalid element in line " + lineNumber + " \"" + line + "\"", ex);
        } catch(NoSuchElementException ex) {
            throw new RuntimeException("Expected additional input in line " + lineNumber + " \"" + line + "\"", ex);
        } catch(UnexpectedElementException ex) {
            throw new RuntimeException("Unexpected addeitional input in line " + lineNumber + " \"" + line + "\"", ex);
        } catch(IOException ex) {
            throw new RuntimeException("Unhandled IOException", ex);
        }
        parseObject();
    }
    
    private void parseVertex(String line) {
        Scanner lineScanner = new Scanner(line);
        float x = lineScanner.nextFloat();
        float y = lineScanner.nextFloat();
        float z = lineScanner.nextFloat();
        float w = lineScanner.hasNextFloat() ? lineScanner.nextFloat() : 1;
        if(lineScanner.hasNext()) {
            throw new UnexpectedElementException();
        }
        positions.add(new Vector4(x, y, z, w));
    }
    
    private void parseTexCoords(String line) {
        Scanner lineScanner = new Scanner(line);
        float u = lineScanner.nextFloat();
        float v = lineScanner.nextFloat();
        float w = lineScanner.hasNextFloat() ? lineScanner.nextFloat() : 0;
        if(lineScanner.hasNext()) {
            throw new UnexpectedElementException();
        }
        texCoords.add(new Vector2(u, v));
    }
    
    private void parseNormal(String line) {
        Scanner lineScanner = new Scanner(line);
        float x = lineScanner.nextFloat();
        float y = lineScanner.nextFloat();
        float z = lineScanner.nextFloat();
        if(lineScanner.hasNext()) {
            throw new UnexpectedElementException();
        }
        normals.add(new Vector(x, y, z));
    }
    
    private void parseFace(String line) {
        ArrayList<Integer> indices = new ArrayList<Integer>();
        Pattern p = Pattern.compile("(\\d)+(/(\\d)*(/(\\d)*)?)?");
        Matcher m = p.matcher(line);
        while(m.find()) {
            int vertexIndex = Integer.parseInt(m.group(1));
            int texCoordIndex = -1;
            int normalIndex = -1;
            if(!m.group(3).isEmpty()) {
                texCoordIndex = Integer.parseInt(m.group(3));
            }
            if(!m.group(5).isEmpty()) {
                normalIndex = Integer.parseInt(m.group(5));
            }
            indices.add(vertices.size());
            vertices.add(new int[]{vertexIndex, texCoordIndex, normalIndex});
        }
        if(!m.hitEnd()) {
            throw new UnexpectedElementException();
        }
        if(indices.isEmpty()) {
            throw new NoSuchElementException();
        }
        faces.add(ArrayUtils.unbox(indices.toArray(new Integer[indices.size()])));
    }

    private void parseObject() {
        objects.add(new WavefrontObject(
                currentObject == null ? "Object" : currentObject,
                positions.toArray(new Vector4[positions.size()]),
                normals.toArray(new Vector[normals.size()]),
                texCoords.toArray(new Vector2[texCoords.size()]),
                vertices.toArray(new int[vertices.size()][]),
                faces.toArray(new int[faces.size()][])
        ));
        positions.clear();
        normals.clear();
        texCoords.clear();
        vertices.clear();
        faces.clear();
    }
    
    /*public Mesh3D[] getMesh(Image textureArea) {
        Mesh3D[] meshes = new Mesh3D[facess.size()];
        for(int i = 0; i < meshes.length; i++) {
            meshes[i] = new Mesh3D(facess.get(i), textureArea);
        }
        return meshes;
    }*/
    
    public WavefrontObject getObject(String name) {
        for(WavefrontObject obj: objects) {
            if(obj.getName().equals(name)) {
                return obj;
            }
        }
        throw new IllegalArgumentException("No Such Object");
    }
    
    public WavefrontObject getObject() {
        return getObject("Object");
    }
    
    @Override
    public String toString() {
        String s = "WavefrontParser:\n====Objects====\n";
        for(WavefrontObject obj: objects) {
            s += obj + "\n";
        }
        return s;
    }
    
    private static class UnexpectedElementException extends RuntimeException {

        UnexpectedElementException() {
            
        }
        
    }
        
}
