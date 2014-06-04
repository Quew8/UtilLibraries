package com.quew8.gutils.services;

/**
 *
 * @author Quew8
 */
public class NoSuitableImplementationException extends Exception {
    public <T extends ServiceImpl> NoSuitableImplementationException(Class<T> servClazz, Iterable<T> impls) {
        super(getMsg(servClazz, impls));
    }
    
    private static <T extends ServiceImpl> String getMsg(Class<T> servClazz, Iterable<T> impls) {
        String s = "No suitable implementation found for " + servClazz.getName() + "\nFound Implementations:";
        for(T t: impls) {
            s += "\n    " + t.getClass().getName() + "\n      Applicable: " + t.isApplicable() + "\n      Precedence: " + t.getPrecedence();
        }
        return s;
    }
}
