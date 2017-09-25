package com.icooding.doc.ui;

import com.icooding.doc.config.DocConfig;
import com.icooding.doc.core.API;
import com.icooding.doc.core.UIHandler;

import java.util.List;
import java.util.Map;

/**
 * Created by jagua on 2017/9/25.
 */
public class MarkdownUiHandler implements UIHandler {


    public String convert(DocConfig config,Map<String,List<API>> apis) {
        StringBuffer doc = new StringBuffer();
        int i = 1;
        if(config.getName() != null){
            doc.append("<center>"+config.getName() +" "+config.getVersion()+"</center>\n");
            doc.append("---\n");
        }

        for (Map.Entry<String, List<API>> stringListEntry : apis.entrySet()) {
            doc.append("\n");
            doc.append(String.format("#### %s. %s \n",i,stringListEntry.getKey()));
            doc.append("\n");
            int j = 1;
            for (API markdownDoc : stringListEntry.getValue()) {
                if(config.getBaseUrl() != null){
                    markdownDoc.setUrl( markdownDoc.getUrl());
                }
                doc.append(markdownDoc.toMarkdowndoc(String.format("%s.%s",i,j)));
                j++;
            }
            i++;
        }
        return doc.toString();
    }
}
