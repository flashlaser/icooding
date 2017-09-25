package com.icooding.doc;

public class MarkdownParam {
    private String name;
    private String type;
    private String must;
    private String def;
    private String desc;
    private String simple;

    public MarkdownParam() {
    }

    public MarkdownParam(String name, String type, String must, String def, String desc, String simple) {
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

    public String getMust() {
        return must;
    }

    public void setMust(String must) {
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
