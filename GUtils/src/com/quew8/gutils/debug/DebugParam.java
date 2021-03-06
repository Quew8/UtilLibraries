package com.quew8.gutils.debug;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author Quew8
 */
@Target(ElementType.FIELD)
@Repeatable(DebugParams.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface DebugParam {
   String name() default "";
   String pointer() default "";
   Class<? extends DebugInterpreter> interpreter() default DebugInterpreter.class;
   boolean finalParam() default false;
}