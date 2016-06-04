package com.quew8.gutils.pathfinding;

import java.util.List;

/**
 *
 * @author Quew8
 */
public class NavTriAStarInterface implements AStarInterface<NavTri> {

    @Override
    public void addNeighbours(NavTri n, List<NavTri> to) {
        if(n.getN1() != null) {
            to.add(n.getN1());
            if(n.getN2() != null) {
                to.add(n.getN2());
                if(n.getN3() != null) {
                    to.add(n.getN3());
                }
            }
        }
    }

    @Override
    public float getDistance(NavTri n1, NavTri n2) {
        return n1.getCentre().distance(n2.getCentre());
    }

    @Override
    public float getHeuristic(NavTri t1, NavTri end) {
        return t1.getCentre().distance(end.getCentre());
    }
}
