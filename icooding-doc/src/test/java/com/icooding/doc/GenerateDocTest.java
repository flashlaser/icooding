package com.icooding.doc;

import com.icooding.doc.config.DocConfig;
import com.icooding.doc.core.GenerateDoc;

import java.io.IOException;

/**
 * Created by jagua on 2017/9/25.
 */
public class GenerateDocTest {


    public static void main(String[] args) {
        DocConfig config = new DocConfig();
        config.setBaseUrl("http://127.0.0.1");
        config.setName("系统接口文档");
        config.setVersion("1.0");
        try {
            GenerateDoc.$().setConfig(config).generate("com.icooding.controller");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
