package com.quew8.gutils.bufferreading;

import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public class BufferReaderRepeater extends BufferReader {
    private final BufferReader toRepeat, gap;
    private final int n, max;

    public BufferReaderRepeater(BufferReader toRepeat, int n, int max, BufferReader gap) {
        this.toRepeat = toRepeat;
        this.gap = gap;
        if(toRepeat.getRequiredBytes() < 0 || 
                (gap != null && gap.getRequiredBytes() < 0)) {
            throw new IllegalArgumentException("Trying to repeat greedy reader");
        }
        this.n = n;
        this.max = max;
        if(max >= 0 && n > max) {
            throw new IllegalArgumentException("Cannot have a maximum less than the specified repetitions: [" + n + ";" + max + "]");
        }
    }

    public BufferReaderRepeater(BufferReader toRepeat, int n, int max) {
        this(toRepeat, n, max, null);
    }

    @Override
    public String read(ByteBuffer bb) {
        String s = "";
        if(n >= 0) {
            for(int i = 0; i < n; i++) {
                if(i != 0 && gap != null) {
                    s += gap.read(bb);
                }
                s += toRepeat.read(bb);
            }
        } else if(max != 0 && bb.remaining() >= toRepeat.getRequiredBytes()) {
            int i = 0;
            s += toRepeat.read(bb);
            i++;
            int stepSize = toRepeat.getRequiredBytes() + (gap == null ? 0 : gap.getRequiredBytes());
            while(max != i && bb.remaining() >= stepSize) {
                if(gap != null) {
                    s += gap.read(bb);
                }
                s += toRepeat.read(bb);
                i++;
            }
        }
        return s;
    }

    @Override
    public int getRequiredBytes() {
        return n > 0 
                ? (n * toRepeat.getRequiredBytes()) + ((n-1) * gap.getRequiredBytes())
                : -1;
    }
    
}
