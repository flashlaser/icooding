package com.icooding.jfinal.restful.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface RequestMapping {
    @Deprecated
    String jfinalValue() default "";//jfinal 的真实Action路径

    String value() default "";      //需要匹配的路径
    RequestMethod[] method() default
            {
                    RequestMethod.POST,
                    RequestMethod.DELETE,
                    RequestMethod.GET,
                    RequestMethod.HEAD,
                    RequestMethod.OPTIONS,
                    RequestMethod.PATCH,
                    RequestMethod.PUT,
                    RequestMethod.TRACE
            }; //方法
}
