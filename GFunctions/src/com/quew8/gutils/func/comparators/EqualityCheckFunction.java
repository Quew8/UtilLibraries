package com.quew8.gutils.func.comparators;

import com.quew8.gutils.func.Function;
import java.util.Comparator;

/**
 *
 * @author Quew8
 */
public class EqualityCheckFunction<T, S> extends ComparatorFunction<T, S> {
    
    public EqualityCheckFunction(Comparator<T> comparator, Function<T, S> function1, Function<T, S> function2) {
        super(comparator, function1, function2);
    }
    
    @Override
    public Boolean f(S s) {
        return compare(function1.f(s), function2.f(s)) == 0;
    }
}
