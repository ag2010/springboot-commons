package com.hr.mapper.system;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hr.bean.system.PageInfo;
import com.hr.bean.system.SysLogs;


@Mapper
public interface SysLogsMapper {

	public int addSysLog(SysLogs sysLogs);

	/**
	 * 分页搜索用户列表
	 * @param sysUsers
	 * @return
	 */
	public int findSysLogsByParamPageCount(SysLogs param);
	public List<SysLogs> findSysLogsByParam(PageInfo<SysLogs> param);

}
