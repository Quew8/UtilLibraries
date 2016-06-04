package com.quew8.gutils.bufferreading;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Quew8
 */
public abstract class BufferReader {
    private static final String QUANTIFIER_REGEX = "([-+]?[-+\\d]+)(;([-+]?[\\d]+))?";
    
    protected abstract String read(ByteBuffer bb);
    protected abstract int getRequiredBytes();
    
    public static String readWith(FloatBuffer fb, BufferReader with) {
        ByteBuffer bb = ByteBuffer.allocate(fb.capacity() * 4);
        for(int i = 0; i < fb.capacity(); i++) {
            bb.asFloatBuffer().put(i, fb.get(i));
        }
        bb.position(fb.position() * 4);
        bb.limit(fb.limit() * 4);
        return readWith(bb, with);
    }
    
    public static String readWith(IntBuffer ib, BufferReader with) {
        ByteBuffer bb = ByteBuffer.allocate(ib.capacity() * 4);
        for(int i = 0; i < ib.capacity(); i++) {
            bb.asIntBuffer().put(i, ib.get(i));
        }
        bb.position(ib.position() * 4);
        bb.limit(ib.limit() * 4);
        return readWith(bb, with);
    }
    
    public static String readWith(ByteBuffer bb, BufferReader with) {
        ByteBuffer copy = bb.asReadOnlyBuffer();
        return with.read(copy);
    }
    
    public static BufferReader parse(String input, BufferReader... extras) {
        return parse(new Input(input, 0, input.length()), extras);
    }
    
    private static BufferReader parse(Input input, BufferReader[] extras) {
//        System.out.println("Parsing: \"" + input.s.substring(input.offset, input.offset + input.length) + "\"");
//        int lastIndex = input.offset;
        ArrayList<BufferReader> expression = new ArrayList<BufferReader>();
        do {
            switch(input.charAt()) {
                case '{': {
                    expression.add(parseInner(input, extras));
                    break;
                }
                case '[': {
                    if(expression.isEmpty()) {
                        throw new ExpressionParseException("Quantifier Without Expression");
                    }
                    Input inner = getInnerSimple(input, ']');
//                    System.out.println("Inner: " + inner.s.substring(inner.offset, inner.offset + inner.length));
                    BufferReader gap = null;
                    if(inner.charAt() == '{') {
                        gap = parseInner(inner, extras);
                        inner.advance();
                    }
                    String remaining = inner.getRemaining();
                    Matcher m = Pattern.compile(QUANTIFIER_REGEX).matcher(remaining);
                    if(!m.matches()) {
                        throw new ExpressionParseException("Ill Formed Quantifier: \"" + remaining + "\"");
                    }
                    int max = -1;
                    String maxString = m.group(3);
                    if(maxString != null) {
                        max = parseInt(maxString);
                    }
                    int n = parseInt(m.group(1));
                    BufferReader last = expression.get(expression.size() - 1);
                    expression.set(expression.size() - 1, new BufferReaderRepeater(last, n, max, gap));
                    break;
                }
                case '\'': {
                    expression.add(new ConstantBufferReader(getInnerSimple(input, '\'').getRemaining()));
                    break;
                }
                case '?': {
                    if(Character.isDigit(input.sampleNext())) {
                        String inner = "";
                        while(input.hasNext() && Character.isDigit(input.sampleNext())) {
                            input.advance();
                            inner += input.charAt();
                        }
                        int extraIndex = parseInt(inner);
                        if(extraIndex >= extras.length) {
                            throw new ExpressionParseException("Not enough extras for index " + extraIndex);
                        }
                        expression.add(extras[extraIndex]);
                    } else {
                        input.advance();
                        String inner = input.getNextN(4);
                        try {
                            expression.add(PropertyBufferReader.get(inner));
                        } catch(IllegalArgumentException ex) {
                            throw new ExpressionParseException("Unknown Property: \"" + inner + "\"");
                        }
                    }
                    break;
                }
                case 'B':
                case 'b': {
                    expression.add(PrimitiveBufferReader.BYTE);
                    break;
                }
                case 'S':
                case 's': {
                    expression.add(PrimitiveBufferReader.SHORT);
                    break;
                }
                case 'C':
                case 'c': {
                    expression.add(PrimitiveBufferReader.CHAR);
                    break;
                }
                case 'I':
                case 'i': {
                    expression.add(PrimitiveBufferReader.INT);
                    break;
                }
                case 'F':
                case 'f': {
                    expression.add(PrimitiveBufferReader.FLOAT);
                    break;
                }
                case 'L':
                case 'l': {
                    expression.add(PrimitiveBufferReader.LONG);
                    break;
                }
                case 'D':
                case 'd': {
                    expression.add(PrimitiveBufferReader.DOUBLE);
                    break;
                }
                case ',': {
                    expression.add(ConstantBufferReader.COMMA);
                    break;
                }
                case ' ': {
                    expression.add(ConstantBufferReader.SPACE);
                    break;
                }
                default:
                    throw new ExpressionParseException("Encountered Unknown Character: \"" + input.charAt() + "\"");
            }
//            if(lastIndex != -1) {
//                System.out.println("Matched Token \"" + input.s.substring(lastIndex, input.index()+1) + "\"");
//            }
//            lastIndex = input.index() + 1;
        } while(input.advanceIfCan());
        return new BufferReaderSequence(expression.toArray(new BufferReader[expression.size()]));
    }
    
    private static BufferReader parseInner(Input input, BufferReader[] extras) {
        int bLevel = 1;
        int sequenceStart = input.index() + 1;
        do {
            input.advance();
            if(input.charAt() == '}') {
                bLevel--;
            } else if(input.charAt() == '{') {
                bLevel++;
            }
        } while(bLevel != 0);
        int sequenceEnd = input.index();
        return parse(input.slice(sequenceStart, sequenceEnd), extras);
    }
    
    private static Input getInnerSimple(Input input, char endChar) {
        int start = input.index() + 1;
        do {
            input.advance();
            if(input.charAt() == endChar) {
                break;
            }
        } while(true);
        return input.slice(start, input.index());
    }
    
    private static int parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch(NumberFormatException ex) {
            throw new ExpressionParseException("Expected integer, found \"" + s + "\"");
        }
    }
    
    private static class Input {
        private final String s;
        private final int offset, length;
        private int index;

        Input(String s, int offset, int length) {
            this.s = s;
            this.offset = offset;
            this.length = length;
            this.index = offset;
        }
        
        public char charAt() {
            return s.charAt(index);
        }
        
        public char sampleNext() {
            if(!hasNext()) {
                throw new ExpressionParseException("Reached End of Expression Whilst Parsing");
            }
            return s.charAt(index + 1);
        }
        
        public int index() {
            return index;
        }
        
        public boolean advanceIfCan() {
            if(hasNext()) {
                index++;
                return true;
            } else {
                return false;
            }
        }
        
        public void advance() {
            if(!hasNext()) {
                throw new ExpressionParseException("Reached End of Expression Whilst Parsing");
            }
            index++;
        }
        
        public boolean hasNext() {
            return index < offset + length - 1;
        }
        
        public Input slice(int start, int end) {
            return new Input(s, start, end - start);
        }
        
        public String getNextN(int n) {
            int temp = index;
            index = index + n - 1;
            if(index > offset + length) {
                throw new ExpressionParseException("Reached End of Expression Whilst Parsing");
            }
            return s.substring(temp, index + 1);
        }
        
        public String getRemaining() {
            int temp = index;
            index = offset + length;
            return s.substring(temp, index);
        }
    }
}
