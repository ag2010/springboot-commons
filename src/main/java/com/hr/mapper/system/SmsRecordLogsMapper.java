package com.hr.mapper.system;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hr.bean.system.PageInfo;
import com.hr.bean.system.SmsRecordLogs;


@Mapper
public interface SmsRecordLogsMapper {

	

	/**
	 * 分页搜索用户列表
	 * @param sysUsers
	 * @return
	 */
	public int findSmsRecordLogsByParamPageCount(SmsRecordLogs param);
	public List<SmsRecordLogs> findSmsRecordLogsByParam(PageInfo<SmsRecordLogs> param);

	public int insertSMSLog(SmsRecordLogs smsRecordLogs);
}
