package com.icooding.jfinal.restful.handler;

import com.icooding.jfinal.restful.plugin.Mapping;
import com.icooding.jfinal.restful.plugin.MappingManager;
import com.jfinal.handler.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 将HttpMethod 添加到url中
 */
public class HttpMethodHandler  extends Handler {

    /**
     * 对URL 进行重写
     */
    @Override
    public void handle(String target, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, boolean[] handlers) {
        Mapping urlMapping = MappingManager.getUrlMapping(target,httpServletRequest.getMethod());
        if(urlMapping != null){
            target = urlMapping.getJfinalPath();
            urlMapping.setParamsToRequestAttribute(httpServletRequest);
        }
        next.handle(target,httpServletRequest,httpServletResponse,handlers);
    }

}
