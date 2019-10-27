package com.hr.service.system;

import org.springframework.beans.factory.annotation.Autowired;

import com.hr.bean.system.SysUsers;
import com.hr.mapper.system.SysUsersMapper;

public class SysUserService{

	@Autowired
	private SysUsersMapper sysUsersMapper;
	
	public SysUsers loginUser(String loginName, String password){
		return sysUsersMapper.login(loginName, password);
	}
	
	
	
}
