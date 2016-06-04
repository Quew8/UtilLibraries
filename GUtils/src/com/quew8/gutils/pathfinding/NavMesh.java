package com.quew8.gutils.pathfinding;

import com.quew8.gmath.GMath;
import com.quew8.gmath.Vector2;
import com.quew8.gutils.collections.Bag;
import com.quew8.gutils.collections.IntBag;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Quew8
 */
public class NavMesh {
    private final Bag<NavTri> meshes;
    
    public NavMesh(NavTri[] triangles) {
        meshes = new Bag<NavTri>(triangles);
    }
    
    public NavMesh(float x0, float y0, float x1, float y1) {
        this(getQuad(x0, y0, x1, y1));
        System.out.println("NavMesh " + x0 + ", " + y0 + ", " + x1 + ", " + y1);
    }
    
    public NavTri[] getTriangles() {
        return meshes.getBackingArray();
    }
    
    public NavTri getTriangle(int index) {
        return meshes.get(index);
    }
    
    public int getNTriangles() {
        return meshes.size();
    }
    
    public void cutout(Vector2 a, Vector2 b, Vector2 c) {
        System.out.println("Cutting Out " + a + ", " + b + ", " + c);
        ArrayList<Vector2> outerPoly = new ArrayList<Vector2>();
        int[] indices = getContainedMeshes(a, b, c);
        if(indices.length == 0) {
            throw new RuntimeException("No triangles containing split");
        }
        NavTri[] intMeshes = new NavTri[indices.length];
        for(int i = 0; i < indices.length; i++) {
            //System.out.println("ADDING TRI: " + meshes.get(indices[i]));
            intMeshes[i] = meshes.get(indices[i]);
            outerPoly.add(intMeshes[i].getA());
            outerPoly.add(intMeshes[i].getB());
            outerPoly.add(intMeshes[i].getC());
        }
        Vector2[] outerVs = sortPoly(outerPoly.toArray(new Vector2[outerPoly.size()]));
        /*System.out.println("OUTER VS");
        for(int i = 0; i < outerVs.length; i++) {
            System.out.println(i + " " + outerVs[i]);
        }*/
        NavTri[] borders = new NavTri[outerVs.length];
        for(int i = 0; i < outerVs.length; i++) {
            Vector2 v = outerVs[i];
            Vector2 n = outerVs[i + 1 == outerVs.length ? 0 : i + 1];
            //System.out.println("Testing for " + i);
            int index = getIndexOfTriSharingEdge(v, n);
            borders[i] = meshes.get(index);
            for(int j = 0; j < indices.length; j++) {
                if(indices[j] == index) {
                    borders[i] = borders[i].getNeighbour(v, n);
                    break;
                }
            }
        }
        for(int i = 0; i < indices.length; i++) {
            intMeshes[i].delete();
            meshes.remove(indices[i]);
        }
        addMeshes(outerVs, borders, a, b, c);
    }
    
    private static Vector2 getCornerNorm(Vector2 edgeA, Vector2 edgeB, Vector2 c) {
        Vector2 centre = edgeA.add(new Vector2(), edgeB);
        centre.scale(centre, 0.5f);
        Vector2 norm = new Vector2(centre, c);
        norm.normalize(norm);
        return norm;
    }
    
    private void addMeshes(Vector2[] outerPoly, NavTri[] borders, Vector2 a, Vector2 b, Vector2 c) {
        Vector2[] tri = sortPoly(new Vector2[]{a, b, c});
        a = tri[0];
        b = tri[1];
        c = tri[2];
        Vector2[] cornerNorms = new Vector2[]{
            getCornerNorm(b, c, a),
            getCornerNorm(a, c, b),
            getCornerNorm(a, b, c)
        };
        Vector2[] edgeNorms = new Vector2[]{
            GMath.perpendicular(new Vector2(a, b)),
            GMath.perpendicular(new Vector2(b, c)),
            GMath.perpendicular(new Vector2(c, a))
        };
        edgeNorms[0].scale(edgeNorms[0], GMath.dot(edgeNorms[0], new Vector2(c, a)));
        edgeNorms[1].scale(edgeNorms[1], GMath.dot(edgeNorms[1], new Vector2(a, b)));
        edgeNorms[2].scale(edgeNorms[2], GMath.dot(edgeNorms[2], new Vector2(b, c)));
        
        Vector2[] edgeAs = new Vector2[]{a, b, c};
        Vector2[] edgeBs = new Vector2[]{b, c, a};
        Vector2[] edgeANorms = {cornerNorms[0], cornerNorms[1], cornerNorms[2]};
        Vector2[] edgeBNorms = {cornerNorms[1], cornerNorms[2], cornerNorms[0]};
        int currentEdge = 0;
        int currentVert = 0;
        while(!inEdgeRegion(
                edgeAs[currentEdge], edgeBs[currentEdge], edgeNorms[currentEdge], 
                edgeANorms[currentEdge], edgeBNorms[currentEdge], 
                outerPoly[currentVert])) {
            currentVert++;
            if(currentVert >= edgeAs.length) {
                throw new RuntimeException("Vertex 0 " + outerPoly[currentVert] + ", is not in any edge region");
            }
        }
        int startEdge = currentEdge;
        if(inEdgeRegion(
                edgeAs[currentEdge], edgeBs[currentEdge], edgeNorms[currentEdge], 
                edgeANorms[currentEdge], edgeBNorms[currentEdge], 
                outerPoly[currentVert])) {
            currentVert = outerPoly.length;
            while(inEdgeRegion(
                    edgeAs[currentEdge], edgeBs[currentEdge], edgeNorms[currentEdge], 
                    edgeANorms[currentEdge], edgeBNorms[currentEdge], outerPoly[currentVert-1])) {
                currentVert--;
                if(currentVert <= 0) {
                    throw new RuntimeException("No Vertices In Region");
                }
            }
            if(currentVert == outerPoly.length) {
                currentVert = 0;
            }
        } else {
            currentVert++;
            while(!inEdgeRegion(edgeAs[currentEdge], edgeBs[currentEdge], edgeNorms[currentEdge], edgeANorms[currentEdge], edgeBNorms[currentEdge], outerPoly[currentVert])) {
                currentVert++;
                if(currentVert >= outerPoly.length) {
                    throw new RuntimeException("No Vertices In Region");
                }
            }
        }
        NavTri firstAdded = null;
        NavTri lastAdded = null;
        while(currentEdge < edgeAs.length + startEdge) {
            final int actualCurrentEdge = currentEdge % edgeAs.length;
            int edgeCount = 0;
            int last = currentVert - 1;
            if(last < 0) {
                last += outerPoly.length;
            }
            NavTri toAdd = new NavTri(edgeAs[actualCurrentEdge], outerPoly[last], outerPoly[currentVert]);
            if(borders[last] != null) {
                NavTri.setNeighbours(borders[last], toAdd);
            }
            if(lastAdded != null) {
                NavTri.setNeighbours(toAdd, lastAdded);
            } else {
                firstAdded = toAdd;
            }
            meshes.add(toAdd);
            lastAdded = toAdd;
            System.out.println("Outer Poly " + outerPoly[currentVert] + ", with edge " + edgeAs[actualCurrentEdge] + ", " + edgeBs[actualCurrentEdge]);
            System.out.println("    Adding " + toAdd);
            do {
                last = currentVert - 1;
                if(last < 0) {
                    last += outerPoly.length;
                }
                if(edgeCount == 0) {
                    toAdd = new NavTri(edgeAs[actualCurrentEdge], edgeBs[actualCurrentEdge], outerPoly[currentVert]);
                } else {
                    toAdd = new NavTri(edgeBs[actualCurrentEdge], outerPoly[last], outerPoly[currentVert]);
                    if(borders[last] != null) {
                        NavTri.setNeighbours(borders[last], toAdd);
                    }
                }
                NavTri.setNeighbours(toAdd, lastAdded);
                meshes.add(toAdd);
                System.out.println("Outer Poly " + outerPoly[currentVert] + ", with edge " + edgeAs[actualCurrentEdge] + ", " + edgeBs[actualCurrentEdge] + " (R)");
                System.out.println("    Adding " + toAdd);
                
                lastAdded = toAdd;
                currentVert++;
                edgeCount++;
                if(currentVert == outerPoly.length) {
                    currentVert = 0;
                }
            } while(inEdgeRegion(
                    edgeAs[actualCurrentEdge], edgeBs[actualCurrentEdge], 
                    edgeNorms[actualCurrentEdge], edgeANorms[actualCurrentEdge], edgeBNorms[actualCurrentEdge], 
                    outerPoly[currentVert])
                    );
            currentEdge++;
        }
        NavTri.setNeighbours(firstAdded, lastAdded);
    }
    
    private boolean inEdgeRegion(Vector2 a, Vector2 b, Vector2 abNorm, Vector2 aNorm, Vector2 bNorm, Vector2 p) {
        if(GMath.dot(abNorm, new Vector2(a, p)) <= 0) {
            return false;
        } 
        Vector2 aPerpNorm = GMath.perpendicular(aNorm);
        Vector2 bPerpNorm = GMath.perpendicular(bNorm).scale(new Vector2(), -1);
        return Math.signum(GMath.dot(aPerpNorm, new Vector2(a, p))) == 
                Math.signum(GMath.dot(bPerpNorm, new Vector2(b, p)));
    }
    
    private int[] getContainedMeshes(Vector2 a, Vector2 b, Vector2 c) {
        IntBag bag = new IntBag(0);
        for(int i = 0; i < meshes.size(); i++) {
            NavTri mesh = meshes.get(i);
            if(intersects(a, b, c, mesh) || 
                    isInside(mesh.getA(), a, b, c) ||
                    isInside(a, mesh.getA(), mesh.getB(), mesh.getC())) {
                
                bag.add(i);
            }
        }
        bag.trim();
        return bag.getBackingArray();
    }
    
    public int getIndexOfTriContaining(Vector2 a) {
        for(int i = 0; i < meshes.size(); i++) {
            NavTri mesh = meshes.get(i);
            if(isInside(a, mesh.getA(), mesh.getB(), mesh.getC())) {
                return i;
            }
        }
        throw new IllegalArgumentException("No ");
    }
    
    private int getIndexOfTriSharingEdge(Vector2 a, Vector2 b) {
        for(int i = 0; i < meshes.size(); i++) {
            NavTri mesh = meshes.get(i);
            //System.out.println("    " + mesh);
            if(mesh.sharesEdge(a, b)) {
                return i;
            }
        }
        throw new IllegalArgumentException("No triangle with edge: " + a + ", " + b);
    }
    
    private static Vector2[] sortPoly(Vector2[] vectors) {
        Vector2 centre = new Vector2();
        Arrays.stream(vectors)
                .forEach((t) -> centre.add(centre, t));
        centre.scale(centre, 1f / vectors.length);
        return Arrays.stream(vectors)
                .distinct()
                .sorted((left, right) -> {
                    Vector2 toLeft = new Vector2(centre, left);
                    Vector2 toRight = new Vector2(centre, right);
                    double angle1 = Math.atan2(toLeft.getY(), toLeft.getX());
                    double angle2 = Math.atan2(toRight.getY(), toRight.getX());
                    if(angle1 < angle2) {
                        return 1;
                    } else if (angle2 < angle1) {
                        return -1;
                    }
                    throw new IllegalArgumentException();
                })
                .toArray((n) -> new Vector2[n]);
    }
    
    private static boolean isInside(Vector2 p, Vector2 a, Vector2 b, Vector2 c) {
        //Potentially use sam sign method rather than dotting all the time
        Vector2 abNorm = GMath.perpendicular(new Vector2(a, b));
        abNorm.scale(abNorm, GMath.dot(abNorm, new Vector2(c, a)));
        Vector2 bcNorm = GMath.perpendicular(new Vector2(b, c));
        bcNorm.scale(bcNorm, GMath.dot(bcNorm, new Vector2(a, b)));
        Vector2 caNorm = GMath.perpendicular(new Vector2(c, a));
        caNorm.scale(caNorm, GMath.dot(caNorm, new Vector2(b, c)));
        return GMath.dot(new Vector2(a, p), abNorm) <= 0 &&
                GMath.dot(new Vector2(b, p), bcNorm) <= 0 &&
                GMath.dot(new Vector2(c, p), caNorm) <= 0;
    }
    
    private static boolean intersects(Vector2 a, Vector2 b, Vector2 c, NavTri mesh) {
        Vector2 ab1 = new Vector2(a, b);
        Vector2 ab2 = new Vector2(mesh.getA(), mesh.getB());
        Vector2 bc2 = new Vector2(mesh.getB(), mesh.getC());
        Vector2 ca2 = new Vector2(mesh.getC(), mesh.getA());
        if(GMath.intersectionLineSegments2D(a, ab1, mesh.getA(), ab2) != null ||
                GMath.intersectionLineSegments2D(a, ab1, mesh.getB(), bc2) != null ||
                GMath.intersectionLineSegments2D(a, ab1, mesh.getC(), ca2) != null) {
            return true;
        }
        Vector2 bc1 = new Vector2(b, c);
        if(GMath.intersectionLineSegments2D(b, bc1, mesh.getA(), ab2) != null ||
                GMath.intersectionLineSegments2D(b, bc1, mesh.getB(), bc2) != null ||
                GMath.intersectionLineSegments2D(b, bc1, mesh.getC(), ca2) != null) {
            return true;
        }
        Vector2 ca1 = new Vector2(c, a);
        return GMath.intersectionLineSegments2D(c, ca1, mesh.getA(), ab2) != null ||
                GMath.intersectionLineSegments2D(c, ca1, mesh.getB(), bc2) != null ||
                GMath.intersectionLineSegments2D(c, ca1, mesh.getC(), ca2) != null;
    }
    
    private static NavTri[] getQuad(float x0, float y0, float x1, float y1) {
        NavTri[] tris = new NavTri[] {
            new NavTri(new Vector2(x0, y0), new Vector2(x1, y0), new Vector2(x1, y1)),
            new NavTri(new Vector2(x0, y0), new Vector2(x1, y1), new Vector2(x0, y1))
        };
        NavTri.setNeighbours(tris[0], tris[1]);
        return tris;
    }
}
