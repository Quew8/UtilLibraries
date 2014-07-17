package com.quew8.gutils.content;

/**
 *
 * @author Quew8
 * @param <T>
 */
public interface ContentReader<T> {
    public T read(com.quew8.gutils.content.Source in);
}
