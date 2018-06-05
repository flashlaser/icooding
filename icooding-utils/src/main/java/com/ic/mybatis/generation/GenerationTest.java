package com.ic.mybatis.generation;


public class GenerationTest {
    public static void main(String[] args) throws Exception{
        Generation.$(
                "jdbc:mysql://192.168.0.19:3306/updfzx?characterEncoding=utf8&useSSL=false&zeroDateTimeBehavior=convertToNull",
                "updfzx",
                "updfzx",
                "!QAZ2wsx",
                "com.mysql.jdbc.Driver"
        )
                .setEntityPackage("com.axinfu.unionpay.dfzx.entity")
                .setMapperPackage("com.axinfu.unionpay.dfzx.mapper")
                .setServicePackage("com.axinfu.unionpay.dfzx.service")
                .setServiceImplPackage("com.axinfu.unionpay.dfzx.service.impl")
                .setMapperPath("mapper")
                .setTableNameReplaceSource("t_")
                .setTableNameReplaceTarget("")
                .init()
                .buildEntity()
                .buildMapper()
                .buildMapperClass()
                .buildService();


    }

}
