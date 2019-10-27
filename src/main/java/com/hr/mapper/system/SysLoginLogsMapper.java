package com.hr.mapper.system;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hr.bean.system.PageInfo;
import com.hr.bean.system.SysLoginLogs;



@Mapper
public interface SysLoginLogsMapper {

	
	public int addLoginLog(SysLoginLogs sysLoginLogs);

	public int findSysLoginLogsByParamPageCount(SysLoginLogs param);
	public List<SysLoginLogs> findSysLoginLogsByParam(PageInfo<SysLoginLogs> param);

}
