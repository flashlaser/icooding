package com.icooding.jfinal.restful.plugin;

/**
 *
 * 根据自己系统规则生成
 * Created by jagua on 2017/9/12.
 */
public interface ActionConvert {
    public String controller(String controllerKey);
    public String method(String actionKey);
}
