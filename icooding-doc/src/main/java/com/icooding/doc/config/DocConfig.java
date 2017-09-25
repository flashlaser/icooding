package com.icooding.doc.config;

import com.icooding.doc.core.DocHandler;
import com.icooding.doc.core.UIHandler;

import java.lang.annotation.Annotation;

public class DocConfig {
    /**
     * 域名
     */
    private String baseUrl;
    /**
     * 文档名字
     */
    private String name;
    /**
     * 文档版本
     */
    private String version;

    /**
     * 文档格式转换
     */
    protected DocHandler methodDoc = null;
    /**
     * UI输出
     */
    protected UIHandler uiHandler = null;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public DocHandler getMethodDoc() {
        return methodDoc;
    }

    public void setMethodDoc(DocHandler methodDoc) {
        this.methodDoc = methodDoc;
    }

    public UIHandler getUiHandler() {
        return uiHandler;
    }

    public void setUiHandler(UIHandler uiHandler) {
        this.uiHandler = uiHandler;
    }

}
