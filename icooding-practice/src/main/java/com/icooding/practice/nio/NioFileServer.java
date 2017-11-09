package com.icooding.practice.nio;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;

/**
 * project_name icooding-practice
 * class FileServer
 * date  2017/11/8
 * author ibm
 * version 1.0
 */
public class NioFileServer {

    public static final String FILE_SERVER_NAME = "文件服务器-玩具版";
    public static final String DEFAULT_INDEX_PATH = "/";
    public static final String WEB_ROOT_DIR = "E:/nginx-1.13.4/html";
    public static final Integer DEFAULT_PORT = 9100;
    public static final String DEFAULT_CHARSET = "UTF-8";

    private HttpRequest request;

    public File[] getFileList(String dir) {
        return new File(getContextPath(dir)).listFiles();
    }


    public String getContextPath(String path) {
        return WEB_ROOT_DIR + path;
    }

    public String getHtml(File[] files) {
        String date = new Date().toString();
        StringBuffer content = new StringBuffer();
        content.append(String.format("<!DOCTYPE HTML>"));
        content.append(String.format("<html>"));
        content.append(String.format("<head>"));
        content.append(String.format("<title>文件服务器"));
        content.append(String.format("</title>"));
        content.append(String.format("</head>"));
        content.append(String.format("<body>"));
        content.append(String.format("<p/>时间:%s", date));
        content.append(String.format("<ul>"));
        if(null != files){
            for (File f : files) {
                if (f.isDirectory()) {
                    content.append(String.format("<li><a href='%s' >%s</a></li>", toContextPath(f.getName()), f.getName()));
                } else {
                    content.append(String.format("<li><a href='%s' >%s</a></li>", toContextPath(f.getName()),f.getName()));
                }
            }
        }
        content.append(String.format("</ul>"));
        content.append(String.format("</body>"));
        content.append(String.format("</html>"));
        return content.toString();
    }

    public String toContextPath(String name){
        return (request.getUrl() + DEFAULT_INDEX_PATH + name).replaceAll("//","/");
    }

    public void start(Integer port) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(port));
        while (true) {
            try (SocketChannel channel = serverSocketChannel.accept()) {
                request = HttpRequest.parse(getRequestStr(channel.socket().getInputStream()));
                request.setIpaddress(channel.getRemoteAddress().toString());

                File file = new File(getContextPath(request.getUrl()));
                if(!file.isDirectory()){
                    try {
                        RandomAccessFile randomAccessFile = new RandomAccessFile(file,"r");
                        ByteBuffer allocate = ByteBuffer.allocate(1024);
                        FileChannel channel1 = randomAccessFile.getChannel();
                        channel.write(ByteBuffer.wrap("HTTP/1.1 200 OK\r\n".getBytes()));
                        channel.write(ByteBuffer.wrap(("Host:"+request.getIpaddress()+"\r\n").getBytes()));
                        channel.write(ByteBuffer.wrap(String.format("Accept:%s\r\n",ContentType.parse(request.getUrl()).value).getBytes()));
                        channel.write(ByteBuffer.wrap(String.format("Accept-Encoding:%s\r\n","gzip").getBytes()));
                        channel.write(ByteBuffer.wrap(String.format("Accept-Language:%s\r\n","zh-CN,en-US").getBytes()));
                        channel.write(ByteBuffer.wrap(String.format("Content-Type:%s\r\n",ContentType.parse(request.getUrl()).value+"; charset=utf-8").getBytes()));
                        channel.write(ByteBuffer.wrap(String.format("Content-Length:%s\r\n",randomAccessFile.length()).getBytes()));
                        channel.write(ByteBuffer.wrap(String.format("Server:%s\r\n", NioFileServer.FILE_SERVER_NAME).getBytes()));
                        channel.write(ByteBuffer.wrap(String.format("Connection:%s\r\n","keep-alive").getBytes()));
                        channel.write(ByteBuffer.wrap("\r\n".getBytes()));
                        while (channel1.read(allocate) != -1){
                            allocate.flip();
                            channel.write(allocate);
                            allocate.clear();
                        }
                        channel.write(ByteBuffer.wrap("\r\n".getBytes()));

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }else {
                    File[] files = file.listFiles();
                    String html = getHtml(files);
                    HttpResponse response = new HttpResponse(request, html);
                    channel.write(response.toByteBuffer());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    public String getRequestStr(InputStream inputStream) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String line = null;
        while (!isNotEmpty(line = reader.readLine())) {
            stringBuffer.append(line + "\r\n");
        }
        return stringBuffer.toString();
    }


    public boolean isNotEmpty(String str) {
        return str == null || str.trim().equals("");
    }

    public static void main(String[] args) throws IOException {
        int port = DEFAULT_PORT;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        NioFileServer fileServer = new NioFileServer();
        fileServer.start(port);
    }
}
