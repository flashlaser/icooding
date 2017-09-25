package com.icooding.doc.core;

import com.icooding.doc.core.API;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;


/**
 * 文档适配器  将Method转换为一个API的DOC对象
 */
public interface DocHandler {

    /**
     * 会先调用此方法判断是否进入convert
     * @param method
     * @return
     */
    public boolean isConvert(Method method);

    /**
     * 将一个扫描到的方法 转换为DOC
     * @param clazz   class
     * @param method  method
     * @param methodAnnotation 方法头的注解
     * @return
     */
    public API convert(Class clazz, Method method , Annotation[] methodAnnotation);



}
