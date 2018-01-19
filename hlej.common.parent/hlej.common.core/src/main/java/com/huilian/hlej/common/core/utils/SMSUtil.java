package com.huilian.hlej.common.core.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
public class SMSUtil {
	private final static String URL = "http://hlej.sms.sys.com/msg/HttpBatchSendSM";
	private final static String ACCOUNT = "huilian_1";
	private final static String PASS_WORD = "Tch123456";
	
	private final static String ACCOUNTHLC = "huilianjr";
	private final static String PASS_WORDHLC = "Huil1234";
//	private final static String ACCOUNTHLC = "huilian";
//	private final static String PASS_WORDHLC = "Tch123456";
	

	private final static String ACCOUNTHSH = "huilian_2";
	private final static String PASS_WORDHSH = "Meng1234";
	
	private final static String ACCOUNTHLEJ = "hlyj-001";
	private final static String PASS_WORDHLEJ = "Meng1234";
	

	public static void sendSms_lc(String content, String mobiles) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(URL);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("account", ACCOUNT));
			nvps.add(new BasicNameValuePair("pswd", PASS_WORD));
			nvps.add(new BasicNameValuePair("mobile", mobiles));
			nvps.add(new BasicNameValuePair("msg", content));
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvps, "utf-8");
			httpPost.setEntity(formEntity);
			CloseableHttpResponse response = null;
			try {
				 response = httpclient.execute(httpPost);
				String retCode = EntityUtils.toString(response.getEntity());
				if (StringUtils.isNotEmpty(retCode)) {
					retCode = StringUtils.split(StringUtils.split(retCode, "\n")[0], ",")[1];
					if (!StringUtils.equalsIgnoreCase("0", retCode)) {
						throw new Exception(retCode);
					}
				}
			} finally {
				if(response != null) response.close();
			}
		} finally {
			httpclient.close();
		}
	}
	
	public static void sendSms_hlc(String content, String mobiles) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(URL);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("account", ACCOUNTHLC));
			nvps.add(new BasicNameValuePair("pswd", PASS_WORDHLC));
			nvps.add(new BasicNameValuePair("mobile", mobiles));
			nvps.add(new BasicNameValuePair("msg", content));
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvps, "utf-8");
			httpPost.setEntity(formEntity);
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				String retCode = EntityUtils.toString(response.getEntity());
				if (StringUtils.isNotEmpty(retCode)) {
					retCode = StringUtils.split(StringUtils.split(retCode, "\n")[0], ",")[1];
					if (!StringUtils.equalsIgnoreCase("0", retCode)) {
						throw new Exception(retCode);
					}
				}
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
	}
	
	public static void sendSms_hlej(String content, String mobiles) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(URL);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("account", ACCOUNTHLEJ));
			nvps.add(new BasicNameValuePair("pswd", PASS_WORDHLEJ));
			nvps.add(new BasicNameValuePair("mobile", mobiles));
			nvps.add(new BasicNameValuePair("msg", content));
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvps, "utf-8");
			httpPost.setEntity(formEntity);
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				String retCode = EntityUtils.toString(response.getEntity());
				if (StringUtils.isNotEmpty(retCode)) {
					retCode = StringUtils.split(StringUtils.split(retCode, "\n")[0], ",")[1];
					if (!StringUtils.equalsIgnoreCase("0", retCode)) {
						throw new Exception(retCode);
					}
				}
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
	}
	
	public static String sendSmsByHlej(String content, String mobiles){
		return sendSms(content, mobiles, ACCOUNTHLEJ,PASS_WORDHLEJ);
	}
	public static String sendSmsByHsh(String content, String mobiles){
		return sendSms(content, mobiles, ACCOUNTHSH,PASS_WORDHSH);
	}
	public static String sendSmsByHlc(String content, String mobiles){
		return sendSms(content, mobiles, ACCOUNTHLC,PASS_WORDHLC);
	}
	private static String sendSms(String content, String mobiles,String account,String passWord){
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			HttpPost httpPost = new HttpPost(URL);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("account", account));
			nvps.add(new BasicNameValuePair("pswd", passWord));
			nvps.add(new BasicNameValuePair("mobile", mobiles));
			nvps.add(new BasicNameValuePair("needstatus", String.valueOf(true)));
			nvps.add(new BasicNameValuePair("msg", content));
			nvps.add(new BasicNameValuePair("product", null));
			nvps.add(new BasicNameValuePair("extno", null));
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvps, "utf-8");
			httpPost.setEntity(formEntity);
			response = httpclient.execute(httpPost);
			String retCode = EntityUtils.toString(response.getEntity());
			if (StringUtils.isNotEmpty(retCode)) {
				retCode = StringUtils.split(StringUtils.split(retCode, "\n")[0], ",")[1];
				if (!StringUtils.equalsIgnoreCase("0", retCode)) {
					return retCode;
				}
			}
		}catch(ParseException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		} 
		finally {
			try {
				if(response != null )response.close();
				if(httpclient != null ) httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static void sendSms_hsh(String mobiles, String content) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(URL);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("account", ACCOUNTHSH));
			nvps.add(new BasicNameValuePair("pswd", PASS_WORDHSH));
			nvps.add(new BasicNameValuePair("mobile", mobiles));
			nvps.add(new BasicNameValuePair("needstatus", String.valueOf(true)));
			nvps.add(new BasicNameValuePair("msg", content));
			nvps.add(new BasicNameValuePair("product", null));
			nvps.add(new BasicNameValuePair("extno", null));
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvps, "utf-8");
			httpPost.setEntity(formEntity);
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				String retCode = EntityUtils.toString(response.getEntity());
				if (StringUtils.isNotEmpty(retCode)) {
					retCode = StringUtils.split(StringUtils.split(retCode, "\n")[0], ",")[1];
					if (!StringUtils.equalsIgnoreCase("0", retCode)) {
						throw new Exception(retCode);
					}
				}
			}finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
	}
	public static void sendSmsWithThread(String mobiles, String content) {
		smsExecutor.submit(new SmsTask(mobiles, content, null));
	}
	public static void sendSmsWithThread(String mobiles, String content,String platform) {
		smsExecutor.submit(new SmsTask(mobiles, content, platform));
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void sendSmsContent(String phone, String content, Map<String, String> map) throws Exception {
		String templete = content;
		if (templete != null) {
			for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext();) {
				Entry<String, String> entry = (Entry<String, String>) iterator.next();
				String value = entry.getValue();
				value = value.replaceAll(Matcher.quoteReplacement("$"), Matcher.quoteReplacement("\\$"));
				templete = templete.replaceAll("\\$\\{" + entry.getKey() + "\\}", value);
			}
		}
		sendSms_lc(templete, phone);
	}
	
	private static ExecutorService smsExecutor = Executors.newFixedThreadPool(2);
}

class SmsTask implements Runnable {
	private String phones;
	private String content;
	private String plaform;
	public SmsTask(String phones,String content,String plaform) {
		this.phones = phones;
		this.content = content;
		this.plaform = plaform;
	}
	@Override
	public void run() {
		if(plaform == null || plaform.equals("hlej")){
			SMSUtil.sendSmsByHlej(content, phones);
		}else if(plaform.equals("hsh")){
			SMSUtil.sendSmsByHsh(phones, content);
		}else if(plaform.equals("hlc")){
			SMSUtil.sendSmsByHlc(phones, content);
		}
	}
	
}
