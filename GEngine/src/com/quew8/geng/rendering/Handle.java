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

    Handle(int start, int end) {
        this.start = start;
        this.end = end;
    }

    protected int getStart() {
        return start;
    }

    protected int getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "Handle{" + "start=" + start + ", end=" + end + '}';
    }

    public static Vector[] removeDuplicates(Vector[] vectors) {
        return ArrayUtils.removeDuplicates(vectors, (Vector o1, Vector o2) -> o1.equals(o2) ? 0 : 1);
    }
}
