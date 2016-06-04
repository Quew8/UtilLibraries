package com.quew8.gutils.bufferreading;

import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public class PropertyBufferReader extends NoReadBufferReader {
    public static final PropertyBufferReader 
            POS = new PropertyBufferReader(BufferProperty.POS, 1),
            LIM = new PropertyBufferReader(BufferProperty.LIM, 1),
            CAP = new PropertyBufferReader(BufferProperty.CAP, 1),
            REM = new PropertyBufferReader(BufferProperty.REM, 1),
            POS2 = new PropertyBufferReader(BufferProperty.POS, 2),
            LIM2 = new PropertyBufferReader(BufferProperty.LIM, 2),
            CAP2 = new PropertyBufferReader(BufferProperty.CAP, 2),
            REM2 = new PropertyBufferReader(BufferProperty.REM, 2),
            POS4 = new PropertyBufferReader(BufferProperty.POS, 4),
            LIM4 = new PropertyBufferReader(BufferProperty.LIM, 4),
            CAP4 = new PropertyBufferReader(BufferProperty.CAP, 4),
            REM4 = new PropertyBufferReader(BufferProperty.REM, 4),
            POS8 = new PropertyBufferReader(BufferProperty.POS, 8),
            LIM8 = new PropertyBufferReader(BufferProperty.LIM, 8),
            CAP8 = new PropertyBufferReader(BufferProperty.CAP, 8),
            REM8 = new PropertyBufferReader(BufferProperty.REM, 8);
    
    private final BufferProperty property;
    private final int stride;
    
    private PropertyBufferReader(BufferProperty property, int stride) {
        this.property = property;
        this.stride = stride;
    }
    
    @Override
    protected String read(ByteBuffer bb) {
        switch(property) {
            case POS: return Integer.toString(bb.position() / stride);
            case LIM: return Integer.toString(bb.limit() / stride);
            case CAP: return Integer.toString(bb.capacity() / stride);
            case REM: return Integer.toString(bb.remaining() / stride);
            default: throw new RuntimeException("IMPOSSIBLE?");
        }
    }
    
    public static PropertyBufferReader get(String type) {
        char stride = Character.toUpperCase(type.charAt(0));
        BufferProperty property = BufferProperty.valueOf(type.substring(1).toUpperCase());
        switch(property) {
            case POS: {
                switch(stride) {
                    case 'B': return POS;
                    case 'S':
                    case 'C': return POS2;
                    case 'I':
                    case 'F': return POS4;
                    case 'L':
                    case 'D': return POS8;
                    default: throw new RuntimeException("IMPOSSIBLE?");
                }
            }
            case LIM: {
                switch(stride) {
                    case 'B': return LIM;
                    case 'S':
                    case 'C': return LIM2;
                    case 'I':
                    case 'F': return LIM4;
                    case 'L':
                    case 'D': return LIM8;
                    default: throw new RuntimeException("IMPOSSIBLE?");
                }
            }
            case CAP: {
                switch(stride) {
                    case 'B': return CAP;
                    case 'S':
                    case 'C': return CAP2;
                    case 'I':
                    case 'F': return CAP4;
                    case 'L':
                    case 'D': return CAP8;
                    default: throw new RuntimeException("IMPOSSIBLE?");
                }
            }
            case REM: {
                switch(stride) {
                    case 'B': return REM;
                    case 'S':
                    case 'C': return REM2;
                    case 'I':
                    case 'F': return REM4;
                    case 'L':
                    case 'D': return REM8;
                    default: throw new RuntimeException("IMPOSSIBLE?");
                }
            }
            default: throw new RuntimeException("IMPOSSIBLE?");
        }
    }
}
