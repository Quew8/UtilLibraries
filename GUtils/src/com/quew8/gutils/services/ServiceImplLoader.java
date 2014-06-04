package com.quew8.gutils.services;

import com.quew8.gutils.debug.DebugLogger;
import com.quew8.gutils.debug.LogLevel;
import com.quew8.gutils.debug.LogOutput;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.ServiceLoader;

/**
 *
 * @author Quew8
 * @param <T>
 */
public class ServiceImplLoader<T extends ServiceImpl> {
    private final ServiceLoader<T> loader;
    private final T[] loadedImpls;
    private final Class<T> servClazz;
    
    public static final String SERVICES_LOG = "SERVICES";
    static {
        DebugLogger.registerLog(SERVICES_LOG, LogLevel.VERBOSE, LogOutput.FILE);
    }
    
    public ServiceImplLoader(Class<T> servClazz, ClassLoader classLoader, T... loadedImpls) {
        this.servClazz = servClazz;
        this.loadedImpls = loadedImpls;
        this.loader = ServiceLoader.load(servClazz, classLoader);
    }
    
    public ServiceImplLoader(Class<T> servClazz, URL[] urls, T... loadedImpls) {
        this(servClazz, new URLClassLoader(urls), loadedImpls);
    }
    
    public ServiceImplLoader(Class<T> servClazz, T... loadedImpls) {
        this(servClazz, (ClassLoader) null, loadedImpls);
    }
    
    public T getImplementation() throws NoSuitableImplementationException {
        T topImpl = null;
        ArrayList<T> allImpls = getAllImplementations();
        for(T t: allImpls) {
            DebugLogger.log(SERVICES_LOG, "Looking At: " + t.getClass());
            if(
                    t.isApplicable() && 
                    (
                        (topImpl == null || topImpl.getPrecedence() == -1 ) || 
                        (t.getPrecedence() < topImpl.getPrecedence() && t.getPrecedence() != -1)
                    )
                    ) {
                topImpl = t;
            }
        }
        if(topImpl != null) {
            return topImpl;
        }
        throw new NoSuitableImplementationException(servClazz, loader);
    }
    
    public T getImplementationNoThrow() {
        try {
            return getImplementation();
        } catch(NoSuitableImplementationException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public T[] getAllImplementations(T[] dest) {
        return getAllImplementations().toArray(dest);
    }
    
    public ArrayList<T> getAllImplementations() {
        ArrayList<T> impls = new ArrayList<T>();
        for(T t: loader) {
            DebugLogger.log(SERVICES_LOG, "Loader Found: " + t.getClass());
            if(t.isApplicable()) {
                impls.add(t);
            }
        }
        for(T t: loadedImpls) {
            if(t.isApplicable()) {
                impls.add(t);
            }
        }
        return impls;
    }
}
