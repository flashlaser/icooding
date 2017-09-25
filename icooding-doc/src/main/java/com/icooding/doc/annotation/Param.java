package com.icooding.doc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Param {
    /**
     * 名称
     * @return
     */
    String name();

    /**
     * 类型
     * @return
     */
    String type();
    /**
     * 默认值
     * @return
     */
    String def() default "";

    /**
     * 描述
     * @return
     */
    String desc() default "";

    /**
     * 示例
     * @return
     */
    String simple() default "";

    /**
     * 必须
     * @return
     */
    boolean requerd() default true;
}
