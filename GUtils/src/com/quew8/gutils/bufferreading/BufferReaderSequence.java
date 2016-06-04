package com.quew8.gutils.bufferreading;

import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public class BufferReaderSequence extends BufferReader {
    final BufferReader[] sequence;
    final int required;
    
    public BufferReaderSequence(BufferReader[] sequence) {
        this.sequence = sequence;
        if(sequence.length == 0) {
            throw new IllegalArgumentException();
        }
        int tRequired = 0;
        for(int i = 0; i < sequence.length; i++) {
            if(sequence[i].getRequiredBytes() < 0) {
                tRequired = -1;
                break;
            }
            tRequired += sequence[i].getRequiredBytes();
        }
        this.required = tRequired;
    }

    @Override
    public String read(ByteBuffer bb) {
        String s = sequence[0].read(bb);
        for(int i = 1; i < sequence.length; i++) {
            s += sequence[i].read(bb);
        }
        return s;
    }

    @Override
    public int getRequiredBytes() {
        return required;
    }
    
}
