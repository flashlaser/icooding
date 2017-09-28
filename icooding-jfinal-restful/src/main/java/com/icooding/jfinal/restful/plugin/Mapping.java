package com.icooding.jfinal.restful.plugin;


import com.icooding.jfinal.restful.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.regex.Pattern;

public class Mapping {
    /**
     * jfinal 的真实接口地址
     */
    private String jfinalPath;
    /**
     * 转换后的地址
     */
    private String restfulPath;

    /**
     * 编译了的正则表达式对象
     */
    private Pattern regexPath;

    /**
     * 接收方法
     */
    private RequestMethod method;

    public Map<String,String> params;

    public Mapping() {
    }

    public Mapping(String jfinalPath, String restfulPath, RequestMethod method) {
        this.jfinalPath = jfinalPath;
        this.restfulPath = restfulPath;
        this.method = method;
        if(isRegexPath()){
            regexPath = UrlPatternUtils.compile(restfulPath);
        }
    }

    /**
     * 是否需要转换为正则表达式
     * @return
     */
    public boolean isRegexPath(){
        return restfulPath == null?false:restfulPath.contains("{");
    }



    public Pattern getRegexPath() {
        return regexPath;
    }

    public void setRegexPath(Pattern regexPath) {
        this.regexPath = regexPath;
    }

    public String getJfinalPath() {
        return jfinalPath;
    }

    public void setJfinalPath(String jfinalPath) {
        this.jfinalPath = jfinalPath;
    }

    public String getRestfulPath() {
        return restfulPath;
    }

    public void setRestfulPath(String restfulPath) {
        this.restfulPath = restfulPath;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public void setMethod(RequestMethod method) {
        this.method = method;
    }

    public static String toKey(String restfulPath,String method){
        return restfulPath +"."+method;
    }

    @Override
    public String toString() {
        return "restfulPath='" + restfulPath + '\'' +
                ", method=" + method +
                "  ==> jfinalPath='" + jfinalPath + '\'' ;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public void setParamsToRequestAttribute(HttpServletRequest request) {
        if(params != null){
            for (Map.Entry<String, String> entry : params.entrySet()) {
                request.setAttribute(entry.getKey(),entry.getValue());
            }
        }
    }
}
