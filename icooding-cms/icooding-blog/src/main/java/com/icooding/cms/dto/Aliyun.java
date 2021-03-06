package com.icooding.cms.dto;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.opensearch.javasdk.CloudsearchClient;
import com.opensearch.javasdk.CloudsearchSearch;
import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;


/**
 * 主要用于阿里云产品的操作，单例模式
 * 
 * @author 幻幻
 * 
 */
public class Aliyun {
    
    private static final Logger LOG = Logger.getLogger(Aliyun.class);

	private static Aliyun aliyun = new Aliyun();
	private String accessKeyId;
	private String accessKeySecret;
	private String ossUrl;
	private String ossBucket;
	private String ossEndpoint;
	// open search的appName
	private String appName;
	private static OSSClient ossClient;
	private static CloudsearchClient openSearchClient;
	private static CloudsearchSearch search;
//	private static CloudsearchSuggest suggest;

	private Aliyun() {
		super();
	}

	public void init(String accessKeyId, String accessKeySecret) {
		this.accessKeyId = accessKeyId;
		this.accessKeySecret = accessKeySecret;
	}

	public void initOSS(String endpoint, String ossUrl, String ossBucket,
			String ossEndpoint) {
		ClientConfiguration conf = new ClientConfiguration();
		conf.setConnectionTimeout(5000);
		conf.setMaxErrorRetry(10);
		this.ossBucket = ossBucket;
		this.ossUrl = ossUrl;
		this.ossEndpoint = ossEndpoint;
		ossClient = new OSSClient("http://" + endpoint, accessKeyId,
				accessKeySecret, conf);
	}

	public void initOpenSearch(String endpoint, String appName) {
		Map<String, Object> opts = new HashMap<String, Object>();
		this.appName = appName;
		// 这里的host需要根据访问应用详情页中提供的的API入口来确定
//		try {
//			openSearchClient = new CloudsearchClient(accessKeyId, accessKeySecret, endpoint, opts, KeyTypeEnum.ALIYUN);
//			search = new CloudsearchSearch(openSearchClient);
//			String indexName = "fate_blog";
//	        String suggestName = "nana";
//			suggest = new CloudsearchSuggest(indexName, suggestName, openSearchClient);
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}

	public static Aliyun getInstance() {
		return aliyun;
	}

	public static OSSClient getOSSClient() {
		return ossClient;
	}

	public static CloudsearchClient getOpenSearchClient() {
		return openSearchClient;
	}

	public String search(String keyword, int curPage, int per)
			throws ClientProtocolException, IOException {
		search.clear();
		// 添加指定搜索的应用：
		search.addIndex(appName);
		// 指定搜索的关键词，这里要指定在哪个索引上搜索，如果不指定的话默认在使用“default”索引（索引字段名称是您在您的数据结构中的“索引到”字段。）
		search.setQueryString("default:'" + keyword + "'");
		// 分页
		search.addCustomConfig("start", (curPage - 1) * per);
		search.addCustomConfig("hit", per);
		// 指定搜索返回的格式。
		search.setFormat("json");
		// 设定过滤条件
		// search.addFilter("price>10");
		// 设定排序方式 + 表示正序 - 表示降序
		search.addSort("publish_date", "-");
		// 返回搜索结果
		return search.search();
	}
	
	public Object suggest(String query){
//		try {
//            suggest.setHit(10);
//            suggest.setQuery(query);
//            String result = suggest.search();
//
//            JSONObject jsonResult = new JSONObject(result);
//            List<String> suggestions = new ArrayList<String>();
//
//            if (!jsonResult.has("errors")) {
//                JSONArray itemsJsonArray = (JSONArray) jsonResult.get("suggestions");
//                for (int i = 0; i < itemsJsonArray.length(); i++){
//                    JSONObject item = (JSONObject) itemsJsonArray.get(i);
//                    suggestions.add(item.getString("suggestion"));
//                }
//                Map<String,Object> ret = new HashMap<String,Object>();
//                ret.put("result",suggestions);
//                ret.put("status","OK");
//                return ret;
//            } else {
//                LOG.info("获取搜索候选词失败");
//            }
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (ClientProtocolException e) {
//        	e.printStackTrace();
//        } catch (IOException e) {
//        	e.printStackTrace();
//        } catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return null;
	}

	public String getOssUrl() {
		return ossUrl;
	}

	public String getOssBucket() {
		return ossBucket;
	}

	public String getOssEndpoint() {
		return ossEndpoint;
	}

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

}
