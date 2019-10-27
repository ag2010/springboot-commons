package com.hr.bean.system;

import java.io.Serializable;
import java.util.List;

import com.hr.util.CodeUtil;


public class WebResult<T> implements Serializable {
	
	
	private int code=CodeUtil.ERROR;
	private String msg="";
	private T dataSingle;
	private List<T> data;
	private int count=0;
	
	public WebResult(){
		
	}
	
	public WebResult(int code){
		this.code=code;
	}
	
	public WebResult(String msg){
		this.msg=msg;
	}
	
	public WebResult(int code,String msg){
		this.code=code;
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
	}

	public void setOK(String msg){
		this.code=CodeUtil.SUCCEED;
		this.msg=msg;
	}
	public void setNo(String msg){
		this.code=CodeUtil.ERROR;
		this.msg=msg;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	
	
	
}
