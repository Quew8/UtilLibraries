package com.quew8.gutils.pathfinding;

import com.quew8.gmath.GMath;
import com.quew8.gmath.Vector2;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Quew8
 */
public class SlidingAlgorithm {
    public List<NavTri> path;
    public Vector2 start, end;
    public ArrayList<Vector2> smoothPath;
    public ArrayList<Integer> smoothPathIndices;
    public Vector2 currentStart;
    public int currentStartIndex;
    public int currentPortal;
    public Vector2 currentEnd;
    public int currentEndIndex;
    
    public int currentStep = 1;
    
    public SlidingAlgorithm(List<NavTri> path) {
        this.path = path;
        this.start = path.get(0).getPortal(path.get(1)).apex;
        this.end = path.get(path.size() - 1).getPortal(path.get(path.size() - 2)).apex;
        this.currentStart = start;
        this.currentStartIndex = 0;
        this.currentPortal = 0;
        this.currentEnd = start;
        this.currentEndIndex = 0;
        this.smoothPath = new ArrayList<Vector2>() {{add(start);}};
        this.smoothPathIndices = new ArrayList<Integer>() {{add(-1);}};
    }
    
    public boolean step() {
        System.out.println("Iteration: " + currentPortal + ", " + currentStep);
        if(currentStep == -1) {
            System.out.println("    Finished");
            return true;
        } else if(currentPortal == -1) {
            if(currentStep == smoothPath.size() - 1) {
                currentStep = -1;
                return true;
            }
            Vector2 toThis = new Vector2(smoothPath.get(currentStep - 1), smoothPath.get(currentStep));
            Vector2 toNext = new Vector2(smoothPath.get(currentStep), smoothPath.get(currentStep + 1));
            NavTri.Portal portal = path.get(smoothPathIndices.get(currentStep)).getPortal(path.get(smoothPathIndices.get(currentStep)+1));
            if(GMath.wedge(toThis, toNext) > 0) {
                smoothPath.set(currentStep, portal.left);
            } else {
                smoothPath.set(currentStep, portal.right);
            }
            currentStep++;
            return false;
        } else if(currentPortal <= path.size() - 1) {
            Vector2 lastEnd = currentEnd;
            int lastEndIndex = currentEndIndex;
            currentEndIndex = currentPortal;
            if(currentPortal < path.size() - 1) {
                System.out.println("    Getting Portal");
                currentEnd = path.get(currentPortal).getPortal(path.get(currentPortal + 1)).getCentre();
            } else {
                System.out.println("    Getting Final End");
                currentEnd = end;
                currentPortal = -1;
            }
            System.out.println("    Current Start " + currentStartIndex);
            System.out.println("    Current End " + currentEndIndex);
            System.out.println("    Last End " + lastEndIndex);
            Vector2 dir = new Vector2(currentStart, currentEnd);
            for(int i = currentStartIndex; i < currentEndIndex - 1; i++) {
                NavTri.Portal portal = path.get(i).getPortal(path.get(i + 1));
                if(GMath.wedge(portal.leftDir, dir) > 0 || GMath.wedge(portal.rightDir, dir) < 0) {
                    System.out.println("    Not Inside setting start to " + lastEnd + ", setting currentPortal to " + (lastEndIndex+1));
                    System.out.println("      Adding " + lastEndIndex);
                    smoothPath.add(lastEnd);
                    smoothPathIndices.add(lastEndIndex);
                    currentStart = lastEnd;
                    currentStartIndex = lastEndIndex;
                    currentPortal = currentEndIndex + 1;
                    return false;
                }
            }
            if(currentPortal == -1) {
                System.out.println("    Ending Algorithm");
                smoothPath.add(end);
                smoothPathIndices.add(-1);
                return true;
            }
            currentPortal++;
            return false;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
