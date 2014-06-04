package com.quew8.geng.rendering;

import com.quew8.gmath.Vector;
import com.quew8.gutils.ArrayUtils;
import java.util.Comparator;

/**
 *
 * @author Quew8
 */
abstract class Handle {
    private final int start;
    private final int end;

    public Handle(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public static Vector[] removeDuplicates(Vector[] vectors) {
        return ArrayUtils.removeDuplicates(vectors, new Comparator<Vector>() {
            @Override
            public int compare(Vector o1, Vector o2) {
                return o1.equals(o2) ? 0 : 1;
            }
        });
    }
}
