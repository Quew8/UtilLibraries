package com.quew8.gutils.debug;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Quew8
 */
@Inherited()
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DebugObject {
    String name() default "";
    Class<? extends DebugChangeNotifier> changeNotifier() default DebugChangeNotifier.class;
}
