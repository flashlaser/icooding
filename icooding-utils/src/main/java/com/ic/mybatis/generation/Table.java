package com.ic.mybatis.generation;


import com.ic.utils.NameUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 */
public class Table {

    /**
     *
     */
    private String name;//表名
    private String className;//Java类名
    private String desc;//描述

    private String replaceSource = "";//要替换的字符串
    private String replaceTarget = "";//提环成的字符串

    private Set<String> importPackages = new HashSet<String>();//实体类需要引入的包

    private List<com.ic.mybatis.generation.Field> fieldList = new ArrayList<Field>();//字段


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getReplaceSource() {
        return replaceSource;
    }

    public void setReplaceSource(String replaceSource) {
        this.replaceSource = replaceSource;
    }

    public String getReplaceTarget() {
        return replaceTarget;
    }

    public void setReplaceTarget(String replaceTarget) {
        this.replaceTarget = replaceTarget;
    }

    public Set<String> getImportPackages() {
        return importPackages;
    }

    public void setImportPackages(Set<String> importPackages) {
        this.importPackages = importPackages;
    }

    public List<com.ic.mybatis.generation.Field> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<com.ic.mybatis.generation.Field> fieldList) {
        this.fieldList = fieldList;
    }

    public void build(){
        String n = name.replace(replaceSource,replaceTarget);
        n  = NameUtils.lineToHump(n);
        char[] chars = n.toCharArray();
        if(chars[0]>= 97 && chars[0] <= 122){
            chars[0] -=32;
        }
        this.className = new String(chars);
        for (com.ic.mybatis.generation.Field field : fieldList) {
            this.importPackages.add(field.getPropertyType());
        }
    }
}
