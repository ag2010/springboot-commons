package com.hr.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.hr.bean.system.MapInfoBean;
import com.hr.bean.system.MapRsInfoBean;
import com.hr.common.http.HttpClientUtils;


public class AmapUtil {

	public static Map<String, Double> getLngAndLatByAmap(String address) throws Exception {

        address = address.trim();

        Map<String, Double> map = new HashMap<String, Double>();
        map.put("lng", 0d);
        map.put("lat", 0d);

        //String url = "http://restapi.amap.com/v3/geocode/geo?key=&address=" + URLEncoder.encode(address, "utf-8");
        String url = "http://restapi.amap.com/v3/geocode/geo";

        HashMap<String,String> params=new HashMap<String,String>();
        params.put("address", address);
        params.put("key", "3f839f31bfd9fe87e179e231c72a5900");
        
        String jsonStr=HttpClientUtils.createHttpInstance().post(url, params);
        MapRsInfoBean mrInfoBean=null;
        if(!StringUtils.isEmpty(jsonStr)) {
        	mrInfoBean=JsonUtil.jsonToObject(jsonStr, MapRsInfoBean.class);
        }
        if(mrInfoBean==null) {
        	return map;
        }
    	if(mrInfoBean.getStatus()!=1) {
    		return map;
    	}
    	if(mrInfoBean.getGeocodes()==null) {
    		return map;
    	}
    	if(mrInfoBean.getGeocodes().size()<=0) {
    		return map;
    	}
    	MapInfoBean minfoBean=mrInfoBean.getGeocodes().get(0);
    	if(minfoBean==null) {
    		return map;
    	}
    	
    	if(StringUtils.isEmpty(minfoBean.getLocation())) {
    		return map;
    	}
    	
    	String[] location = minfoBean.getLocation().split(",");
    	
    	if(location==null || location.length<2) {
    		return map;
    	}
    	try {
            double lng = Double.parseDouble(location[0]);
            double lat = Double.parseDouble(location[0]);

            map.put("lng", lng);
            map.put("lat", lat);
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
        return map;

    }
	
	public static void main(String[] args) throws Exception {
		System.out.println(getLngAndLatByAmap("湖北省武汉市江夏区世贸中心"));
	}
}
