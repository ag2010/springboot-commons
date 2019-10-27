package com.hr.bean.system;


import java.util.List;

public class SysMenu implements java.io.Serializable{
	
	
	
	private int id;
	private int pid;
	private String menu_name;
	private String link_url;
	private int sort;
	private int level;
	private int status;
	private String parent_code;
	private String code;
	private String iconCls;
	private int expanded;
	private String menu_path;
	private List<SysMenu> childMenu;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getMenu_name() {
		return menu_name;
	}
	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}
	public String getLink_url() {
		return link_url;
	}
	public void setLink_url(String link_url) {
		this.link_url = link_url;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getParent_code() {
		return parent_code;
	}
	public void setParent_code(String parent_code) {
		this.parent_code = parent_code;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public int getExpanded() {
		return expanded;
	}
	public void setExpanded(int expanded) {
		this.expanded = expanded;
	}
	public List<SysMenu> getChildMenu() {
		return childMenu;
	}
	public void setChildMenu(List<SysMenu> childMenu) {
		this.childMenu = childMenu;
	}
	public String getMenu_path() {
		return menu_path;
	}
	public void setMenu_path(String menu_path) {
		this.menu_path = menu_path;
	}
	
	

}
