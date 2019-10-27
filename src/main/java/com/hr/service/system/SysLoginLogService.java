package com.hr.service.system;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hr.bean.system.PageInfo;
import com.hr.bean.system.SysLoginLogs;
import com.hr.mapper.system.SysLoginLogsMapper;

@Service
public class SysLoginLogService {
	 
	@Autowired
	private SysLoginLogsMapper sysLoginLogsMapper;
	
	public PageInfo<SysLoginLogs> findAllSysLoginLogsInfoList(PageInfo page, SysLoginLogs param) throws ParseException {
		
		
		
		
		
		
		PageInfo<SysLoginLogs> pinfo=new PageInfo<SysLoginLogs>();
		page.setParamBean(param);
		int totalCount=sysLoginLogsMapper.findSysLoginLogsByParamPageCount(param);
		List<SysLoginLogs> pageList=sysLoginLogsMapper.findSysLoginLogsByParam(page);
		pinfo.setTotalCount(totalCount);
		pinfo.setPageList(pageList);
		return pinfo;
	}
}
