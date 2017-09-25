package com.icooding.doc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DOC {
    String model() default "API";
    String name();
    String url();
    String method() default "GET";
    Param[] params() default {};
    Param[]  response() default {};
}
