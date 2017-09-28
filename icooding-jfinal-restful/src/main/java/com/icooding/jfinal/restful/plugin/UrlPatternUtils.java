package com.icooding.jfinal.restful.plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlPatternUtils {


    private final static String regex  = "\\{\\w+\\}";
    private final static String seat  = "*";

    public static String replace(String url){
        return url.replaceAll(regex, seat);
    }

    public static boolean isPattern(String reg,String url){
        String rege = reg.replace(seat,"\\w+");
        return Pattern.matches(rege,url);
    }

    /**
     * 提取根据rule 提取path中的参数
     * @param rule   带占位符的URL /orgs/{orgName}/apis/{apiName}/assets
     * @param path   真实URL       /orgs/dev/apis/dts/assets
     * @return   返回:  {apiName=dts, orgName=dev}
     */
    public static Map<String,String> match(String rule, String path) {
        rule = rule.replace("{","<").replace("}",">");
        System.out.println(rule+":"+path);
        StringBuilder pathRule = new StringBuilder();
        List<String> params = new ArrayList<String>();
        Pattern ruleSyntax = Pattern.compile("<(\\w*)>");
        Matcher ruleMatcher = ruleSyntax.matcher(rule);
        int offset = 0;
        while (ruleMatcher.find()) {
            int groupOffset = ruleMatcher.start();
            pathRule.append(rule.substring(offset, groupOffset));
            String groupName = ruleMatcher.group(1);
            params.add(groupName);
            // 拼接成 (?<name>[^/]+) 的正则表达式
            pathRule.append("(?<").append(groupName).append(">[^/]+)");
            offset = ruleMatcher.end();
        }
        if (offset < rule.length()) {
            pathRule.append(rule.substring(offset, rule.length()));
        }
        System.out.println("pathRule:"+pathRule);
        Pattern pathPattern = Pattern.compile("^" + pathRule.toString() + "$");
        Matcher pathMatcher = pathPattern.matcher(path);
        Map<String,String> result = new HashMap<String,String>();
        if (pathMatcher.matches()) {
            for (String param : params) {
                result.put(param, pathMatcher.group(param));
            }
        }
        return result;
    }

    /**
     * 判断URL是否能匹配上
     * @param regexPath  正则表达式类型的URL ^/orgs/(\w*)/apis/(\w*)/assets$
     * @param path        真实Path  /orgs/dev/apis/dts/assets
     * @return    true
     */
    public static boolean isHttpPathMatching(String regexPath,String path){
        return Pattern.compile(regexPath).matcher(path).find();
    }


    /**
     * 将URL编译为一个 正则表达式
     * @param url /orgs/{orgName}/apis/{apiName}/assets => ^/orgs/(\w*)/apis/(\w*)/assets$
     * @return
     */
    public static Pattern compile(String url){
        return Pattern.compile("^"+url.replaceAll(regex,"(\\\\w*)")+"$");
    }

}
