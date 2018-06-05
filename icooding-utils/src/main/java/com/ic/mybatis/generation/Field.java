package com.ic.mybatis.generation;

import com.ic.utils.NameUtils;

import java.math.BigDecimal;
import java.util.Date;

public class Field {


    private boolean key;//主键
    private String columnName;//数据库列名
    private String propertyName;//Java类属性名
    private String columnType;//列类型
    private String propertyType;//Java属性类型
    private String propertySimpleType;//Java属性类型
    private String methodName;//方法名称//仅仅将首字符变大写 方便匹配   getXxx  setXxx
    private String desc;


    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPropertySimpleType() {
        return propertySimpleType;
    }

    public void setPropertySimpleType(String propertySimpleType) {
        this.propertySimpleType = propertySimpleType;
    }

    public Class getJavaType() {
        if(columnType.toUpperCase().contains("CHAR")){
            return String.class;
        }
        if(columnType.toUpperCase().contains("TEXT")){
            columnType = "VARCHAR";
            return String.class;
        }

        if(columnType.toUpperCase().contains("DATE")){
            columnType = "TIMESTAMP";
            return Date.class;
        }
        if(columnType.toUpperCase().contains("TIME")){
            columnType = "TIMESTAMP";
            return Date.class;
        }
        if(columnType.toUpperCase().contains("TIMESTAMP")){
            columnType = "TIMESTAMP";
            return Date.class;
        }
        if(columnType.toUpperCase().contains("BIT")){
            return Boolean.class;
        }
        if(columnType.toUpperCase().contains("INT")){
            columnType = "INTEGER";
            return Integer.class;
        }
        if(columnType.toUpperCase().contains("BIGINT")){
            return Long.class;
        }
        if(columnType.toUpperCase().contains("DOUBLE")){
            return Double.class;
        }
        if(columnType.toUpperCase().contains("FLOAT")){
            return Float.class;
        }
        if(columnType.toUpperCase().contains("DECIMAL")){
            return BigDecimal.class;
        }
        return String.class;
    }


    public boolean isKey() {
        return key;
    }

    public void setKey(boolean key) {
        this.key = key;
    }

    public void build(){
        columnType = columnType.toUpperCase().replaceAll("\\(.*?\\)","");
        Class javaType = getJavaType();
        propertySimpleType = javaType.getSimpleName();
        propertyType = javaType.getName();
        propertyName = NameUtils.lineToHump(columnName);
        char[] chars = propertyName.toCharArray();
        if(chars[0]>= 97 && chars[0] <= 122){
            chars[0] -=32;
        }
        methodName = new String(chars);
    }

}
