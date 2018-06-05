package com.ic.mybatis.generation;

import com.ic.mybatis.generation.mysql.MysqlDatabase;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Generation {

    private String tableNameReplaceSource = "";//要替换的字符串
    private String tableNameReplaceTarget = "";//提环成的字符串

    private String entityPackage = "";
    private String entityTemaplate = "entity.vm";

    private String mapperPackage = "";
    private String mapperPath = "mapper";
    private String mapperTemaplate = "mapperXml.vm";
    private String mapperClassTemaplate = "mapper.vm";

    private String servicePackage = "";
    private String serviceImplPackage = "";
    private String serviceTemaplate = "service.vm";
    private String serviceImplTemaplate = "serviceImpl.vm";

    private String parentEntity = "";
    private String sourcePath;




    private String jdbcUrl;
    private String dbName;
    private String username;
    private String password;
    private String driver;

    private List<Table> tables;


    private String CHAR_ENCODING = "UTF-8";

    private static Database database;

    static Generation generation ;
    static  VelocityEngine velocityEngine = new VelocityEngine();


    private Generation(){
        velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.init();
        this.sourcePath = getClass().getClassLoader().getResource("").getPath();
        System.out.println(this.sourcePath);
    }


    public static Generation $(String jdbcUrl, String dbName, String username, String password, String driver){
        if(database == null){
                database = new MysqlDatabase(jdbcUrl,  dbName,  username,  password,  driver);
        }
        if(generation == null){
            generation = new Generation();
        }
        return generation;
    }

    public Generation init() throws Exception{
        List<Table> tables = database.getTables();
        for (Table table : tables) {
            table.setFieldList(database.getFields(table.getName()));
            table.setReplaceSource(tableNameReplaceSource);
            table.setReplaceTarget(tableNameReplaceTarget);
            table.build();

        }

        for (Table table : tables) {
            List<com.ic.mybatis.generation.Field> fieldList = table.getFieldList();
            List<com.ic.mybatis.generation.Field> newfieldList = new ArrayList<com.ic.mybatis.generation.Field>();

            List<String> exlude = new ArrayList<String>();
            if(StringUtils.isNotEmpty(parentEntity)){
                Class<?> aClass = Class.forName(parentEntity);
                for (Field field : aClass.getDeclaredFields()) {
                    if(field.getModifiers() != 26){
                        exlude.add(field.getName());
                    }
                }
            }


            for (com.ic.mybatis.generation.Field field : fieldList) {
                if(!exlude.contains(field.getPropertyName())){
                    newfieldList.add(field);
                }
            }
            table.setFieldList(newfieldList);
            this.tables  = tables;
        }
        return this;
    }


    public Generation buildEntity() throws Exception{
        Template template = velocityEngine.getTemplate(entityTemaplate, CHAR_ENCODING);
        for (Table table : tables) {
            VelocityContext context = new VelocityContext();
            context.put("entityPackage", entityPackage);
            context.put("parentEntity", parentEntity.substring(parentEntity.lastIndexOf(".")+1,parentEntity.length()));
            context.put("parentEntityPackage",parentEntity);
            context.put("t", table);
            String entitypath = sourcePath+entityPackage.replace(".","/")+"/";
            String entityName = table.getClassName()+ ".java";
            File file = new File(entitypath);
            if(!file.exists()){
                file.mkdirs();
            }
            FileWriter fileWriter = new FileWriter(new File(entitypath,entityName));
            template.merge(context, fileWriter);
            fileWriter.flush();
        }
        return this;
    }


    public Generation buildMapper() throws Exception{
        Template template = velocityEngine.getTemplate(mapperTemaplate, CHAR_ENCODING);
        for (Table table : tables) {
            VelocityContext context = new VelocityContext();
            context.put("entityPackage", entityPackage);
            context.put("mapperPackage", mapperPackage);
            context.put("t", table);
            String entitypath = sourcePath + mapperPath+"/";
            String entityName = table.getClassName()+ "Mapper.xml";
            File file = new File(entitypath);
            if(!file.exists()){
                file.mkdirs();
            }
            FileWriter fileWriter = new FileWriter(new File(entitypath,entityName));
            template.merge(context, fileWriter);
            fileWriter.flush();
        }

        return this;
    }


    public Generation buildMapperClass() throws Exception{
        Template template = velocityEngine.getTemplate(mapperClassTemaplate, CHAR_ENCODING);
        for (Table table : tables) {
            VelocityContext context = new VelocityContext();
            context.put("entityPackage", entityPackage);
            context.put("mapperPackage", mapperPackage);
            context.put("t", table);
            String entitypath = sourcePath+mapperPackage.replace(".","/")+"/";
            String entityName = table.getClassName()+ "Mapper.java";
            File file = new File(entitypath);
            if(!file.exists()){
                file.mkdirs();
            }
            FileWriter fileWriter = new FileWriter(new File(entitypath,entityName));
            template.merge(context, fileWriter);
            fileWriter.flush();
        }

        return this;
    }



    public Generation buildService() throws Exception{
        Template template = velocityEngine.getTemplate(serviceTemaplate, CHAR_ENCODING);
        for (Table table : tables) {
            VelocityContext context = new VelocityContext();
            context.put("entityPackage", entityPackage);
            context.put("mapperPackage", mapperPackage);
            context.put("servicePackage", servicePackage);
            context.put("t", table);
            String entitypath = sourcePath+servicePackage.replace(".","/")+"/";
            String entityName = table.getClassName()+ "Service.java";
            File file = new File(entitypath);
            if(!file.exists()){
                file.mkdirs();
            }
            FileWriter fileWriter = new FileWriter(new File(entitypath,entityName));
            template.merge(context, fileWriter);
            fileWriter.flush();
        }


        template = velocityEngine.getTemplate(serviceImplTemaplate, CHAR_ENCODING);
        for (Table table : tables) {
            VelocityContext context = new VelocityContext();
            context.put("entityPackage", entityPackage);
            context.put("mapperPackage", mapperPackage);
            context.put("servicePackage", servicePackage);
            context.put("serviceImplPackage", serviceImplPackage);
            context.put("t", table);
            String entitypath = sourcePath+serviceImplPackage.replace(".","/")+"/";
            String entityName = table.getClassName()+ "ServiceImpl.java";
            File file = new File(entitypath);
            if(!file.exists()){
                file.mkdirs();
            }
            FileWriter fileWriter = new FileWriter(new File(entitypath,entityName));
            template.merge(context, fileWriter);
            fileWriter.flush();
        }

        return this;
    }

    public Generation setEntityPackage(String entityPackage) {
        this.entityPackage = entityPackage;
        return this;
    }


    public Generation setMapperPackage(String mapperPackage) {
        this.mapperPackage = mapperPackage;
        return this;
    }

    public Generation setParentEntity(String parentEntity) {
        this.parentEntity = parentEntity;
        return this;
    }

    public Generation setMapperPath(String mapperPath) {
        this.mapperPath = mapperPath;
        return this;
    }

    public Generation setServicePackage(String servicePackage) {
        this.servicePackage = servicePackage;
        return this;
    }


    public Generation setServiceImplPackage(String serviceImplPackage) {
        this.serviceImplPackage = serviceImplPackage;
        return this;
    }

    public Generation setTableNameReplaceSource(String tableNameReplaceSource) {
        this.tableNameReplaceSource = tableNameReplaceSource;
        return this;
    }

    public Generation setTableNameReplaceTarget(String tableNameReplaceTarget) {
        this.tableNameReplaceTarget = tableNameReplaceTarget;
        return this;
    }
}
