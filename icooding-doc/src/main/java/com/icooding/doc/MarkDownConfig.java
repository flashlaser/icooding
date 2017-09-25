package com.icooding.doc;

import java.lang.annotation.Annotation;

public class MarkDownConfig {


    private String baseUrl;
    private String name;
    private String version;
    private Class<? extends Annotation> docAnnotation;

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

    public Class<? extends Annotation> getDocAnnotation() {
        return docAnnotation;
    }

    public void setDocAnnotation(Class<? extends Annotation> docAnnotation) {
        this.docAnnotation = docAnnotation;
    }
}
