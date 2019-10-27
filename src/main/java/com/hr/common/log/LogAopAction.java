package com.hr.common.log;

import java.lang.reflect.Method;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hr.bean.system.OsAndBrowserInfo;
import com.hr.bean.system.SysLoginLogs;
import com.hr.bean.system.SysLogs;
import com.hr.bean.system.SysUsers;
import com.hr.bean.system.WebResult;
import com.hr.common.log.system.SysLog;
import com.hr.mapper.system.SysLoginLogsMapper;
import com.hr.mapper.system.SysLogsMapper;
import com.hr.service.redis.RedisService;
import com.hr.util.CodeUtil;
import com.hr.util.IpUtil;

@Aspect
@Component
public class LogAopAction {

	 @Autowired
	 private SysLogsMapper sysLogsMapper;
	 
	 @Autowired
	 private SysLoginLogsMapper sysLoginLogsMapper;
	 
	 @Autowired
	 RedisService redisService;
	 
	 
    /**
     * 后台系统-通用记录操作日志
     */
	 @AfterReturning(returning = "rs",pointcut="@annotation(com.hr.common.log.system.SysLog)")//定义切点
    public void recordLog(JoinPoint joinPoint,WebResult rs){
    	try {
	    	HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	    	String ip=IpUtil.getIpAddr(request);
	    	OsAndBrowserInfo osAndBrowserInfo=IpUtil.getOsAndBrowserInfo(request);
	    	Object sysObj=SecurityUtils.getSubject().getPrincipal();
	    	if(sysObj==null) {
	    		return;
	    	}
	    	SysUsers sysUsers=(SysUsers)sysObj;
	    	int status=0;
	    	String msg="";
	    	if(CodeUtil.SUCCEED==rs.getCode()) {
	    		if(StringUtils.isEmpty(rs.getMsg())) {
	    			msg="操作成功";
	    		}else {
	    			msg=rs.getMsg();
	    		}
	    		status=1;
	    	}else {
	    		msg=rs.getMsg();
	    		status=0;
	    	}
	    	
	    	Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
	    	SysLog sLog = method.getAnnotation(com.hr.common.log.system.SysLog.class);
	    	
	    	SysLogs sysLogs=new SysLogs();
	    	sysLogs.setUser_id(sysUsers.getId());
	    	sysLogs.setUser_name(sysUsers.getUser_name());
	    	sysLogs.setOper_act(method.getName());
	    	sysLogs.setLog_memo(sLog.name());
	    	sysLogs.setLog_ip(ip);
	    	sysLogs.setUser_os(osAndBrowserInfo.getSysOs());
	    	sysLogs.setBrowser(osAndBrowserInfo.getBrowser());
	    	sysLogs.setCreate_time(new Timestamp(System.currentTimeMillis()));
	    	sysLogs.setStatus(status);
	    	sysLogsMapper.addSysLog(sysLogs);
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	

    
    /**
     * 后台系统-记录登录日志
     */
    @AfterReturning(returning = "rs",pointcut="@annotation(com.hr.common.log.system.SysLogLogin)")//定义切点
    public void recordLoginLog(JoinPoint joinPoint,WebResult rs){
    	try {
	    	HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	    	String ip=IpUtil.getIpAddr(request);
	    	OsAndBrowserInfo osAndBrowserInfo=IpUtil.getOsAndBrowserInfo(request);
	    	Object sysObj=SecurityUtils.getSubject().getPrincipal();
	    	int uid=0;
	    	String uname="";
	    	int status=0;
	    	String msg="";
	    	if(sysObj!=null) {
	    		SysUsers sysUsers=(SysUsers)sysObj;
	    		uid=sysUsers.getId();
	    		uname=sysUsers.getUser_name();
	    	}else {
	    		uname=request.getParameter("username");
	    	}
	    	
	    	if(CodeUtil.SUCCEED==rs.getCode()) {
	    		msg="登录成功！";
	    		status=1;
	    	}else {
	    		msg=rs.getMsg();
	    		status=0;
	    	}
	    	
	    	SysLoginLogs sysLoginLogs=new SysLoginLogs();
	    	sysLoginLogs.setUser_id(uid);
	    	sysLoginLogs.setUser_name(uname);
	    	sysLoginLogs.setLog_memo(msg);
	    	sysLoginLogs.setLog_ip(ip);
	    	sysLoginLogs.setOs(osAndBrowserInfo.getSysOs());
	    	sysLoginLogs.setBrowser(osAndBrowserInfo.getBrowser());
	    	sysLoginLogs.setCreate_time(new Timestamp(System.currentTimeMillis()));
	    	sysLoginLogs.setStatus(status);
	    	
	    	sysLoginLogsMapper.addLoginLog(sysLoginLogs);
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	}
    
 
}
