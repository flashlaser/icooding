package com.icooding.cms.utils;

import com.google.gson.Gson;
import com.icooding.cms.dto.GlobalSetting;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;

import java.io.InputStream;
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




}
