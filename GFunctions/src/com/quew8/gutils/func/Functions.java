package com.quew8.gutils.func;

import com.quew8.gutils.func.converters.ByteToFloatConverterFunction;
import com.quew8.gutils.func.converters.ByteToIntConverterFunction;
import com.quew8.gutils.func.converters.FloatToByteConverterFunction;
import com.quew8.gutils.func.converters.FloatToIntConverterFunction;
import com.quew8.gutils.func.converters.IntToByteConverterFunction;
import com.quew8.gutils.func.converters.IntToFloatConverterFunction;
import com.quew8.gutils.func.math.ByteAddFunction;
import com.quew8.gutils.func.math.ByteMultFunction;
import com.quew8.gutils.func.math.ByteNegateFunction;
import com.quew8.gutils.func.math.FloatAddFunction;
import com.quew8.gutils.func.math.FloatMultFunction;
import com.quew8.gutils.func.math.FloatNegateFunction;
import com.quew8.gutils.func.math.FloatRecFunction;
import com.quew8.gutils.func.math.IntAddFunction;
import com.quew8.gutils.func.math.IntMultFunction;
import com.quew8.gutils.func.math.IntNegateFunction;

/**
 *
 * @author Quew8
 */
public class Functions {
    
    private Functions() {
        
    }
    
    public static <T> Function<T, T> square(Function<T, T> f) {
        return new JoinFunction<T, T, T>(f, f);
    }
    
    public static <T> Function<Integer, T> addI(Function<Integer, T> f1, Function<Integer, T> f2) {
        return new IntAddFunction<T>(f1, f2);
    }   
    
    public static <T> Function<Float, T> addF(Function<Float, T> f1, Function<Float, T> f2) {
        return new FloatAddFunction<T>(f1, f2);
    }  
    
    public static <T> Function<Byte, T> addB(Function<Byte, T> f1, Function<Byte, T> f2) {
        return new ByteAddFunction<T>(f1, f2);
    }
    
    public static <T> Function<Integer, T> addI(Function<Integer, T> f1, int i) {
        return new IntAddFunction<T>(f1, new WrapFunction<Integer, T>(i));
    }  
    
    public static <T> Function<Float, T> addF(Function<Float, T> f1, float f) {
        return new FloatAddFunction<T>(f1, new WrapFunction<Float, T>(f));
    }    
    
    public static <T> Function<Byte, T> addB(Function<Byte, T> f1, byte b) {
        return new ByteAddFunction<T>(f1, new WrapFunction<Byte, T>(b));
    }   
    
    public static Function<Integer, Integer> addI(int i) {
        return new IntAddFunction<Integer>(new ReturnFunction<Integer>(), new WrapFunction<Integer, Integer>(i));
    }  
    
    public static Function<Float, Float> addF(float f) {
        return new FloatAddFunction<Float>(new ReturnFunction<Float>(), new WrapFunction<Float, Float>(f));
    }    
    
    public static Function<Byte, Byte> addB(byte b) {
        return new ByteAddFunction<Byte>(new ReturnFunction<Byte>(), new WrapFunction<Byte, Byte>(b));
    } 
    
    public static <T> Function<Integer, T> subtractI(Function<Integer, T> f1, Function<Integer, T> f2) {
        return new IntAddFunction<T>(f1, new IntNegateFunction<T>(f2));
    }  
    
    public static <T> Function<Float, T> subtractF(Function<Float, T> f1, Function<Float, T> f2) {
        return new FloatAddFunction<T>(f1, new FloatNegateFunction<T>(f2));
    }   
    
    public static <T> Function<Byte, T> subtractB(Function<Byte, T> f1, Function<Byte, T> f2) {
        return new ByteAddFunction<T>(f1, new ByteNegateFunction<T>(f2));
    }    
    
    public static <T> Function<Integer, T> multiplyI(Function<Integer, T> f1, Function<Integer, T> f2) {
        return new IntMultFunction<T>(f1, f2);
    }  
    
    public static <T> Function<Float, T> multiplyF(Function<Float, T> f1, Function<Float, T> f2) {
        return new FloatMultFunction<T>(f1, f2);
    }  
    
    public static <T> Function<Byte, T> multiplyB(Function<Byte, T> f1, Function<Byte, T> f2) {
        return new ByteMultFunction<T>(f1, f2);
    }   
    
    public static <T> Function<Integer, T> multiplyI(Function<Integer, T> f1, int i) {
        return new IntMultFunction<T>(f1, new WrapFunction<Integer, T>(i));
    }   
    
    public static <T> Function<Float, T> multiplyF(Function<Float, T> f1, float f) {
        return new FloatMultFunction<T>(f1, new WrapFunction<Float, T>(f));
    }  
    
    public static <T> Function<Byte, T> multiplyB(Function<Byte, T> f1, byte b) {
        return new ByteMultFunction<T>(f1, new WrapFunction<Byte, T>(b));
    }   
    
    public static Function<Integer, Integer> multiplyI(int i) {
        return new IntMultFunction<Integer>(new ReturnFunction<Integer>(), new WrapFunction<Integer, Integer>(i));
    }   
    
    public static Function<Float, Float> multiplyF(float f) {
        return new FloatMultFunction<Float>(new ReturnFunction<Float>(), new WrapFunction<Float, Float>(f));
    }  
    
    public static Function<Byte, Byte> multiplyB(byte b) {
        return new ByteMultFunction<Byte>(new ReturnFunction<Byte>(), new WrapFunction<Byte, Byte>(b));
    } 
    
    public static <T> Function<Float, T> divideF(Function<Float, T> f1, Function<Float, T> f2) {
        return new FloatMultFunction<T>(f1, new FloatRecFunction<T>(f2));
    }
    
    public static <T> Function<Integer, T> convertFI(Function<Float, T> f) {
        return new FloatToIntConverterFunction<T>(f);
    }
    
    public static <T> Function<Byte, T> convertFB(Function<Float, T> f) {
        return new FloatToByteConverterFunction<T>(f);
    }
    
    public static <T> Function<Byte, T> convertIB(Function<Integer, T> f) {
        return new IntToByteConverterFunction<T>(f);
    }
    
    public static <T> Function<Float, T> convertIF(Function<Integer, T> f) {
        return new IntToFloatConverterFunction<T>(f);
    }
    
    public static <T> Function<Float, T> convertBF(Function<Byte, T> f) {
        return new ByteToFloatConverterFunction<T>(f);
    }
    
    public static <T> Function<Integer, T> convertBI(Function<Byte, T> f) {
        return new ByteToIntConverterFunction<T>(f);
    }
}
