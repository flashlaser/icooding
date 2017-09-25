package com.icooding.doc;

import com.icooding.controller.UserController;
import com.icooding.doc.annotation.DOC;
import com.icooding.doc.annotation.Param;
import com.icooding.utils.ClassUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class GenerateDoc {
    private static volatile GenerateDoc doc = new GenerateDoc();
    private  MarkDownConfig config = new MarkDownConfig();

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

    public GenerateDoc setConfig(MarkDownConfig config) {
        this.config = config;
        return this;
    }

    private GenerateDoc() {
    }

    private Map<String,List<MarkdownDoc>> apis = new HashMap<String, List<MarkdownDoc>>();

    protected void put(String title,MarkdownDoc markdownDoc){
        if(apis.containsKey(title)){
            List<MarkdownDoc> markdownDocs = apis.get(title);
            markdownDocs.add(markdownDoc);
        }else {
            List<MarkdownDoc> list = new LinkedList<MarkdownDoc>();
            list.add(markdownDoc);
            apis.put(title, list);
        }
    }

    public String toString(){
        StringBuffer doc = new StringBuffer();
        int i = 1;
        if(config.getName() != null){
            doc.append(" <center>"+config.getName() +" "+config.getVersion()+"</center>\n");
            doc.append("---\n");
        }

        for (Map.Entry<String, List<MarkdownDoc>> stringListEntry : apis.entrySet()) {
            System.out.println(stringListEntry.getKey()+":"+stringListEntry.getValue().size());
            doc.append("\n");
            doc.append(String.format("#### %s. %s \n",i,stringListEntry.getKey()));
            doc.append("\n");
            int j = 1;
            for (MarkdownDoc markdownDoc : stringListEntry.getValue()) {
                if(config.getBaseUrl() != null){
                    markdownDoc.setUrl(config.getBaseUrl() + markdownDoc.getUrl());
                }
                doc.append(markdownDoc.toMarkdowndoc(String.format("%s.%s",i,j)));
                j++;
            }
            i++;
        }
        return doc.toString();
    }


    protected void addDoc(DOC doc){
        MarkdownDoc markdownDoc = new MarkdownDoc();
        markdownDoc.setMethod(doc.method());
        markdownDoc.setModel(doc.model());
        markdownDoc.setName(doc.name());
        markdownDoc.setUrl(doc.url());
        List<MarkdownParam> requestParam = new ArrayList<MarkdownParam>();
        for (Param param : doc.params()) {
            MarkdownParam markdownParam = new MarkdownParam();
            markdownParam.setName(param.name());
            markdownParam.setDesc(param.desc());
            markdownParam.setMust(param.requerd()?"YES":"NO");
            markdownParam.setType(param.type());
            markdownParam.setSimple(param.simple());
            markdownParam.setDef(param.def());
            requestParam.add(markdownParam);
        }
        markdownDoc.setRequestEntity(requestParam);
        List<MarkdownParam> responseParam = new ArrayList<MarkdownParam>();
        for (Param param : doc.response()) {
            MarkdownParam markdownParam = new MarkdownParam();
            markdownParam.setName(param.name());
            markdownParam.setDesc(param.desc());
            markdownParam.setMust(param.requerd()?"YES":"NO");
            markdownParam.setType(param.type());
            markdownParam.setSimple(param.simple());
            markdownParam.setDef(param.def());
            responseParam.add(markdownParam);
        }
        markdownDoc.setResponseEntity(responseParam);
        put(markdownDoc.getModel(),markdownDoc);
    }

    protected void generate(File out,String...packages){
        List<Class<?>> classes = new LinkedList<Class<?>>();
        for (String aPackage : packages) {
            classes.addAll( ClassUtils.getClasses(aPackage));
        }

        for (Class<?> aClass : classes) {
            for (Method method : aClass.getMethods()) {
                if(Modifier.isPublic(method.getModifiers()) && method.isAnnotationPresent(DOC.class)){
                    GenerateDoc.$().addDoc(method.getAnnotation(DOC.class));
                }
            }
        }

        try {
            System.out.println(out.getAbsolutePath());
            FileOutputStream outputStream = new FileOutputStream(out);
            outputStream.write(this.toString().getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void generate(String...packages){
        generate(new File(this.getClass().getResource("/").getPath()+"generateDoc.md"),packages);
    }

    public static void main(String[] args) {
        MarkDownConfig config = new MarkDownConfig();
        config.setBaseUrl("http://127.0.0.1");
        config.setName("系统接口文档");
        config.setVersion("1.0");
        GenerateDoc.$().setConfig(config).generate("com.icooding.controller");
    }

}
