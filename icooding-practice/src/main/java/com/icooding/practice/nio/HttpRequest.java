package com.icooding.practice.nio;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * project_name icooding-practice
 * class HttpRequest
 * date  2017/11/9
 * author ibm
 * version 1.0
 */
public class HttpRequest {

    private String ipaddress;
    private String method;
    private String url;
    private String protocol;

    public Map<String,String> headers = new HashMap<>();



    private HttpRequest() {
    }


    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress.replace("/","");
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public static HttpRequest parse(String header) throws IOException{
        HttpRequest request = new HttpRequest();
        String[] split = header.split("\r\n");
        String[] http = split[0].split(" ");
        request.setMethod(http[0]);
        if(http[1].indexOf("?") != -1){
            http[1] = http[1].substring(0,http[1].indexOf("?"));
        }
        request.setUrl(URLDecoder.decode(http[1], NioFileServer.DEFAULT_CHARSET));


        request.setProtocol(http[2]);
        for (int i = 1; i < split.length; i++) {
            String[] head = split[i].split(":");
            request.getHeaders().put(head[0].trim(),head[1].trim());
        }
        return request;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "ipaddress='" + ipaddress + '\'' +
                ", method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", protocol='" + protocol + '\'' +
                ", headers=" + headers +
                '}';
    }
}
