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
        for(Source s: sourceSheet.getSources()) {
            content.put(
                    getId(sourceSheet.getIdClass(), s), 
                    reader.read(s)
                    );
        }
    }
    
    public T get(int id) {
        return content.get(id);
    }
    
    public T[] putAll(T[] in) {
        return content.values().toArray(in);
    }
    
    private static int getId(Class<?> idClass, Source source) {
        try {
            return idClass.getField(source.getIdString()).getInt(null);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }
}
