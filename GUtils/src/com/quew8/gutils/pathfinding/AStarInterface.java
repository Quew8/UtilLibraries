package com.quew8.gutils.pathfinding;

import java.util.List;

/**
 *
 * @author Quew8
 * @param <T>
 */
public interface AStarInterface<T> {
    public void addNeighbours(T t, List<T> to);
    public float getDistance(T t1, T t2);
    public float getHeuristic(T t1, T end);
}
