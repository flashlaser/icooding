package com.icooding.practice.nio;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.*;

/**
 * project_name icooding-practice
 * class NIO
 * date  2017/11/7
 * author ibm
 * version 1.0
 */
public class NIO {
    RandomAccessFile randomAccessFile;
    private static Charset charset = Charset.forName("US-ASCII");
    private static CharsetEncoder encoder = charset.newEncoder();
    @Before
    public void before() throws Exception{
        URL resource = NIO.class.getResource("/");
        String path = resource.getPath();
        randomAccessFile = new RandomAccessFile(path+"/nio-data.txt","rw");
    }

    @Test
    public void clear() throws Exception{
        randomAccessFile.setLength(0);
    }



    @Test
    public void testBuffer() throws Exception{
        FileChannel channel = randomAccessFile.getChannel();
        long size = channel.size();
        String data = "DatagramChannel 能通过UDP读写网络中的数据。\n";
        ByteBuffer byteBuffer = ByteBuffer.allocate(data.getBytes().length);
        System.out.println(byteBuffer.position());
        byteBuffer.put(data.getBytes());
        System.out.println(byteBuffer.position());

        byteBuffer.flip();
        System.out.println(byteBuffer.position());
        if(size == 0L){
            channel.write(byteBuffer);
        }else {
            channel.write( byteBuffer, (int) size);
        }
        channel.close();
    }


    public String getRequestStr(InputStream inputStream) throws IOException{
        StringBuffer stringBuffer = new StringBuffer();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String line = null;
        while (!isNotEmpty(line = reader.readLine())){
            stringBuffer.append(line+"\r\n");
        }
        return stringBuffer.toString();
    }

    public boolean isNotEmpty(String str){
        return str == null || str.trim().equals("");
    }

    @Test
    public void chanl() throws Exception{
        int port = 30;
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverChannel.socket();
        serverSocket.bind(new InetSocketAddress(port));
        Selector selector = Selector.open();
//
//        serverChannel.configureBlocking(false);
//        serverChannel.register(selector,SelectionKey.OP_ACCEPT|SelectionKey.OP_CONNECT|SelectionKey.OP_READ|SelectionKey.OP_WRITE);
        while (true){
            Socket accept = serverSocket.accept();
            try(SocketChannel channel = accept.getChannel()) {
//                String in = getRequestStr(accept.getInputStream());
//                SocketRequest request = new SocketRequest(accept);
//                request.readData();
//                System.out.println(request.getData());
                String date = new Date().toString();
                StringBuffer html = new StringBuffer();
                StringBuffer content = new StringBuffer();
                content.append(String.format("<!DOCTYPE HTML>"));
                content.append(String.format("<html>"));
                content.append(String.format("<head>"));
                content.append(String.format("<title>server date "));
                content.append(String.format("</title>"));
                content.append(String.format("</head>"));
                content.append(String.format("<body>"));
                content.append(String.format("<p/>IP:%s",accept.getRemoteSocketAddress()));
                content.append(String.format("<p/>date:%s",date));
                content.append(String.format("</body>"));
                content.append(String.format("</html>"));

                html.append(String.format("HTTP/1.1 200 OK\r\n"));
                html.append(String.format("Host:%s\r\n",accept.getRemoteSocketAddress()));
                html.append(String.format("Accept:text/html\r\n"));
                html.append(String.format("Accept-Charset:UTF-8,iso-8859-1.bg2132\r\n"));
                html.append(String.format("Accept-Encoding:gzip\r\n"));
                html.append(String.format("Content-Type:text/html; charset=utf-8\r\n"));
                html.append(String.format("Content-Length:%s\r\n",content.length()));
                html.append(String.format("Server:NGINS\r\n"));
                html.append(String.format("Connection:keep-alive\r\n"));
                html.append(String.format("\r\n"));
                html.append(content.toString());
                html.append(String.format("\r\n"));
                channel.write(ByteBuffer.wrap(html.toString().getBytes()));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }



    @Test
    public void set(){
        Set<String> stringSet = new HashSet<String>();
        stringSet.add("A");
        Assert.assertTrue(stringSet.add("A"));
    }

    @Test
    public void hashmap(){
        Map<String,String> map = new HashMap<String,String>();
        for (int i = 0; i < 10; i++) {
            map.put("i"+i,i+"");
        }

        map.forEach((key,value) -> System.out.println(key+":"+value));
    }

    private void readDataFromSocket(SelectionKey key) {
        System.out.println(key);
    }







//    @Test
//    public void te(){
//        String mina = "application/envoy\tevy\n" +
//                "application/fractals\tfif\n" +
//                "application/futuresplash\tspl\n" +
//                "application/hta\thta\n" +
//                "application/internet-property-stream\tacx\n" +
//                "application/mac-binhex40\thqx\n" +
//                "application/msword\tdoc\n" +
//                "application/msword\tdot\n" +
//                "application/octet-stream\t*\n" +
//                "application/octet-stream\tbin\n" +
//                "application/octet-stream\tclass\n" +
//                "application/octet-stream\tdms\n" +
//                "application/octet-stream\texe\n" +
//                "application/octet-stream\tlha\n" +
//                "application/octet-stream\tlzh\n" +
//                "application/oda\toda\n" +
//                "application/olescript\taxs\n" +
//                "application/pdf\tpdf\n" +
//                "application/pics-rules\tprf\n" +
//                "application/pkcs10\tp10\n" +
//                "application/pkix-crl\tcrl\n" +
//                "application/postscript\tai\n" +
//                "application/postscript\teps\n" +
//                "application/postscript\tps\n" +
//                "application/rtf\trtf\n" +
//                "application/set-payment-initiation\tsetpay\n" +
//                "application/set-registration-initiation\tsetreg\n" +
//                "application/vnd.ms-excel\txla\n" +
//                "application/vnd.ms-excel\txlc\n" +
//                "application/vnd.ms-excel\txlm\n" +
//                "application/vnd.ms-excel\txls\n" +
//                "application/vnd.ms-excel\txlt\n" +
//                "application/vnd.ms-excel\txlw\n" +
//                "application/vnd.ms-outlook\tmsg\n" +
//                "application/vnd.ms-pkicertstore\tsst\n" +
//                "application/vnd.ms-pkiseccat\tcat\n" +
//                "application/vnd.ms-pkistl\tstl\n" +
//                "application/vnd.ms-powerpoint\tpot\n" +
//                "application/vnd.ms-powerpoint\tpps\n" +
//                "application/vnd.ms-powerpoint\tppt\n" +
//                "application/vnd.ms-project\tmpp\n" +
//                "application/vnd.ms-works\twcm\n" +
//                "application/vnd.ms-works\twdb\n" +
//                "application/vnd.ms-works\twks\n" +
//                "application/vnd.ms-works\twps\n" +
//                "application/winhlp\thlp\n" +
//                "application/x-bcpio\tbcpio\n" +
//                "application/x-cdf\tcdf\n" +
//                "application/x-compress\tz\n" +
//                "application/x-compressed\ttgz\n" +
//                "application/x-cpio\tcpio\n" +
//                "application/x-csh\tcsh\n" +
//                "application/x-director\tdcr\n" +
//                "application/x-director\tdir\n" +
//                "application/x-director\tdxr\n" +
//                "application/x-dvi\tdvi\n" +
//                "application/x-gtar\tgtar\n" +
//                "application/x-gzip\tgz\n" +
//                "application/x-hdf\thdf\n" +
//                "application/x-internet-signup\tins\n" +
//                "application/x-internet-signup\tisp\n" +
//                "application/x-iphone\tiii\n" +
//                "application/x-javascript\tjs\n" +
//                "application/x-latex\tlatex\n" +
//                "application/x-msaccess\tmdb\n" +
//                "application/x-mscardfile\tcrd\n" +
//                "application/x-msclip\tclp\n" +
//                "application/x-msdownload\tdll\n" +
//                "application/x-msmediaview\tm13\n" +
//                "application/x-msmediaview\tm14\n" +
//                "application/x-msmediaview\tmvb\n" +
//                "application/x-msmetafile\twmf\n" +
//                "application/x-msmoney\tmny\n" +
//                "application/x-mspublisher\tpub\n" +
//                "application/x-msschedule\tscd\n" +
//                "application/x-msterminal\ttrm\n" +
//                "application/x-mswrite\twri\n" +
//                "application/x-netcdf\tcdf\n" +
//                "application/x-netcdf\tnc\n" +
//                "application/x-perfmon\tpma\n" +
//                "application/x-perfmon\tpmc\n" +
//                "application/x-perfmon\tpml\n" +
//                "application/x-perfmon\tpmr\n" +
//                "application/x-perfmon\tpmw\n" +
//                "application/x-pkcs12\tp12\n" +
//                "application/x-pkcs12\tpfx\n" +
//                "application/x-pkcs7-certificates\tp7b\n" +
//                "application/x-pkcs7-certificates\tspc\n" +
//                "application/x-pkcs7-certreqresp\tp7r\n" +
//                "application/x-pkcs7-mime\tp7c\n" +
//                "application/x-pkcs7-mime\tp7m\n" +
//                "application/x-pkcs7-signature\tp7s\n" +
//                "application/x-sh\tsh\n" +
//                "application/x-shar\tshar\n" +
//                "application/x-shockwave-flash\tswf\n" +
//                "application/x-stuffit\tsit\n" +
//                "application/x-sv4cpio\tsv4cpio\n" +
//                "application/x-sv4crc\tsv4crc\n" +
//                "application/x-tar\ttar\n" +
//                "application/x-tcl\ttcl\n" +
//                "application/x-tex\ttex\n" +
//                "application/x-texinfo\ttexi\n" +
//                "application/x-texinfo\ttexinfo\n" +
//                "application/x-troff\troff\n" +
//                "application/x-troff\tt\n" +
//                "application/x-troff\ttr\n" +
//                "application/x-troff-man\tman\n" +
//                "application/x-troff-me\tme\n" +
//                "application/x-troff-ms\tms\n" +
//                "application/x-ustar\tustar\n" +
//                "application/x-wais-source\tsrc\n" +
//                "application/x-x509-ca-cert\tcer\n" +
//                "application/x-x509-ca-cert\tcrt\n" +
//                "application/x-x509-ca-cert\tder\n" +
//                "application/ynd.ms-pkipko\tpko\n" +
//                "application/zip\tzip\n" +
//                "audio/basic\tau\n" +
//                "audio/basic\tsnd\n" +
//                "audio/mid\tmid\n" +
//                "audio/mid\trmi\n" +
//                "audio/mpeg\tmp3\n" +
//                "audio/x-aiff\taif\n" +
//                "audio/x-aiff\taifc\n" +
//                "audio/x-aiff\taiff\n" +
//                "audio/x-mpegurl\tm3u\n" +
//                "audio/x-pn-realaudio\tra\n" +
//                "audio/x-pn-realaudio\tram\n" +
//                "audio/x-wav\twav\n" +
//                "image/bmp\tbmp\n" +
//                "image/cis-cod\tcod\n" +
//                "image/gif\tgif\n" +
//                "image/ief\tief\n" +
//                "image/jpeg\tjpe\n" +
//                "image/jpeg\tjpeg\n" +
//                "image/jpeg\tjpg\n" +
//                "image/pipeg\tjfif\n" +
//                "image/svg+xml\tsvg\n" +
//                "image/tiff\ttif\n" +
//                "image/tiff\ttiff\n" +
//                "image/x-cmu-raster\tras\n" +
//                "image/x-cmx\tcmx\n" +
//                "image/x-icon\tico\n" +
//                "image/x-portable-anymap\tpnm\n" +
//                "image/x-portable-bitmap\tpbm\n" +
//                "image/x-portable-graymap\tpgm\n" +
//                "image/x-portable-pixmap\tppm\n" +
//                "image/x-rgb\trgb\n" +
//                "image/x-xbitmap\txbm\n" +
//                "image/x-xpixmap\txpm\n" +
//                "image/x-xwindowdump\txwd\n" +
//                "message/rfc822\tmht\n" +
//                "message/rfc822\tmhtml\n" +
//                "message/rfc822\tnws\n" +
//                "text/css\tcss\n" +
//                "text/h323\t323\n" +
//                "text/html\thtm\n" +
//                "text/html\thtml\n" +
//                "text/html\tstm\n" +
//                "text/iuls\tuls\n" +
//                "text/plain\tbas\n" +
//                "text/plain\tc\n" +
//                "text/plain\th\n" +
//                "text/plain\ttxt\n" +
//                "text/richtext\trtx\n" +
//                "text/scriptlet\tsct\n" +
//                "text/tab-separated-values\ttsv\n" +
//                "text/webviewhtml\thtt\n" +
//                "text/x-component\thtc\n" +
//                "text/x-setext\tetx\n" +
//                "text/x-vcard\tvcf\n" +
//                "video/mpeg\tmp2\n" +
//                "video/mpeg\tmpa\n" +
//                "video/mpeg\tmpe\n" +
//                "video/mpeg\tmpeg\n" +
//                "video/mpeg\tmpg\n" +
//                "video/mpeg\tmpv2\n" +
//                "video/quicktime\tmov\n" +
//                "video/quicktime\tqt\n" +
//                "video/x-la-asf\tlsf\n" +
//                "video/x-la-asf\tlsx\n" +
//                "video/x-ms-asf\tasf\n" +
//                "video/x-ms-asf\tasr\n" +
//                "video/x-ms-asf\tasx\n" +
//                "video/x-msvideo\tavi\n" +
//                "video/x-sgi-movie\tmovie\n" +
//                "x-world/x-vrml\tflr\n" +
//                "x-world/x-vrml\tvrml\n" +
//                "x-world/x-vrml\twrl\n" +
//                "x-world/x-vrml\twrz\n" +
//                "x-world/x-vrml\txaf\n" +
//                "x-world/x-vrml\txof";
//
//
//
//        Map<String,String > mia = new HashMap<>();
//        String[] split = mina.split("\n");
//        for (String s : split) {
//            String[] split1 = s.split("\t");
//            mia.put(split1[1].trim(),split1[0].trim());
//        }
//
//        for (Map.Entry<String, String> stringStringEntry : mia.entrySet()) {
////            String key  = stringStringEntry.getValue().split("/")[0].toUpperCase()+"_"+stringStringEntry.getKey().toUpperCase();
////            System.out.println("public final static String "+key+" = \""+stringStringEntry.getValue()+"\"; ");
////            System.out.println(key+"(\""+stringStringEntry.getKey()+"\",\""+stringStringEntry.getValue()+"\"),");
//        }
//
//
//    }




}
