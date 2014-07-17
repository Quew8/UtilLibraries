package com.quew8.gutils.services;

import com.quew8.gutils.debug.DebugLogger;
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
    
    @SuppressWarnings("unchecked")
    public ServiceImplLoader(Class<T> servClazz, ClassLoader classLoader, T... loadedImpls) {
        this.servClazz = servClazz;
        this.loadedImpls = loadedImpls;
        this.loader = ServiceLoader.load(servClazz, classLoader);
    }
    
    @SuppressWarnings("unchecked")
    public ServiceImplLoader(Class<T> servClazz, URL[] urls, T... loadedImpls) {
        this(servClazz, new URLClassLoader(urls), loadedImpls);
        if(urls.length == 0) {
            DebugLogger.v(SERVICES_LOG, "No URLs passed to URLClassLoader");
        } else {
            for(int i = 0; i < urls.length; i++) {
                DebugLogger.v(SERVICES_LOG, "URL passed to URLClassLoader[" + i + "] = " + urls[i]);
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    public ServiceImplLoader(Class<T> servClazz, T... loadedImpls) {
        this(servClazz, (ClassLoader) null, loadedImpls);
    }
    
    public T getImplementation() throws NoSuitableImplementationException {
        T topImpl = null;
        ArrayList<T> allImpls = getAllImplementations();
        for(T t: allImpls) {
            DebugLogger.v(SERVICES_LOG, "Looking At: " + t.getClass());
            DebugLogger.v(SERVICES_LOG, "    Is Applicable: " + t.isApplicable());
            DebugLogger.v(SERVICES_LOG, "    Precedence: " + t.getPrecedence());
            if(
                    t.isApplicable() && 
                    (
                        (topImpl == null || topImpl.getPrecedence() == -1 ) || 
                        (t.getPrecedence() < topImpl.getPrecedence() && t.getPrecedence() != -1)
                    )
                    ) {
                topImpl = t;
                DebugLogger.v(SERVICES_LOG, "    Setting As Top Implementation");
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
        DebugLogger.v(SERVICES_LOG, "Loader: " + loader.toString());
        for(T t: loader) {
            DebugLogger.v(SERVICES_LOG, "Loader Found: " + t.getClass());
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
