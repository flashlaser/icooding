package com.icooding.doc.core;

import com.icooding.doc.adapter.DefaultMarkdownHandlerAdapter;
import com.icooding.doc.annotation.DOC;
import com.icooding.doc.config.DocConfig;
import com.icooding.doc.ui.MarkdownUiHandler;
import com.icooding.utils.ClassUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class GenerateDoc {
    private static volatile GenerateDoc doc = new GenerateDoc();
    private DocConfig config;

    public  static GenerateDoc $(){
        if(doc == null){
            synchronized (doc){
                if(doc == null){
                    doc = new GenerateDoc();
                }
            }
        }
        return doc;
    }

    public GenerateDoc setConfig(DocConfig config) {
        if(config.getMethodDoc() != null){
            this.config.setMethodDoc(config.getMethodDoc());
        }
        if(config.getUiHandler() != null){
            this.config.setUiHandler(config.getUiHandler());
        }
        this.config.setName(config.getName());
        this.config.setBaseUrl(config.getBaseUrl());
        this.config.setVersion(config.getVersion());
        return this;
    }

    private GenerateDoc() {
        config = new DocConfig();
        config.setMethodDoc(new DefaultMarkdownHandlerAdapter());
        config.setUiHandler(new MarkdownUiHandler());
    }

    private Map<String,List<API>> apis = new HashMap<String, List<API>>();

    protected void put(String title,API markdownDoc){
        if(apis.containsKey(title)){
            List<API> markdownDocs = apis.get(title);
            markdownDocs.add(markdownDoc);
        }else {
            List<API> list = new LinkedList<API>();
            list.add(markdownDoc);
            apis.put(title, list);
        }
    }

    protected void generate(File out,String...packages)  throws IOException{
        List<Class<?>> classes = new LinkedList<Class<?>>();
        for (String aPackage : packages) {
            classes.addAll( ClassUtils.getClasses(aPackage));
        }

        for (Class<?> aClass : classes) {
            for (Method method : aClass.getMethods()) {
                if(config.getMethodDoc().isConvert(method)){
                    API convert = config.getMethodDoc().convert(aClass, method, method.getAnnotations());
                    if(convert != null){
                        put(convert.getModel(),convert);
                    }
                }
            }
        }
        System.out.println(out.getAbsolutePath());
        FileOutputStream outputStream = new FileOutputStream(out);
        outputStream.write(config.getUiHandler().convert(config,apis).getBytes());
        outputStream.close();
    }

    public void generate(String...packages) throws IOException{
        generate(new File(this.getClass().getResource("/").getPath()+config.getName()+".md"),packages);
    }
}
