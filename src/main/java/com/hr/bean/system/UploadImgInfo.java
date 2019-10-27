package com.hr.bean.system;

public class UploadImgInfo implements java.io.Serializable{
	
	private String imgName;
	private String imgPath;//服务器绝对路径
	private String imgCallPath;//服务器访问相对路径
	private String imgSrcUrl;
	
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public String getImgCallPath() {
		return imgCallPath;
	}
	public void setImgCallPath(String imgCallPath) {
		this.imgCallPath = imgCallPath;
	}
	public String getImgSrcUrl() {
		return imgSrcUrl;
	}
	public void setImgSrcUrl(String imgSrcUrl) {
		this.imgSrcUrl = imgSrcUrl;
	}
	
	

}
