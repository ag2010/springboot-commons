package com.hr.web.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hr.common.log.system.SysLog;

/*
 * wangaidong
 * 设备口令管理
 */
@Controller
@RequestMapping("/password")
public class SystemPasswordAction extends BaseAction {
	
	
	
	@ResponseBody
	@SysLog(mod="设备口令配置",name="登录口令")
    @RequestMapping(value="/loginPassword",method = RequestMethod.POST)
	public String loginPassword() {
		return "登录密码";
	}
	
	@ResponseBody
	@SysLog(mod="设备口令配置",name="连接口令")
    @RequestMapping(value="/joinPassword",method = RequestMethod.POST)
	public String joinPassword() {
		return "连接密码";
	}
	
	
	
	@ResponseBody
	@SysLog(mod="设备口令配置",name="注销口令")
    @RequestMapping(value="/logoutPassword",method = RequestMethod.POST)
	public String logoutPassword() {
		return "注销密码";
	}

}
