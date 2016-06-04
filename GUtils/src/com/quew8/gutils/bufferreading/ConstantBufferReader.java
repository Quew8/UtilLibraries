package com.quew8.gutils.bufferreading;

import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public class ConstantBufferReader extends NoReadBufferReader {
    public static final ConstantBufferReader COMMA = new ConstantBufferReader(", "),
            SPACE = new ConstantBufferReader(" ");
    private final String constant;

    public ConstantBufferReader(String constant) {
        this.constant = constant;
    }

    @Override
    public String read(ByteBuffer bb) {
        return constant;
    }
    
}
