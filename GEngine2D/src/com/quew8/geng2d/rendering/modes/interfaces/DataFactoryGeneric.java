package com.quew8.geng2d.rendering.modes.interfaces;

import com.quew8.geng.rendering.modes.interfaces.DataFactory;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 * @param <T>
 */
public interface DataFactoryGeneric<T> extends DataFactory {
    public void addData(ByteBuffer to, T data);
}
