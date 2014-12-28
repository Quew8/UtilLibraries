package com.quew8.gutils.pathfinding;

/**
 *
 * @author Quew8
 */
public class AStarNode<T> {
    protected final T data;
    protected float gScore, fScore;
    protected AStarNode<T> cameFrom;
    
    public AStarNode(T data) {
        this.data = data;
    }
}
