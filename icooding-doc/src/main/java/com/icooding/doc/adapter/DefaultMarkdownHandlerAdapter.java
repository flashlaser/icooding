package com.icooding.doc.adapter;

import com.icooding.doc.core.API;
import com.icooding.doc.core.DocHandler;
import com.icooding.doc.core.Param;
import com.icooding.doc.annotation.DOC;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * 默认的实现
 * Created by jagua on 2017/9/25.
 */
public class DefaultMarkdownHandlerAdapter implements DocHandler {

    public boolean isConvert(Method method) {
        return Modifier.isPublic(method.getModifiers()) && method.isAnnotationPresent(DOC.class);
    }

    public API convert(Class clazz, Method method, Annotation[] methodAnnotation) {
        API markdownDoc = new API();
        DOC doc = (DOC) methodAnnotation[0];
        markdownDoc.setMethod(doc.method());
        markdownDoc.setModel(doc.model());
        markdownDoc.setName(doc.name());
        markdownDoc.setUrl(doc.url());
        List<Param> requestParam = new ArrayList<Param>();
        for (com.icooding.doc.annotation.Param param : doc.params()) {
            Param markdownParam = new Param();
            markdownParam.setName(param.name());
            markdownParam.setDesc(param.desc());
            markdownParam.setMust(param.requerd()? Param.Must.YES:Param.Must.NO);
            markdownParam.setType(param.type());
            markdownParam.setSimple(param.simple());
            markdownParam.setDef(param.def());
            requestParam.add(markdownParam);
        }
        markdownDoc.setRequestEntity(requestParam);
        List<Param> responseParam = new ArrayList<Param>();
        for (com.icooding.doc.annotation.Param param : doc.response()) {
            Param markdownParam = new Param();
            markdownParam.setName(param.name());
            markdownParam.setDesc(param.desc());
            markdownParam.setMust(param.requerd()? Param.Must.YES:Param.Must.NO);
            markdownParam.setType(param.type());
            markdownParam.setSimple(param.simple());
            markdownParam.setDef(param.def());
            responseParam.add(markdownParam);
        }
        markdownDoc.setResponseEntity(responseParam);
        return markdownDoc;
    }
}
