package com.quew8.gutils.func;

/**
 *
 * @author Quew8
 * @param <T>
 * @param <S>
 */
public class SwitchFunction<T, S> extends AbstractDualOperandFunction<T, T, S> {
    protected Function<Boolean, S> switchFunction;
    
    public SwitchFunction(Function<Boolean, S> switchFunction, Function<T, S> function1, Function<T, S> function2) {
        super(function1, function2);
        this.switchFunction = switchFunction;
    }
    
    @Override
    public T f(S s) {
        return switchFunction.f(s) ? function1.f(s) : function2.f(s);
    }
}
