package com.icooding.jfinal.restful.intercepter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.icooding.jfinal.restful.annotation.ResponseBody;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 自动JSON返回
 */
public class ResponseBodyInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation invocation) {
        Class<? extends Controller> aClass = invocation.getController().getClass();
        ResponseBody annotation = aClass.getAnnotation(ResponseBody.class);
        if(annotation == null){
            annotation = aClass.getSuperclass().getAnnotation(ResponseBody.class);
        }
        invocation.invoke();
        if(annotation != null){
            Object returnValue = invocation.getReturnValue();
            invocation.getController().renderJson(returnValue);
        }
    }
}
