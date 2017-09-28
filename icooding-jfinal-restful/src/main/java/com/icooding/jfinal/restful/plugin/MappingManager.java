package com.icooding.jfinal.restful.plugin;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class MappingManager {

    static Map<String,Mapping> urlMappings = new HashMap<String,Mapping>();
    static Map<String,Mapping> regexMappings = new HashMap<String,Mapping>();

    public static void addUrlMapping(Mapping mapping){
        String key  = Mapping.toKey(mapping.getRestfulPath(),mapping.getMethod().name());
        if(mapping.isRegexPath()){
            regexMappings.put(key, mapping);
        }else {
            urlMappings.put(key, mapping);
        }
    }

    public static Mapping getUrlMapping(String url,String method){
        String key = Mapping.toKey(url,method);
        if(urlMappings.containsKey(key)){
            return urlMappings.get(key);
        }
        for (Map.Entry<String, Mapping> entry : regexMappings.entrySet()) {
            Mapping mapping = entry.getValue();
            if(mapping.getRegexPath().matcher(url).find() && StringUtils.equalsIgnoreCase(mapping.getMethod().name(),method)){
                String[] keys = mapping.getRestfulPath().split("/");
                String[] values = url.split("/");
                if(keys.length == values.length){
                    Map<String,String> params = new HashMap<String,String>();
                    for (int i = 0; i < keys.length; i++) {
                        params.put(cleanKey(keys[i]),values[i]);
                    }
                    mapping.setParams(params);
                }
                return mapping;
            }
        }
        return null;
    }

    private static String cleanKey(String key){
        return key.replace("{","").replace("}","");
    }

    public static void print(){
        for (Map.Entry<String, Mapping> entry : urlMappings.entrySet()) {
            System.out.println("MappingManager  " + entry.getValue().toString());
        }
        for (Map.Entry<String, Mapping> entry : regexMappings.entrySet()) {
            System.out.println("MappingManager  " + entry.getValue().toString());
        }
    }



}
