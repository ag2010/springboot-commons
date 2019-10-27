package com.hr.common.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;



public class HttpsClientUtils {
	
	//private static Logger logger = LogManager.getLogger(HttpsClientUtils.class);

	private static HttpsClientUtils httpInstance=new HttpsClientUtils();
	

	public static HttpsClientUtils createHttpsInstance(){
		return httpInstance;
	}
	
	public String get(String requestUrl){
		return httpsRequest(requestUrl,"GET",null);
	}
	
	public String post(String requestUrl,String outputStr){
		return httpsRequest(requestUrl,"POST",outputStr);
	}
	
	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		String jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		InputStream inputStream=null;
		InputStreamReader inputStreamReader=null;
		HttpsURLConnection httpUrlConn=null;
		BufferedReader bufferedReader=null;
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod)){
				httpUrlConn.connect();
			}

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			inputStream = httpUrlConn.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			jsonObject = buffer.toString();
		} catch (ConnectException ce) {
			ce.printStackTrace();
			//logger.error("Weixin server connection timed out.");
		} catch (Exception e) {
			e.printStackTrace();
			//logger.error("https request error:{}", e);
		}finally{
			// 释放资源
			try{
				if(bufferedReader!=null){
					bufferedReader.close();
				}
				if(inputStreamReader!=null){
					inputStreamReader.close();
				}
				if(inputStream!=null){
					inputStream.close();
					inputStream = null;
				}
				if(httpUrlConn!=null){
					httpUrlConn.disconnect();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return jsonObject;
	}

}
