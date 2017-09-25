package com.icooding.doc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public interface MarkdownDocInterface {

    /**
     * 将一个扫描到的方法 转换为DOC
     * @param clazz   class
     * @param method  method
     * @param methodAnnotation 方法头的注解
     * @return
     */
    public MarkdownDoc convert(Class clazz, Method method , Annotation[] methodAnnotation);



}
