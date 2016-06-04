package com.quew8.gutils.pathfinding;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Quew8
 * @param <T>
 */
public class AStarMap<T> {
    private final HashMap<T, AStarNode> map;
    //private final AStarNode[] nodes;
    private final AStarInterface<T> inter;
    
    public AStarMap(T[] nodes, AStarInterface<T> inter) {
        this.map = new HashMap<T, AStarNode>();
        for(int i = 0; i < nodes.length; i++) {
            map.put(nodes[i], new AStarNode(nodes[i]));
        }
        this.inter = inter;
    }
    
    public List<T> findPath(T tStart, T tEnd) {
        ArrayList<AStarNode> openSet = new ArrayList<AStarNode>();
        ArrayList<AStarNode> closedSet = new ArrayList<AStarNode>();
        ArrayList<T> neighbours = new ArrayList<T>();
        
        for(AStarNode n: map.values()) {
            n.cameFrom = null;
            n.fScore = Float.MAX_VALUE;
            n.gScore = Float.MAX_VALUE;
        }
        AStarNode<T> start = map.get(tStart);
        AStarNode<T> end = map.get(tEnd);
        start.gScore = 0;
        start.fScore = inter.getHeuristic(tStart, tEnd);
        openSet.add(start);
        AStarNode<T> current;
        while(!openSet.isEmpty()) {
            current = openSet.get(0);
            for(int i = 1; i < openSet.size(); i++) {
                if(openSet.get(i).fScore < current.fScore) {
                    current = openSet.get(i);
                }
            }
            if(current == end) {
                ArrayDeque<T> path = new ArrayDeque<T>();
                path.add(current.data);
                while(current.cameFrom != null) {
                    path.addFirst(current.cameFrom.data);
                    current = current.cameFrom;
                }
                ArrayList<T> forwardPath = new ArrayList<T>();
                path.stream().forEach((t) -> forwardPath.add(t));
                return forwardPath;
            }
            openSet.remove(current);
            closedSet.add(current);
            neighbours.clear();
            inter.addNeighbours(current.data, neighbours);
            for(T t: neighbours) {
                AStarNode n = map.get(t);
                if(closedSet.contains(n)) {
                    continue;
                }
                float tempGScore = current.gScore + inter.getDistance(current.data, t);
                if(!openSet.contains(n) || tempGScore < n.gScore) {
                    n.gScore = tempGScore;
                    n.fScore = tempGScore + inter.getHeuristic(t, tEnd);
                    n.cameFrom = current;
                    if(!openSet.contains(n)) {
                        openSet.add(n);
                    }
                }
            }
        }
        return null;
    }
}
