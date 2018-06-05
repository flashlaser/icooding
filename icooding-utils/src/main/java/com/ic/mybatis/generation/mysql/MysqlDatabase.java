package com.ic.mybatis.generation.mysql;


import com.ic.mybatis.generation.Database;
import com.ic.mybatis.generation.Field;
import com.ic.mybatis.generation.Table;
import org.apache.commons.lang.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MysqlDatabase implements Database {

    private String jdbcUrl;
    private String dbName;
    private String username;
    private String password;
    private String driver;


    public MysqlDatabase(String jdbcUrl, String dbName, String username, String password, String driver) {
        this.jdbcUrl = jdbcUrl;
        this.dbName = dbName;
        this.username = username;
        this.password = password;
        this.driver = driver;
    }

    private Connection connection;

    public Connection getConnection() throws Exception{
        if(connection != null){
            return connection;
        }
        Class.forName(driver);
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    public String getDatabaseName() throws Exception{
        return dbName;
    }

    public List<Table> getTables() throws Exception{
        String sql = "SELECT TABLE_NAME,TABLE_COMMENT FROM information_schema.TABLES WHERE table_schema='"+getDatabaseName()+"'";
        ResultSet show_tables = getConnection().createStatement().executeQuery(sql);
        List<Table> tables = new ArrayList<Table>();
        while (show_tables.next()){
            Table table = new Table();
            table.setName(show_tables.getString("TABLE_NAME"));
            table.setDesc(show_tables.getString("TABLE_COMMENT"));
            tables.add(table);
        }
        return tables;
    }

    public List<Field> getFields(String tableName) throws Exception{
        String sql = "SELECT COLUMN_NAME,column_comment,COLUMN_TYPE,COLUMN_KEY FROM INFORMATION_SCHEMA.Columns WHERE table_name='"+tableName+"' AND table_schema= '"+getDatabaseName()+"'";
        ResultSet show_tables = getConnection().createStatement().executeQuery(sql);
        List<Field> fields = new ArrayList<Field>();
        while (show_tables.next()){
            Field field = new Field();
            field.setColumnName(show_tables.getString("COLUMN_NAME"));
            field.setDesc(show_tables.getString("column_comment"));
            field.setColumnType(show_tables.getString("COLUMN_TYPE"));
            field.setKey(StringUtils.equals("PRI",show_tables.getString("COLUMN_KEY")));
            field.build();
            fields.add(field);
        }
        return fields;
    }
}
