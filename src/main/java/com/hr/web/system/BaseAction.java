package com.hr.web.system;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.HandlerMapping;

import com.hr.bean.system.AppResult;
import com.hr.bean.system.SysUsers;
import com.hr.bean.system.WebResult;
import com.hr.paramenum.ClientEnum;
import com.hr.util.CodeUtil;

@Controller
public class BaseAction {
	public static final int OK=CodeUtil.SUCCEED;//成功
	public static final int NO=CodeUtil.ERROR;//失败
	
	public WebResult rs=new WebResult(NO,"");//WEB端使用
	public AppResult app=new AppResult(NO,"");//API接口使用
	
	
    @Autowired  
    public Environment env;
	
	
    
    
	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	protected Object getCurrentUser(){
		return getSubject().getPrincipal();
	}
	
	protected SysUsers getShiroUser() {
		Object userObj=getCurrentUser();
		if(userObj!=null) {
			return (SysUsers)userObj;
		}else {
			return null;
		}
		
	}
	
	public String getBaseImgUrl() {
		String nginxImg=env.getProperty("nginx.call.img.url");
		return nginxImg;
	}
	
	public String getWxUrl() {
		String nginxWx=env.getProperty("nginx.call.wx.url");
		return nginxWx;
	}
	
	public String getAppUrl() {
		String nginxApp=env.getProperty("nginx.call.app.url");
		return nginxApp;
	}
	
	
	public HttpServletResponse getHttpResponse() {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return requestAttributes.getResponse();
	}
	
	public HttpServletRequest getHttpRequest() {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return requestAttributes.getRequest();
	}
	
	
	

	
	

	/**
	 * 获得Token
	 * @return
	 */
	public String getToken() {
		String token=getHttpRequest().getParameter("token");
		if(StringUtils.isEmpty(token)) {
			return "";
		}else {
			return token;
		}
	}
	
	/**
	 * 获得页大小
	 * @return
	 */
	public int getPageSize() {
		int pageSize=10;
		String pageSizeStr=getHttpRequest().getParameter("pageSize");
		if(!StringUtils.isEmpty(pageSizeStr)) {
			try {
				pageSize=Integer.parseInt(pageSizeStr);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return pageSize;
	}
	
	/**
	 * 获得当前页
	 * @return
	 */
	public int getPageIndex() {
		int pageIndex=1;
		String pageIndexStr=getHttpRequest().getParameter("pageIndex");
		if(!StringUtils.isEmpty(pageIndexStr)) {
			try {
				pageIndex=Integer.parseInt(pageIndexStr);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return pageIndex;
	}
	
	/**
	 * 获得当前请求终端的类型
	 */
	public ClientEnum getClientType() {
		NativeWebRequest webRequest = new ServletWebRequest(getHttpRequest());
		Map<String, String> uriTemplateVars = (Map<String, String>)webRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
		String os=uriTemplateVars.get("os");
		if(!StringUtils.isEmpty(os)) {
			return ClientEnum.getClientEnumByCode(os);
		}else {
			return ClientEnum.OTHER;
		}
	}
	
	protected void setSessionObj(String key,Object obj){
		getSession().setAttribute(key, obj);
	}
	protected Session getSession() {
		return getSubject().getSession();
	}

	protected Session getSession(Boolean flag) {
		return getSubject().getSession(flag);
	}
	
}
