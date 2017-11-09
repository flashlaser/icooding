package com.icooding.practice.nio;


import io.netty.handler.codec.http.HttpResponseStatus;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * project_name icooding-practice
 * class HttpResponse
 * date  2017/11/9
 * author ibm
 * version 1.0
 */
public class HttpResponse {
    private String protocol = "HTTP/1.1";
    private HttpResponseStatus status = HttpResponseStatus.OK;
    private String host;
    private String body;

    public Map<String,String> headers = new HashMap<String,String>() ;

    public HttpResponse(String protocol, HttpResponseStatus status, HttpRequest request, String body) {
        this.protocol = protocol;
        this.status = status;
        this.host = request.getIpaddress();
        this.body = body;
        headers.put("Accept","text/html");
        headers.put("Accept-Encoding","gzip");
        headers.put("Accept-Language","zh-CN,en-US");
        headers.put("Content-Type",ContentType.parse(request.getUrl()).value+"; charset=utf-8");
        headers.put("Content-Length",String.valueOf(getBody().length()));
        headers.put("Server", NioFileServer.FILE_SERVER_NAME);
        headers.put("Connection","keep-alive");
    }

    public HttpResponse(HttpRequest request, String body) {
        this("HTTP/1.1",HttpResponseStatus.OK,request,body);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public HttpResponseStatus getStatus() {
        return status;
    }

    public void setStatus(HttpResponseStatus status) {
        this.status = status;
    }

    public String getBody() {
        return body == null?"":body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    @Override
    public String toString() {
        StringBuffer html = new StringBuffer();
        html.append(String.format("%s %s %s\r\n",protocol,status.code(),status.reasonPhrase()));
        html.append(String.format("Host:%s\r\n", host));
        headers.forEach((key,value) -> html.append(String.format("%s:%s\r\n", key,value)));
        html.append(String.format("\r\n"));
        html.append(getBody());
        html.append(String.format("\r\n"));
        return html.toString();
    }

    public ByteBuffer toByteBuffer(){
        String content = toString();
        return ByteBuffer.wrap(content.getBytes());
    }
}
