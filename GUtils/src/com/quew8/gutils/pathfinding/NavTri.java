package com.quew8.gutils.pathfinding;

import com.quew8.gmath.GMath;
import com.quew8.gmath.Vector2;
import java.util.Objects;

/**
 *
 * @author Quew8
 */
public class NavTri {
    private final Vector2 a, b, c;
    private NavTri n1, n2, n3;

    public NavTri(Vector2 a, Vector2 b, Vector2 c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Vector2 getA() {
        return a;
    }

    public Vector2 getB() {
        return b;
    }

    public Vector2 getC() {
        return c;
    }

    public NavTri getN1() {
        return n1;
    }

    public NavTri getN2() {
        return n2;
    }

    public NavTri getN3() {
        return n3;
    }

    /**
     * 
     * 
     * @param neighbour
     * @return 
     */
    public Portal getPortal(NavTri neighbour) {
        Vector2 left, right, apex;
        if(neighbour.sharesEdge(a, b)) {
            left = a;
            right = b;
            apex = c;
        } else if(neighbour.sharesEdge(b, c)) {
            left = b;
            right = c;
            apex = a;
        } else if(neighbour.sharesEdge(c, a)) {
            left = c;
            right = a;
            apex = b;
        } else {
            throw new IllegalArgumentException("Not Neighbour");
        }
        if(GMath.wedge(new Vector2(apex, left), new Vector2(apex, right)) > 0) {
            return new Portal(right, left, apex);
        } else {
            return new Portal(left, right, apex);
        }
    }

    public Vector2 getCommonPoint(NavTri n2, NavTri n3) {
        //System.out.println(this + "\n" + n2 + "\n" + n3);
        if(this.a.equals(n2.a) || this.a.equals(n2.b) || this.a.equals(n2.c)) {
            if(this.a.equals(n3.a) || this.a.equals(n3.b) || this.a.equals(n3.c)) {
                return this.a;
            }
        }
        if(this.b.equals(n2.a) || this.b.equals(n2.b) || this.b.equals(n2.c)) {
            if(this.b.equals(n3.a) || this.b.equals(n3.b) || this.b.equals(n3.c)) {
                return this.b;
            }
        }
        if(this.c.equals(n2.a) || this.c.equals(n2.b) || this.c.equals(n2.c)) {
            if(this.c.equals(n3.a) || this.c.equals(n3.b) || this.c.equals(n3.c)) {
                return this.c;
            }
        }
        return null;
    } 
    
    public boolean sharesEdge(Vector2 a, Vector2 b) {
        if(this.a.equals(a)) {
            if(this.b.equals(b) || this.c.equals(b)) {
                return true;
            }
        } else if(this.a.equals(b)) {
            if(this.b.equals(a) || this.c.equals(a)) {
                return true;
            }
        } else if(this.b.equals(a)) {
            if(this.a.equals(b) || this.c.equals(b)) {
                return true;
            }
        } else if(this.b.equals(b)) {
            if(this.a.equals(a) || this.c.equals(a)) {
                return true;
            }
        } else if(this.c.equals(a)) {
            if(this.a.equals(b) || this.b.equals(b)) {
                return true;
            }
        } else if(this.c.equals(b)) {
            if(this.a.equals(a) || this.b.equals(a)) {
                return true;
            }
        }
        return false;
    }

    public NavTri getNeighbour(Vector2 a, Vector2 b) {
        if(n1 != null && n1.sharesEdge(a, b)) {
            return n1;
        } else if(n2 != null && n2.sharesEdge(a, b)) {
            return n2;
        } else if(n3 != null && n3.sharesEdge(a, b)) {
            return n3;
        } else {
            return null;
        }
    }
    
    private void addNeighbour(NavTri n) {
        if(n1 == null) {
            n1 = n;
        } else if(n2 == null) {
            n2 = n;
        } else if(n3 == null) {
            n3 = n;
        } else {
            throw new IllegalStateException();
        }
    }

    private void removeNeighbour(NavTri n) {
        if(n.equals(n1)) {
            if(n3 != null) {
                n1 = n3;
                n3 = null;
            } else if(n2 != null) {
                n1 = n2;
                n2 = null;
            } else {
                n1 = null;
            }
        } else if(n.equals(n2)) {
            if(n3 != null) {
                n2 = n3;
                n3 = null;
            } else {
                n2 = null;
            }
        } else if(n.equals(n3)) {
            n3 = null;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void delete() {
        if(n1 != null) {
            n1.removeNeighbour(this);
            if(n2 != null) {
                n2.removeNeighbour(this);
                if(n3 != null) {
                    n3.removeNeighbour(this);
                }
            }
        }
    }

    public Vector2 getCentre() {
        Vector2 centre = new Vector2().setXY(a);
        centre.add(centre, b);
        centre.add(centre, c);
        return centre.scale(centre, 1f / 3f);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.a);
        hash = 97 * hash + Objects.hashCode(this.b);
        hash = 97 * hash + Objects.hashCode(this.c);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        final NavTri other = (NavTri) obj;
        if(!Objects.equals(this.a, other.a)) {
            return false;
        }
        if(!Objects.equals(this.b, other.b)) {
            return false;
        }
        return Objects.equals(this.c, other.c);
    }

    @Override
    public String toString() {
        return "NavTri{" + "a=" + a + ", b=" + b + ", c=" + c + '}';
    }
    
    public static void setNeighbours(NavTri n1, NavTri n2) {
        n1.addNeighbour(n2);
        n2.addNeighbour(n1);
    }
    
    public static void setNotNeighbours(NavTri n1, NavTri n2) {
        n1.removeNeighbour(n2);
        n2.removeNeighbour(n1);
    }
    
    public static class Portal {
        public final Vector2 left, right, apex;
        public final Vector2 leftDir, rightDir;
        
        private Portal(Vector2 left, Vector2 right, Vector2 apex) {
            this.left = left;
            this.right = right;
            this.apex = apex;
            this.leftDir = new Vector2(apex, left);
            this.rightDir = new Vector2(apex, right);
        }

        public Vector2 getCentre() {
            Vector2 centre = new Vector2().setXY(left);
            centre.add(centre, right);
            return centre.scale(centre, 0.5f);
        }
        
        @Override
        public String toString() {
            return "Portal{" + "left=" + left + ", right=" + right + ", apex=" + apex + '}';
        }
    }
}
