package com.quew8.gutils.func.comparators;

import com.quew8.gutils.func.AbstractDualOperandFunction;
import com.quew8.gutils.func.Function;
import java.util.Comparator;

/**
 *
 * @author Quew8
 */
public abstract class ComparatorFunction<T, S> extends AbstractDualOperandFunction<Boolean, T, S> {
    protected final Comparator<T> comparator;
    
    public ComparatorFunction(Comparator<T> comparator, Function<T, S> function1, Function<T, S> function2) {
        super(function1, function2);
        this.comparator = comparator;
    }

    public int compare(T o1, T o2) {
        return comparator.compare(o1, o2);
    }
}
