package com.quew8.gutils.pathfinding;

import com.quew8.gmath.GMath;
import com.quew8.gmath.Vector2;
import com.quew8.gutils.collections.Bag;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Quew8
 */
public class FunnelAlgorithm {
    public List<NavTri> path;
    public ArrayList<Vector2> smoothPath = new ArrayList<Vector2>();
    //public Bag<NavTri.Portal> portals = new Bag<NavTri.Portal>(NavTri.Portal.class);
    public ArrayDeque<Vector2> leftFunnel = new ArrayDeque<Vector2>();
    public ArrayDeque<Vector2> rightFunnel = new ArrayDeque<Vector2>();
    //public NavTri.Portal portal;
    public Vector2 apex;
    public int restartPoint = 0;
    public int currentPortal = 1;
    
    public FunnelAlgorithm(List<NavTri> path) {
        this.path = path;
        NavTri.Portal portal = path.get(0).getPortal(path.get(1));
        this.apex = portal.apex;
        leftFunnel.addLast(portal.left);
        rightFunnel.addLast(portal.right);
        smoothPath.add(apex);
        //portals.add(portal);
    }
    
    public boolean step() {
        if(currentPortal == -1) {
            return true;
        } else if(currentPortal < path.size() - 1) {
            System.out.println("Iteration " + currentPortal);
            NavTri.Portal portal = path.get(currentPortal).getPortal(path.get(currentPortal + 1));
            leftFunnel.addLast(portal.left);
            rightFunnel.addLast(portal.right);
            /*Vector2 newLeftDir = new Vector2(apex, portal.left);
            Vector2 newRightDir = new Vector2(apex, portal.right);
            if(GMath.wedge(leftDir, newLeftDir) < 0) {
                System.out.println("    New Left To Right of Left" + " (" + GMath.wedge(leftDir, newLeftDir) + ")");
                if(GMath.wedge(rightDir, newLeftDir) > 0) {
                    System.out.println("    New Left To Left of Right" + " (" + GMath.wedge(rightDir, newLeftDir) + ")");
                    left = portal.left;
                    leftDir = newLeftDir;
                } else {
                    System.out.println("    New Left To Right of Right" + " (" + GMath.wedge(rightDir, newLeftDir) + ")");
                    for(int i = restartPoint; i < portals.size(); i++) {
                        System.out.println("      Testing Portal " + i + ": " + portals.get(i));
                        if(portals.get(i).apex.equals(right)) {
                            System.out.println("      New Apex at " + portals.get(i).apex);
                            apex = portals.get(i).apex;
                            left = portals.get(i).left;
                            right = portals.get(i).right;
                            leftDir = new Vector2(apex, left);
                            rightDir = new Vector2(apex, right);
                            smoothPath.add(apex);
                            restartPoint = i;
                            break;
                        } else if(i == portals.size() - 1) {
                            throw new RuntimeException("Give up at " + i + " " + restartPoint + " " + portals.size());
                        }
                    }
                }
            } else {
                System.out.println("    New Left To Left of Left" + " (" + GMath.wedge(leftDir, newLeftDir) + ")");
            }
            if(GMath.wedge(rightDir, newRightDir) > 0) {
                System.out.println("    New Right To Left of Right" + " (" + GMath.wedge(rightDir, newRightDir) + ")");
                if(GMath.wedge(leftDir, newRightDir) < 0) {
                    System.out.println("    New Right To Right of Left" + " (" + GMath.wedge(leftDir, newRightDir) + ")");
                    right = portal.right;
                    rightDir = newRightDir;
                } else {
                    System.out.println("    New Right To Left of Left" + " (" + GMath.wedge(leftDir, newRightDir) + ")");
                    for(int i = restartPoint; i < portals.size(); i++) {
                        if(portals.get(i).apex.equals(left)) {
                            System.out.println("      New Apex at " + portals.get(i).apex);
                            apex = portals.get(i).apex;
                            left = portals.get(i).left;
                            right = portals.get(i).right;
                            leftDir = new Vector2(apex, left);
                            rightDir = new Vector2(apex, right);
                            smoothPath.add(apex);
                            restartPoint = i;
                            break;
                        } else if(i == portals.size() - 1) {
                            throw new RuntimeException();
                        }
                    }
                }
            } else {
                System.out.println("    New Right To Right of Right" + " (" + GMath.wedge(rightDir, newRightDir) + ")");
            }
            portals.add(portal);*/
            currentPortal++;
            return false;
        } else {
            NavTri.Portal lastPortal = path.get(path.size() - 1).getPortal(path.get(path.size() - 2));
            smoothPath.add(lastPortal.apex);
            currentPortal = -1;
            return true;
        }
    }
}
