package com.icooding.cms.utils;

import com.google.gson.Gson;
import com.icooding.cms.dto.GlobalSetting;
import com.icooding.weibo.http.HttpClient;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import org.apache.http.client.utils.HttpClientUtils;
import sun.security.ssl.SSLContextImpl;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.UUID;

/**
 * project_name icooding-cms
 * class QiniuCloudUtils
 * date  2017/11/24
 * author ibm
 * version 1.0
 */
public class QiniuCloudUtils {

    static String accessKey = "ISvHZm5Xhg_LGne9mlJpkaOfvt-K3qiAh_4flz1K";
    static String secretKey = "0qFrKgY4UWmdiC-slAOPJppy2gN5hS0ndNATrjRq";
    static String bucket = "icooding";
    static String prefix = "img/%s/";



    static Configuration cfg = new Configuration(Zone.zone2());
    static UploadManager uploadManager = new UploadManager(cfg);
    static Auth auth = Auth.create(accessKey, secretKey);


    public static String updateFile(String localFilePath,int id) throws QiniuException{
        String upToken = auth.uploadToken(bucket);
        String key = String.format(prefix+"%s.png",id, TokenUtil.uuid());
        Response response = uploadManager.put(localFilePath, key, upToken);
        //解析上传成功的结果
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        System.out.println(putRet.key);
        return GlobalSetting.getInstance().getImageServer() +"/"+ putRet.key;
    }


    public static String updateFile(byte[] file,int id) throws QiniuException{
        String upToken = auth.uploadToken(bucket);
        String key = String.format(prefix+"%s.png",id, TokenUtil.uuid());
        Response response = uploadManager.put(file, key, upToken);
        //解析上传成功的结果
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        System.out.println(putRet.key);
        return GlobalSetting.getInstance().getImageServer() +"/"+ putRet.key;
    }


    public static String updateFileByFilePath(String filePath,int id) throws QiniuException{
        String upToken = auth.uploadToken(bucket);
        String key = String.format(prefix+"%s.png",id, TokenUtil.uuid());
        File download = download(filePath);
        Response response = uploadManager.put(download, key, upToken,null,null,false);
        //解析上传成功的结果
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        System.out.println(putRet.key);
        return GlobalSetting.getInstance().getImageServer() +"/"+ putRet.key;
    }



    public static String[] listFiles(String prefix){
        BucketManager bucketManager = new BucketManager(auth, cfg);
        //每次迭代的长度限制，最大1000，推荐值 1000
        int limit = 1000;
            //指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
        String delimiter = "";
        //列举空间文件列表
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(bucket, prefix, limit, delimiter);

        FileInfo[] next = fileListIterator.next();
        String[] files = new String[next.length];
        for (int i = 0; i < next.length; i++) {
            files[i] = GlobalSetting.getInstance().getImageServer() +"/"+next[i].key;
        }
        return files;
    }



    private static File download(String path){
        InputStream inputStream =  null;
        OutputStream outputStream =  null;
        HttpURLConnection urlConnection = null;
        try {
            if(path.contains("https")){
                urlConnection = getHttpsURLConnection(path);
            }else{
                urlConnection = (HttpURLConnection) new URL(path).openConnection();
            }
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            urlConnection.setRequestProperty("accept-encoding","gzip, deflate, br");
            urlConnection.setRequestProperty("accept-language","zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7");
            urlConnection.setRequestProperty("cache-control","no-cache");
            urlConnection.setRequestProperty("pragma","no-cache");
            urlConnection.setRequestProperty("upgrade-insecure-requests","1");
            urlConnection.setRequestProperty("user-agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.89 Safari/537.36");
            inputStream = urlConnection.getInputStream();
            File file = new File(GlobalSetting.getInstance().getTemp_dir()+TokenUtil.uuid());
            outputStream = new FileOutputStream(file);
            byte[] b = new byte[1024];
            while (inputStream.read(b) > 0) {
                outputStream.write(b,0,b.length);
            }
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private static class DefaultTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    public static HttpsURLConnection getHttpsURLConnection(String uri) throws IOException {
        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() }, new SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        SSLSocketFactory ssf = ctx.getSocketFactory();
        URL url = new URL(uri);
        HttpsURLConnection httpsConn = (HttpsURLConnection) url.openConnection();
        httpsConn.setSSLSocketFactory(ssf);
        httpsConn.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }
        });
        httpsConn.setDoInput(true);
        httpsConn.setDoOutput(true);
        return httpsConn;
    }


}
