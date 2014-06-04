package com.quew8.gutils;

import com.quew8.gutils.func.Function;
import com.quew8.gutils.func.Functions;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * 
 * @author Quew8
 */
public class ArrayUtils {
    
    private ArrayUtils() {
        
    }
    
    /**
     * 
     * @param <T>
     * @param <S>
     * @param input
     * @param inOffset
     * @param f
     * @param output
     * @param outOffset
     * @param length
     * @return 
     */
    public static <T, S> T[] performFunction(S[] input, int inOffset, 
            Function<T, S> f, T[] output, int outOffset, int length) {
        
        for(int i = 0; i < length; i++) {
            output[outOffset + i] = f.f(input[inOffset + i]);
        }
        return output;
    }
    
    /**
     * 
     * @param input
     * @param inOffset
     * @param f
     * @param output
     * @param outOffset
     * @param length
     * @return 
     */
    public static float[] performFunction(float[] input, int inOffset, 
            Function<Float, Float> f, float[] output, int outOffset, int length) {
        
        for(int i = 0; i < length; i++) {
            output[outOffset + i] = f.f(input[inOffset + i]);
        }
        return output;
    }
    
    /**
     * 
     * @param input
     * @param inOffset
     * @param f
     * @param output
     * @param outOffset
     * @param length
     * @return 
     */
    public static int[] performFunction(int[] input, int inOffset, 
            Function<Integer, Integer> f, int[] output, int outOffset, int length) {
        
        for(int i = 0; i < length; i++) {
            output[outOffset + i] = f.f(input[inOffset + i]);
        }
        return output;
    }
    
    /**
     * 
     * @param input
     * @param inOffset
     * @param f
     * @param output
     * @param outOffset
     * @param length
     * @return 
     */
    public static byte[] performFunction(byte[] input, int inOffset, 
            Function<Byte, Byte> f, byte[] output, int outOffset, int length) {
        
        for(int i = 0; i < length; i++) {
            output[outOffset + i] = f.f(input[inOffset + i]);
        }
        return output;
    }
    
    /**
     * 
     * @param array
     * @param offset
     * @param length
     * @param f
     * @return 
     */
    public static float[] add(float[] array, int offset, int length, float f) {
        return performFunction(array, offset, Functions.addF(f), array, offset, length);
    }
    
    /**
     * 
     * @param array
     * @param offset
     * @param length
     * @param i
     * @return 
     */
    public static int[] add(int[] array, int offset, int length, int i) {
        return performFunction(array, offset, Functions.addI(i), array, offset, length);
    }
    
    /**
     * 
     * @param array
     * @param i
     * @return 
     */
    public static int[] add(int[] array, int i) {
        return add(array, 0, array.length, i); 
    }
            
    /**
     * 
     * @param array
     * @param offset
     * @param length
     * @param b
     * @return 
     */
    public static byte[] add(byte[] array, int offset, int length, byte b) {
        return performFunction(array, offset, Functions.addB(b), array, offset, length);
    }
    
    /**
     * 
     * @param array
     * @param offset
     * @param length
     * @param f
     * @return 
     */
    public static float[] multiply(float[] array, int offset, int length, float f) {
        return performFunction(array, offset, Functions.multiplyF(f), array, offset, length);
    }
    
    /**
     * 
     * @param array
     * @param offset
     * @param length
     * @param i
     * @return 
     */
    public static int[] multiply(int[] array, int offset, int length, int i) {
        return performFunction(array, offset, Functions.multiplyI(i), array, offset, length);
    }
    
    /**
     * 
     * @param array
     * @param offset
     * @param length
     * @param b
     * @return 
     */
    public static byte[] multiply(byte[] array, int offset, int length, byte b) {
        return performFunction(array, offset, Functions.multiplyB(b), array, offset, length);
    }
    
    /**
     * 
     * @param <T>
     * @param array
     * @param comparator
     * @return 
     */
    public static <T> T[] removeDuplicates(T[] array, Comparator<? super T> comparator) {
        ArrayList<T> list = new ArrayList<>();
        for(int i = 0; i < array.length; i++) {
            if(array[i] != null) {
                for(int j = 0; j < array.length; j++) {
                    if(i != j && array[j] != null && comparator.compare(array[i], array[j]) == 0) {
                        array[j] = null;
                    }
                }
                list.add(array[i]);
            }
        }
        return list.toArray(Arrays.copyOf(array, list.size()));
    }
    
    /**
     * 
     * @param array
     * @return 
     */
    public static Integer[] toIntegerArray(int[] array) {
        Integer[] result = new Integer[array.length];
        for(int i = 0; i < array.length; i++) {
            result[i] = array[i];
        } 
        return result;
    }
    
    /**
     * 
     * @param array
     * @return 
     */
    public static Float[] toFloatArray(float[] array) {
        Float[] result = new Float[array.length];
        for(int i = 0; i < array.length; i++) {
            result[i] = array[i];
        } 
        return result;
    }
    
    /**
     * 
     * @param array
     * @return 
     */
    public static int[] toIntArray(Integer[] array) {
        int[] result = new int[array.length];
        for(int i = 0; i < array.length; i++) {
            result[i] = array[i];
        } 
        return result;
    }
    
    /**
     * 
     * @param array
     * @return 
     */
    public static float[] toFloatArray(Float[] array) {
        float[] result = new float[array.length];
        for(int i = 0; i < array.length; i++) {
            result[i] = array[i];
        } 
        return result;
    }
    
    /**
     * 
     * @param <T>
     * @param array
     * @param from
     * @param length
     * @return 
     */
    public static <T> T[] split(T[] array, int from, int length) {
        T[] result = Arrays.copyOf(array, length);
        System.arraycopy(array, from, result, 0, length);
        return result;
    }
    
    /**
     * 
     * @param <T>
     * @param array
     * @param from
     * @return 
     */
    public static <T> T[] split(T[] array, int from) {
        return split(array, from, array.length - from);
    }
    
    /**
     * 
     * @param <T>
     * @param array
     * @param to
     * @return 
     */
    public static <T> int[] arrayOfLengthsTo(T[][] array, int to) {
        int[] result = new int[to];
        for(int i = 0; i < to; i++) {
            result[i] = array[i].length;
        }
        return result;
    }
    
    /**
     * 
     * @param lengths
     * @return
     */
    public static int[] arrayOfOffsetsFromLengths(int[] lengths) {
    	int[] offsets = new int[lengths.length+1];
    	offsets[0] = 0;
    	for(int i = 1; i < offsets.length; i++) {
            offsets[i] = offsets[i-1] + lengths[i-1];
    	}
    	return offsets;
    }
    
    /**
     * 
     * @param <T>
     * @param array
     * @return 
     */
    public static <T> int[] arrayOfLengths(T[][] array) {
        return arrayOfLengthsTo(array, array.length);
    }
    
    /**
     * 
     * @param array
     * @param to
     * @return 
     */
    public static int[] arrayOfLengthsTo(float[][] array, int to) {
        int[] result = new int[to];
        for(int i = 0; i < to; i++) {
            result[i] = array[i].length;
        }
        return result;
    }
    
    /**
     * 
     * @param array
     * @return 
     */
    public static int[] arrayOfLengths(float[][] array) {
        return arrayOfLengthsTo(array, array.length);
    }
    
    /**
     * 
     * @param array
     * @param to
     * @return 
     */
    public static int[] arrayOfLengthsTo(int[][] array, int to) {
        int[] result = new int[to];
        for(int i = 0; i < to; i++) {
            result[i] = array[i].length;
        }
        return result;
    }
    
    /**
     * 
     * @param array
     * @return 
     */
    public static int[] arrayOfLengths(int[][] array) {
        return arrayOfLengthsTo(array, array.length);
    }
    
    /**
     * 
     * @param array
     * @param to
     * @return 
     */
    public static int[] arrayOfLengthsTo(byte[][] array, int to) {
        int[] result = new int[to];
        for(int i = 0; i < to; i++) {
            result[i] = array[i].length;
        }
        return result;
    }
    
    /**
     * 
     * @param array
     * @return 
     */
    public static int[] arrayOfLengths(byte[][] array) {
        return arrayOfLengthsTo(array, array.length);
    }
    
    
    /**
     * 
     * @param to
     * @param array
     * @return 
     */
    public static long sumArrayTo(long to, long... array) {
        assert(array.length > 0);
        long x = 0;
        for(int i = 0; i < to; i++) {
            x += array[i];
        }
        return x;
    }
    
    /**
     * 
     * @param array
     * @return 
     */
    public static long sumArray(long... array) {
        return sumArrayTo(array.length, array);
    }
    
    /**
     * 
     * @param to
     * @param array
     * @return 
     */
    public static int sumArrayTo(int to, int... array) {
        assert(array.length > 0);
        int x = 0;
        for(int i = 0; i < to; i++) {
            x += array[i];
        }
        return x;
    }
    
    /**
     * 
     * @param array
     * @return 
     */
    public static int sumArray(int... array) {
        return sumArrayTo(array.length, array);
    }
    
    /**
     * 
     * @param to
     * @param array
     * @return 
     */
    public static float sumArrayTo(int to, float... array) {
        assert(array.length > 0);
        float x = 0;
        for(int i = 0; i < to; i++) {
            x += array[i];
        }
        return x;
    }
    
    /**
     * 
     * @param array
     * @return 
     */
    public static float sumArray(float... array) {
        return sumArrayTo(array.length, array);
    }
    
    /**
     * 
     * @param to
     * @param array
     * @return 
     */
    public static byte sumArrayTo(int to, byte... array) {
        assert(array.length > 0);
        byte x = 0;
        for(int i = 0; i < to; i++) {
            x += array[i];
        }
        return x;
    }
    
    /**
     * 
     * @param array
     * @return 
     */
    public static byte sumArray(byte... array) {
        return sumArrayTo(array.length, array);
    }
    
    /**
     * 
     * @param array
     * @return 
     */
    public static float getMaxElement(float... array) {
        float f = array[0];
        for(int i = 1; i < array.length; i++) {
            if(array[i] > f) {
                f = array[i];
            }
        }
        return f;
    }
    
    /**
     * 
     * @param array
     * @return 
     */
    public static int getMaxElement(int... array) {
       int i = array[0];
        for(int j = 1; j < array.length; j++) {
            if(array[j] > i) {
                i = array[j];
            }
        }
        return i;
    }
    
    /**
     * 
     * @param array
     * @return 
     */
    public static byte getMaxElement(byte... array) {
        byte b = array[0];
        for(int i = 1; i < array.length; i++) {
            if(array[i] > b) {
                b = array[i];
            }
        }
        return b;
    }
    
    /**
     * 
     * @param array
     * @return 
     */
    public static float getMaxElement(float[]... array) {
        float f = getMaxElement(array[0]);
        for(int i = 1; i < array.length; i++) {
            float g = getMaxElement(array[i]);
            if(g > f) {
                f = g;
            }
        }
        return f;
    }
    
    /**
     * 
     * @param array
     * @return 
     */
    public static int getMaxElement(int[]... array) {
        int j = getMaxElement(array[0]);
        for(int i = 1; i < array.length; i++) {
            int k = getMaxElement(array[i]);
            if(k > j) {
                j = k;
            }
        }
        return j;
    }
    
    /**
     * 
     * @param array
     * @return 
     */
    public static byte getMaxElement(byte[]... array) {
        byte b = getMaxElement(array[0]);
        for(int i = 1; i < array.length; i++) {
            byte v = getMaxElement(array[i]);
            if(v > b) {
                b = v;
            }
        }
        return b;
    }
    
    /**
     * 
     * @param array
     * @return 
     */
    public static float getMinElement(float... array) {
        float f = array[0];
        for(int i = 1; i < array.length; i++) {
            if(array[i] < f) {
                f = array[i];
            }
        }
        return f;
    }
    
    /**
     * 
     * @param array
     * @return 
     */
    public static int getMinElement(int... array) {
       int i = array[0];
        for(int j = 1; j < array.length; j++) {
            if(array[j] > i) {
                i = array[j];
            }
        }
        return i;
    }
    
    /**
     * 
     * @param array
     * @return 
     */
    public static byte getMinElement(byte... array) {
        byte b = array[0];
        for(int i = 1; i < array.length; i++) {
            if(array[i] > b) {
                b = array[i];
            }
        }
        return b;
    }
    
    /**
     * 
     * @param array
     * @return 
     */
    public static float getMinElement(float[]... array) {
        float f = getMaxElement(array[0]);
        for(int i = 1; i < array.length; i++) {
            float g = getMinElement(array[i]);
            if(g < f) {
                f = g;
            }
        }
        return f;
    }
    
    /**
     * 
     * @param array
     * @return 
     */
    public static int getMinElement(int[]... array) {
        int j = getMaxElement(array[0]);
        for(int i = 1; i < array.length; i++) {
            int k = getMinElement(array[i]);
            if(k < j) {
                j = k;
            }
        }
        return j;
    }
    
    /**
     * 
     * @param array
     * @return 
     */
    public static byte getMinElement(byte[]... array) {
        byte b = getMaxElement(array[0]);
        for(int i = 1; i < array.length; i++) {
            byte v = getMinElement(array[i]);
            if(v < b) {
                b = v;
            }
        }
        return b;
    }
    
    /**
     * 
     * @param i
     * @param j
     * @param lengths
     * @return 
     */
    public static int get1DIndexFrom2D(int i, int j, int[] lengths) {
        return sumArrayTo(i, lengths) + j;
    }
    
    /**
     * 
     * @param <E>
     * @param i
     * @param j
     * @param array
     * @return 
     */
    public static <E> int get1DIndexFrom2D(int i, int j, E[][] array) {
        return get1DIndexFrom2D(i, j, arrayOfLengthsTo(array, i));
    }
    
    /**
     * 
     * @param array
     * @param n
     * @return 
     */
    public static float[] ensureArrayLength(float[] array, int n) {
        if(array.length < n) {
            array = Arrays.copyOf(array, n);
        }
        return array;
    }
    
    /**
     * 
     * @param array
     * @param n
     * @return 
     */
    public static int[] ensureArrayLength(int[] array, int n) {
        if(array.length < n) {
            array = Arrays.copyOf(array, n);
        }
        return array;
    }
    
    /**
     * 
     * @param array
     * @param n
     * @return 
     */
    public static byte[] ensureArrayLength(byte[] array, int n) {
        if(array.length < n) {
            array = Arrays.copyOf(array, n);
        }
        return array;
    }
    
    /**
     * 
     * @param <T>
     * @param array
     * @param n
     * @return 
     */
    public static <T> T[] ensureArrayLength(T[] array, int n) {
        if(array.length < n) {
            array = Arrays.copyOf(array, n);
        }
        return array;
    }
    
    /**
     * 
     * @param array
     * @param n1
     * @param n2
     * @return 
     */
    public static float[][] ensureArrayLength(float[][] array, int n1, int n2) {
        if(array.length < n1) {
            array = new float[n1][n2];
            return array;
        }
        for(int i = 0; i < array.length; i++) {
            ensureArrayLength(array[i], n2);
        }
        return array;
    }
    
    /**
     * 
     * @param array
     * @param n1
     * @param n2
     * @return 
     */
    public static int[][] ensureArrayLength(int[][] array, int n1, int n2) {
        if(array.length < n1) {
            array = new int[n1][n2];
            return array;
        }
        for(int i = 0; i < array.length; i++) {
            ensureArrayLength(array[i], n2);
        }
        return array;
    }
    
    /**
     * 
     * @param array
     * @param n1
     * @param n2
     * @return 
     */
    public static byte[][] ensureArrayLength(byte[][] array, int n1, int n2) {
        if(array.length < n1) {
            array = new byte[n1][n2];
            return array;
        }
        for(int i = 0; i < array.length; i++) {
            ensureArrayLength(array[i], n2);
        }
        return array;
    }
    
    /**
     * 
     * @param <T>
     * @param array
     * @param n1
     * @param n2
     * @return 
     */
    public static <T> T[][] ensureArrayLength(T[][] array, int n1, int n2) {
        if(array.length < n1) {
            int oldL = array.length;
            array = Arrays.copyOf(array, n1);
            for(int i = oldL; i < array.length; i++) {
                array[i] = Arrays.copyOf(array[0], n2);
            }
        }
        for(int i = 0; i < array.length; i++) {
            ensureArrayLength(array[i], n2);
        }
        return array;
    }
    
    /**
     * 
     * @param <T>
     * @param t
     * @return 
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getClass(T t) {
        return (Class<T>) t.getClass();
    }
    
    /**
     * 
     * @param <T>
     * @param clazz
     * @param length
     * @return 
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] createArray(Class<T> clazz, int length) {
        return (T[]) Array.newInstance(clazz, length);
    }
    
    /**
     * 
     * @param <T>
     * @param clazz
     * @param lengths
     * @return 
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] createArray(Class<T> clazz, int... lengths) {
        return (T[]) Array.newInstance(clazz, lengths);
    }
    
    /**
     * 
     * @param i
     * @param length
     * @return 
     */
    public static int makeIndexInBounds(int i, int length) {
        while(i >= length) {
            i -= length;
        }
        while(i < 0) {
            i += length;
        }
        return i;
    }
    
    /**
     * 
     * @param <E>
     * @param i
     * @param array
     * @return 
     */
    public static <E> int makeIndexInBounds(int i, E[] array) {
        return makeIndexInBounds(i, array.length);
    }
    
    /**
     * 
     * @param <E>
     * @param arrays
     * @param totalLength
     * @return 
     */
    public static <E> E[] concatArrays(E[][] arrays, int totalLength) {
        E[] result = Arrays.copyOf(arrays[0], totalLength);
        for(int i = 1, pos = arrays[0].length; i < arrays.length; i++) {
            System.arraycopy(arrays[i], 0, result, pos, arrays[i].length);
            pos += arrays[i].length;
        }
        return result;
    }
    
    /**
     * Assumes all arrays are the same size.
     * @param <E>
     * @param arrays
     * @return 
     */
    public static <E> E[] concatArrays(E[][] arrays) {
        return concatArrays(arrays, arrays.length * arrays[0].length);
    }
    
    /**
     * 
     * @param <E>
     * @param arrays
     * @param lengths
     * @return 
     */
    public static <E> E[] concatArrays(E[][] arrays, int[] lengths) {
        return concatArrays(arrays, sumArray(lengths));
    }
    
    /**
     * 
     * @param <E>
     * @param arrays
     * @return 
     */
    public static <E> E[] concatVariableLengthArrays(E[][] arrays) {
        return concatArrays(arrays, arrayOfLengths(arrays));
    }
    
    /**
     * 
     * @param arrays
     * @param totalLength
     * @return 
     */
    public static float[] concatArrays(float[][] arrays, int totalLength) {
        float[] data = new float[totalLength];
        int pos = 0;
        for(int i = 0; i < arrays.length; i++) {
            System.arraycopy(arrays[i], 0, data, pos, arrays[i].length);
            pos += arrays[i].length;
        }
        return data;
    }
    
    /**
     * Assumes all arrays are the same size.
     * @param arrays
     * @return 
     */
    public static float[] concatArrays(float[][] arrays) {
        return concatArrays(arrays, arrays.length * arrays[0].length);
    }
    
    /**
     * 
     * @param arrays
     * @param lengths
     * @return 
     */
    public static float[] concatArrays(float[][] arrays, int[] lengths) {
        return concatArrays(arrays, sumArray(lengths));
    }
    
    /**
     * 
     * @param arrays
     * @return 
     */
    public static float[] concatVariableLengthArrays(float[][] arrays) {
        return concatArrays(arrays, arrayOfLengths(arrays));
    }
    
    /**
     * 
     * @param arrays
     * @param totalLength
     * @return 
     */
    public static int[] concatArrays(int[][] arrays, int totalLength) {
        int[] data = new int[totalLength];
        int pos = 0;
        for(int i = 0; i < arrays.length; i++) {
            System.arraycopy(arrays[i], 0, data, pos, arrays[i].length);
            pos += arrays[i].length;
        }
        return data;
    }
    
    /**
     * Assumes all arrays are the same size.
     * @param arrays
     * @return 
     */
    public static int[] concatArrays(int[][] arrays) {
        return concatArrays(arrays, arrays.length * arrays[0].length);
    }
    
    /**
     * 
     * @param arrays
     * @param lengths 
     * @return 
     */
    public static int[] concatArrays(int[][] arrays, int[] lengths) {
        return concatArrays(arrays, sumArray(lengths));
    }
    
    /**
     * 
     * @param arrays
     * @return 
     */
    public static int[] concatVariableLengthArrays(int[][] arrays) {
        return concatArrays(arrays, arrayOfLengths(arrays));
    }

    /**
     * 
     * @param arrays
     * @param totalLength
     * @return 
     */
    public static byte[] concatArrays(byte[][] arrays, int totalLength) {
        byte[] data = new byte[totalLength];
        int pos = 0;
        for(int i = 0; i < arrays.length; i++) {
            System.arraycopy(arrays[i], 0, data, pos, arrays[i].length);
            pos += arrays[i].length;
        }
        return data;
    }
    
    /**
     * Assumes all arrays are the same size.
     * @param arrays
     * @return 
     */
    public static byte[] concatArrays(byte[][] arrays) {
        return concatArrays(arrays, arrays.length * arrays[0].length);
    }
    
    /**
     * @param arrays
     * @param lengths 
     * @return 
     */
    public static byte[] concatArrays(byte[][] arrays, int[] lengths) {
        return concatArrays(arrays, sumArray(lengths));
    }
    
    /**
     * 
     * @param arrays
     * @return 
     */
    public static byte[] concatVariableLengthArrays(byte[][] arrays) {
        return concatArrays(arrays, arrayOfLengths(arrays));
    }
}
