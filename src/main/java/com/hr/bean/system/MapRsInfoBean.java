package com.hr.bean.system;

import java.util.List;

public class MapRsInfoBean implements java.io.Serializable{

	private int status;
	private List<MapInfoBean> geocodes;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List<MapInfoBean> getGeocodes() {
		return geocodes;
	}
	public void setGeocodes(List<MapInfoBean> geocodes) {
		this.geocodes = geocodes;
	}
	
	
}
