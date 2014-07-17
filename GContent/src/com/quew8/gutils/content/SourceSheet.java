package com.quew8.gutils.content;

import com.quew8.gutils.ResourceLoader;

/**
 *
 * @author Quew8
 */
public class SourceSheet {
    private final Class<?> idClass;
    private final Class<?> readerClass;
    private final Source[] sources;

    public SourceSheet(ResourceLoader loader, Class<?> idClass, Class<?> readerClass, Source[] sources) {
        this.idClass = idClass;
        this.readerClass = readerClass;
        this.sources = sources;
        for(int i = 0; i < sources.length; i++) {
            sources[i].setLoader(loader);
        }
    }
    
    public Class<?> getIdClass() {
        return idClass;
    }
    
    public Class<?> getReaderClass() {
        return readerClass;
    }
    
    @SuppressWarnings("unchecked")
    public <T> Class<? extends ContentReader<T>> getReaderClass(Class<T> clazz) {
        return (Class<? extends ContentReader<T>>) readerClass;
    }
    
    public <T> ContentReader<T> getReader(Class<T> clazz) {
        try {
            return getReaderClass(clazz).newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        } 
    }
    
    public Source[] getSources() {
        return sources;
    }

}
