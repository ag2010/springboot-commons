package com.hr.util;


import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;






/**
 * 
 * @author wangaidong
 *
 */
public class GetLatAndLng{
	/**
	 * @param addr高德查询的地址的经纬度
	 * @return
	 * @throws IOException
	 */
	
	public static Map<String,Object> getAreaLongAndDimen(String addr) throws Exception {
		String address = addr.trim();
		Map<String, Object> map = new HashMap<String, Object>();
        String url = "http://restapi.amap.com/v3/geocode/geo?address=" + URLEncoder.encode(address, "utf-8")
                + "&output=json&key=7ef02bf8c6fc46af252f94f0e8a319a1";
        GetMethod method = new GetMethod(url);
        method.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
        HttpClient client = new HttpClient();
        client.getHttpConnectionManager().getParams().setConnectionTimeout(10000); // 设置连接超时
        int status = client.executeMethod(method);
        if (status == 200) {
            String json = method.getResponseBodyAsString();
            JSONObject obj = JSONObject.parseObject(json);
            if (obj.get("status").toString().equals("1")) {
                JSONArray array = obj.getJSONArray("geocodes");
                String str = array.getString(0);
                JSONObject locationjson = JSONObject.parseObject(str);
                str = locationjson.getString("location");
                String[] location = str.split(",");
                double lng = Double.parseDouble(location[0]);
                double lat = Double.parseDouble(location[1]);
                map.put("lng", lng);
                map.put("lat", lat);
             } else {
                System.out.println("未找到相匹配的经纬度！");
                throw new Exception();
            }
        }
        return map;
    }


//	public static void main(String[] arg) {
//		GetLatAndLng a = new GetLatAndLng();
//		try {
//			Map param =a.getAreaLongAndDimen("洪山区光谷广场");
//			System.out.println(param);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}
