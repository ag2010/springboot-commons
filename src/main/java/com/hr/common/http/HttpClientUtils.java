package com.hr.common.http;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

import com.hr.bean.system.PostParam;



public class HttpClientUtils {
	
	//private static Logger logger = LogManager.getLogger(HttpClientUtils.class);
	
	private static HttpClientUtils httpInstance=new HttpClientUtils();
	private static HttpClient httpClient=new DefaultHttpClient();
	private static final int CONNECTION_TIMEOUT=30000;//30s
	private static final int SO_TIMEOUT=30000;//30s
	private static final String E_CODE="UTF-8";
	private static final int MAX_CONNECTION=3;//最大重试连接次数

	static{
		//请求超时
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
		//读取超时
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);
	}
	
	public static HttpClientUtils createHttpInstance(){
		return httpInstance;
	}

	public String get(String url){
		String result="";
		HttpGet get=new HttpGet(url);
		
		try{
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
		}catch(Exception e){
			e.printStackTrace();
		}
		int lineCount=0;
		//logger.info("正在连接远程WEB服务器...");
		HttpResponse response=connectionGet(get,lineCount);
		lineCount=0;
		
		if(response!=null){
			HttpEntity entiry=response.getEntity();
			if(entiry!=null){
				try {
					result=EntityUtils.toString(entiry,E_CODE);
				}catch(Exception e){
					e.printStackTrace();
					//logger.error("读取响应内容异常",e);
				}
			}
		}
		return result;
	}
	
	public String post(String url){
		return post(url,null);
	}

	public String postByParams(String url,List<PostParam> params){
		String result="";
		HttpPost post=new HttpPost(url);
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		if(params!=null && params.size()>0){
			for(PostParam map:params){
				if(!StringUtils.isEmpty(map.getKeyStr())){
					formparams.add(new BasicNameValuePair(map.getKeyStr(), map.getValueStr()));
				}
			}
			if(formparams.size()>0){
				UrlEncodedFormEntity uefEntity;
				try {
					uefEntity = new UrlEncodedFormEntity(formparams, E_CODE);
					post.setEntity(uefEntity);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					//logger.warn("运行环境不支持UTF-8编码字符集");
				}
			}
		}
		int lineCount=0;
		//logger.info("正在连接远程WEB服务器...");
		HttpResponse response=connectionPost(post,lineCount);
		lineCount=0;
		
		if(response!=null){
			HttpEntity entiry=response.getEntity();
			if(entiry!=null){
				try {
					result=EntityUtils.toString(entiry,E_CODE);
				}catch(Exception e){
					e.printStackTrace();
					//logger.error("读取响应内容异常",e);
				}
			}
		}
		return result;
	}
	
	
	public String post(String url,HashMap<String,String> params){
		String result="";
		HttpPost post=new HttpPost(url);
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		
		if(params!=null && !params.isEmpty()){
			for(Map.Entry<String, String> map:params.entrySet()){
				if(!StringUtils.isEmpty(map.getKey())){
					formparams.add(new BasicNameValuePair(map.getKey(), map.getValue()));
				}
			}
			if(formparams.size()>0){
				UrlEncodedFormEntity uefEntity;
				try {
					uefEntity = new UrlEncodedFormEntity(formparams, E_CODE);
					post.setEntity(uefEntity);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					//logger.warn("运行环境不支持UTF-8编码字符集");
				}
			}
		}
		int lineCount=0;
		//logger.info("正在连接远程WEB服务器...");
		HttpResponse response=connectionPost(post,lineCount);
		lineCount=0;
		
		if(response!=null){
			HttpEntity entiry=response.getEntity();
			if(entiry!=null){
				try {
					result=EntityUtils.toString(entiry,E_CODE);
				}catch(Exception e){
					e.printStackTrace();
					//logger.error("读取响应内容异常",e);
				}
			}
		}
		return result;
	}
	
	
	private HttpResponse connectionPost(HttpPost post,int lineCount){
		int code=-1;
		HttpResponse response=null;
		try {
			lineCount++;
			response = httpClient.execute(post);
		}catch(Exception e){
			if(e!=null && e.getMessage()!=null){
				if(e.getMessage().contains("timed out")){
					//logger.info(lineCount+" 次连接超时. ");
				}else{
					//logger.info(lineCount+" 次连接异常. ");
				}
				if(lineCount>=MAX_CONNECTION){
					//logger.error("连接错误.");
					//logger.error(e);
				}
			}
		}finally{
			post.releaseConnection();
		}
		if(response!=null){
			code=response.getStatusLine().getStatusCode();
			//logger.info("HTTP响应码:"+code);
		}
		if(code!=HttpStatus.SC_OK){
			if(lineCount<MAX_CONNECTION){
				//logger.info("正在重试连接...");
				connectionPost(post,lineCount);
			}else{
				response=null;
			}
		}
		return response;
	}
	
	
	private HttpResponse connectionGet(HttpGet get,int lineCount){
		int code=-1;
		HttpResponse response=null;
		try {
			lineCount++;
			response = httpClient.execute(get);
		}catch(Exception e){
			if(e!=null && e.getMessage()!=null){
				if(e.getMessage().contains("timed out")){
					//logger.info(lineCount+" 次连接超时. ");
				}else{
					//logger.info(lineCount+" 次连接异常. ");
				}
				if(lineCount>=MAX_CONNECTION){
					//logger.error("连接错误.");
					//logger.error(e);
				}
			}
		}finally{
			get.releaseConnection();
		}
		if(response!=null){
			code=response.getStatusLine().getStatusCode();
			//logger.info("HTTP响应码:"+code);
		}
		if(code!=HttpStatus.SC_OK){
			if(lineCount<MAX_CONNECTION){
				//logger.info("正在重试连接...");
				connectionGet(get,lineCount);
			}else{
				response=null;
			}
		}
		return response;
	}
	
	
}
