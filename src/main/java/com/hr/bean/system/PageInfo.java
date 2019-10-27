package com.hr.bean.system;

import java.util.List;

public class PageInfo<T> {
	
	private int limit=20;//页面数据行数,页大小
	private int page=1;//当前页码
	private int totalCount;//总数据行数
	private int totalPage;//总页数
	private int currentDataIndex=0;
	private List<T> pageList;//数据集合
	private T paramBean;
	
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getCurrentDataIndex() {
		if(page<=0){
			return 0;
		}else{
			currentDataIndex=(page-1)*limit;
		}
		return currentDataIndex;
	}
	public void setCurrentDataIndex(int currentDataIndex) {
		this.currentDataIndex = currentDataIndex;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	//计算分页
	public int getTotalPage() {
		if(totalCount==0){
			totalPage=0;
			return totalPage;
		}
		totalPage=totalCount%limit==0?totalCount/limit:totalCount/limit+1;
		return totalPage;
	}
	public List<T> getPageList() {
		return pageList;
	}
	public void setPageList(List<T> pageList) {
		this.pageList = pageList;
	}
	public T getParamBean() {
		return paramBean;
	}
	public void setParamBean(T paramBean) {
		this.paramBean = paramBean;
	}
	
	
	
	
}
