package com.quew8.gutils;

/**
 *
 * @author Quew8
 * @param <T>
 * @param <S>
 */
public abstract class ResourceLocator<T, S> {
    private final ResourceLocator<T, S> subLocator;

    public ResourceLocator(ResourceLocator<T, S> subLocator) {
        this.subLocator = subLocator;
    }

    public ResourceLocator() {
        this(null);
    }
    
    public S locate(T t) {
        S s = localLocate(t);
        return 
                s == null ? 
                (
                subLocator == null ? 
                null :
                subLocator.locate(t)
                ): 
                s;
    }
    
    public abstract S localLocate(T t);
}
