package com.icooding.doc.core;

public class Param {

    public enum Must{
        YES,NO
    }

    /**
     * 参数名
     */
    private String name;
    /**
     * 类型
     */
    private String type;
    /**
     * 必须 YES/NO
     */
    private Must must;
    /**
     * 默认值
     */
    private String def;
    /**
     * 描述
     */
    private String desc;
    /**
     * 示例
     */
    private String simple;

    public Param() {
    }

    public Param(String name, String type, Must must, String def, String desc, String simple) {
        this.name = name;
        this.type = type;
        this.must = must;
        this.def = def;
        this.desc = desc;
        this.simple = simple;
    }

    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Must getMust() {
        return must;
    }

    public void setMust(Must must) {
        this.must = must;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSimple() {
        return simple;
    }

    public void setSimple(String simple) {
        this.simple = simple;
    }
}
