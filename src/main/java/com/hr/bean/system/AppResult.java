package com.hr.bean.system;

import com.hr.util.CodeUtil;

public class AppResult<T> implements java.io.Serializable{
	
	private int code=CodeUtil.ERROR;;
	private String msg="";
	private T dataSingle;
	private DataArray<T> dataArray;
	
	
	public AppResult(){
		
	}
	
	public AppResult(int code){
		this.code=code;
	}
	
	public AppResult(String msg){
		this.msg=msg;
	}
	
	public AppResult(int code,String msg){
		this.code=code;
		this.msg=msg;
	}
	
	
	public void OK(String msg){
		this.code=CodeUtil.SUCCEED;
		this.msg=msg;
	}
	public void OK(){
		this.code=CodeUtil.SUCCEED;
		this.msg="";
	}
	public void NO(String msg){
		this.code=CodeUtil.ERROR;
		this.msg=msg;
	}
	public void NO(int errorCode,String msg) {
		this.code=errorCode;
		this.msg=msg;
	}
	
	
	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getDataSingle() {
		return dataSingle;
	}
	public void setDataSingle(T dataSingle) {
		this.dataSingle = dataSingle;
		this.OK();
	}
	public DataArray<T> getDataArray() {
		return dataArray;
	}
	public void setDataArray(DataArray<T> dataArray) {
		this.dataArray = dataArray;
	}
	
	
	
	
	
	
	
}
