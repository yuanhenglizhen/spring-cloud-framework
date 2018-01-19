
package com.huilian.hlej.common.core.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 *  HttpClient工具类 
 *  类            名:   HttpClientUtils
 *  修 改 记 录:   //修改历史记录，包括修改日期、修改者及修改内容
 *  版 权 所 有:   版权所有(C)2017-2017
 *  公             司:  汇联金融服务控股有限公司
 *  @version  V1.0
 *  @date     2017年3月10日
 *  @author   qinlinhai
 *
 */
public class HttpClientUtils {

	//编码格式。发送编码格式统一用UTF-8
    private static String ENCODING = "UTF-8";
    
	private static final String APPLICATION_JSON = "application/json";
	private static final String APPLICATION_JSON_UTF_8 = "application/json; charset=UTF-8";
    /**
     * 基于HttpClient 4.3的通用GET方法
     * @param url       提交的URL
     * @param paramsMap 提交<参数，值>Map
     * @return 提交响应
     */
    public static String get(String url, String params) {
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpGet method = new HttpGet(url+(params == null?"":"?"+params));
            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseText;
    }
    
    /**
     * 基于HttpClient 4.3的通用GET方法
     * @param url       提交的URL
     * @param paramsMap 提交<参数，值>Map
     * @return 提交响应
     */
    public static int getByStatus(String url, String params) {
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            HttpGet method = new HttpGet(url+(params == null?"":"?"+params));
            response = client.execute(method);
            return response.getStatusLine().getStatusCode();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 404;
    }
    
    /**
     * 基于HttpClient 4.3的通用GET方法
     * @param url       提交的URL
     * @param paramsMap 提交<参数，值>Map
     * @return 提交响应
     */
    public static String get(String url, Map<String, String> paramsMap) {
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpGet method = new HttpGet(url);
            if (paramsMap != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                    paramList.add(pair);
                }
            }
            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseText;
    }
	/**
     * 基于HttpClient 4.3的通用POST方法
     * @param url       提交的URL
     * @param paramsMap 提交<参数，值>Map
     * @return 提交响应
     */
    public static String post(String url, Map<String, String> paramsMap) {
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(url);
            if (paramsMap != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                    paramList.add(pair);
                }
                method.setEntity(new UrlEncodedFormEntity(paramList, ENCODING));
            }
            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseText;
    }
    /**
     * 基于HttpClient 4.3的通用POST方法
     * @param url       提交的URL
     * @param paramsMap 提交<参数，值>Map
     * @return 提交响应
     */
    public static JSONObject postJson(String url, Map<String, String> paramsMap) {
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        BufferedReader bufferedReader = null;
        try {
            HttpPost method = new HttpPost(url);
            if (paramsMap != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                    paramList.add(pair);
                }
                method.setEntity(new UrlEncodedFormEntity(paramList, ENCODING));
            }
            response = client.execute(method);
            HttpEntity httpEntity=response.getEntity(); 
            if (httpEntity!=null) { 
            	StringBuilder sb = new StringBuilder();
                try { 
                    bufferedReader=new BufferedReader
                    (new InputStreamReader(httpEntity.getContent(), "UTF-8"), 8*1024); 
                    String line=null; 
                    while ((line=bufferedReader.readLine())!=null) { 
                        sb.append(line); 
                    } 
                    //利用从HttpEntity中得到的String生成JsonObject 
                    return JSONObject.parseObject(sb.toString());
                } catch (Exception e) { 
                    e.printStackTrace(); 
                } 
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
                if(bufferedReader != null){
                	bufferedReader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public static String httpPost(String httpUrl, String httpJson) {
		StringBuffer result = new StringBuffer();
		try {
			URL url = new URL(httpUrl);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestProperty("content-type", "text/xml");
			httpURLConnection.setRequestProperty("Accept-Charset", "UTF-8");
			httpURLConnection.setRequestProperty("contentType", "UTF-8");
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.connect();
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8"));
			out.write(httpJson);
			out.flush();
			BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
			while (true) {
				String line = in.readLine();
				if (line == null) {
					break;
				} else {
					result.append(line);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	
	
	public static String httpGet(String httpUrl) {
		StringBuffer result = new StringBuffer();
		try {
			URL url = new URL(httpUrl);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestProperty("content-type", "text/xml");
			httpURLConnection.setRequestProperty("Accept-Charset", "UTF-8");
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
			while (true) {
				String line = in.readLine();
				if (line == null) {
					break;
				} else {
					result.append(line);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	
	public static String postJson1(String url,Map<String,String> headerMap, Map<String, String> paramsMap) {
		CloseableHttpClient client = HttpClients.createDefault();
		String responseText = "";
		CloseableHttpResponse response = null;
		try {
			HttpPost method = new HttpPost(url);
			method.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON_UTF_8);
			//加入头部信息
			if(headerMap != null){
				for (Map.Entry<String, String> param : headerMap.entrySet()) {
					method.addHeader(param.getKey(), param.getValue());
				}
			}
			//加入参数信息
			if (paramsMap != null) {
				String encoderJson = JSON.toJSONString(paramsMap);
				StringEntity se = new StringEntity(encoderJson,"UTF-8");
		        se.setContentType(APPLICATION_JSON_UTF_8);
		        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
				method.setEntity(se);
			}
			response = client.execute(method);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				responseText = EntityUtils.toString(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return responseText;
	}
	
    public static void main(String[] args) {
    	String url = "http://localhost:8080/HuishApiCommWeb/api/user/user";
		post(url, new HashMap<String,String>());
	}
}
