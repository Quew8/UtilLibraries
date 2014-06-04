package com.quew8.gutils.content;

import java.io.InputStream;
import java.util.HashMap;

/**
 *
 * @author Quew8
 * @param <T>
 */
public interface ContentReader<T> {
    public T read(InputStream in, HashMap<String, String> params);
}
