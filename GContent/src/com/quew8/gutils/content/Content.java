package com.quew8.gutils.content;

import java.util.HashMap;

/**
 *
 * @author Quew8
 * @param <T>
 */
public class Content<T> {
    private final Class<T> clazz;
    private final HashMap<Integer, T> content = new HashMap<Integer, T>();

    public Content(Class<T> clazz) {
        this.clazz = clazz;
    }
    
    public void loadSources(SourceSheet sourceSheet) {
        ContentReader<T> reader = sourceSheet.getReader(clazz);
        for(com.quew8.gutils.content.Source s: sourceSheet.getSources()) {
            T t = reader.read(s);
            if(t == null) {
                throw new RuntimeException("Loading Null Resource: " + s.getSource());
            }
            content.put(
                    getId(sourceSheet.getIdClass(), s), 
                    t
                    );
        }
    }
    
    public T get(int id) {
        return content.get(id);
    }
    
    public T[] putAll(T[] in) {
        return content.values().toArray(in);
    }
    
    private static int getId(Class<?> idClass, com.quew8.gutils.content.Source source) {
        try {
            return idClass.getField(source.getIdString()).getInt(null);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }
}
