package com.ic.mybatis.generation;

import java.sql.Connection;
import java.util.List;

public interface Database {

    public Connection getConnection() throws Exception;


    public String getDatabaseName() throws Exception;


    public List<Table> getTables() throws Exception;


    public List<com.ic.mybatis.generation.Field> getFields(String tableName)throws Exception;


}
