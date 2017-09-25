package com.icooding.doc;

import com.icooding.utils.RegexUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkdownDoc {
    private String model;
    private String name;
    private String url;
    private String method;
    private String defult;
    private List<MarkdownParam> requestEntity;
    private List<MarkdownParam> responseEntity ;
    private String sampleCode;


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setRequestEntity(List<MarkdownParam> requestEntity) {
        this.requestEntity = requestEntity;
    }

    public void setResponseEntity(List<MarkdownParam> responseEntity) {
        this.responseEntity = responseEntity;
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    public String getDefult() {
        return defult;
    }

    public void setDefult(String defult) {
        this.defult = defult;
    }

    public String toMarkdowndoc(String number){
        StringBuffer doc = new StringBuffer();
        doc.append(String.format("\n"));
        doc.append(String.format("--- \n"));
        doc.append("\n");
        doc.append(String.format("##### %s %s \n",number,name));
        doc.append("\n");
        doc.append(String.format("URL: %s \n",url));
        doc.append("\n");
        doc.append(String.format("Method: %s \n",method));
        doc.append("\n");

        List<String> pathParam = RegexUtils.findbyRegex("(\\{\\w+\\})", url);
        if(pathParam.size() > 0){
            doc.append(String.format("Path参数: \n"));
            doc.append(String.format("|参数名|类型|是否必填|默认值|描述|示例值|\n"));
            doc.append(String.format("|----|----|----|----|----|------| \n"));
            for (String s : pathParam ) {
                doc.append(String.format("|%s|string|YES||| |\n",s.replace("{","").replace("}","")));
            }
        }

        doc.append("\n");
        doc.append(String.format("请求参数: \n"));
        doc.append(String.format("|参数名|类型|是否必填|默认值|描述|示例值|\n"));
        doc.append(String.format("|----|----|----|----|----|------| \n"));
        for (MarkdownParam p : requestEntity) {
            doc.append(String.format("|%s|%s|%s|%s|%s| %s|\n",p.getName(),p.getType(),p.getMust(),p.getDef(),p.getDesc(),p.getSimple()));
        }
        doc.append("\n");
        doc.append(String.format("响应参数: \n"));
        doc.append(String.format("|参数名|类型|是否必填|默认值|描述|示例值|\n"));
        doc.append(String.format("|----|----|----|----|------|------| \n"));
        for (MarkdownParam p : responseEntity) {
            doc.append(String.format("|%s|%s|%s|%s|%s|%s| \n",p.getName(),p.getType(),p.getMust(),p.getDef(),p.getDesc(),p.getSimple()));
        }
        doc.append("\n");

        if(getSampleCode() != null) {
            doc.append(String.format("响应示例: \n"));
            doc.append(String.format("``` \n"));
            doc.append(getSampleCode());
            doc.append(String.format("``` \n"));
        }
        return doc.toString();
    }

}
