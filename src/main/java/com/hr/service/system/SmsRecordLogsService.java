package com.hr.service.system;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hr.bean.system.PageInfo;
import com.hr.bean.system.SmsRecordLogs;
import com.hr.mapper.system.SmsRecordLogsMapper;

@Service
public class SmsRecordLogsService {
	 
	@Autowired
	private SmsRecordLogsMapper smsRecordLogsMapper;
	
	public PageInfo<SmsRecordLogs> findAllSmsRecordLogsInfoList(PageInfo page, SmsRecordLogs param) throws ParseException {
	/*	if(null==param.getStart() || "".equals(param.getStart())){
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			param.setStart(sdf.format(sdf.parse("1970-01-01 00:00:00")));
		}
		
		if(null==param.getEnd() || "".equals(param.getEnd())) {
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			param.setEnd(sdf.format(sdf.parse("2099-12-31 23:59:59")));
		}*/
		
		PageInfo<SmsRecordLogs> pinfo=new PageInfo<SmsRecordLogs>();
		page.setParamBean(param);
		int totalCount=smsRecordLogsMapper.findSmsRecordLogsByParamPageCount(param);
		List<SmsRecordLogs> pageList=smsRecordLogsMapper.findSmsRecordLogsByParam(page);
		pinfo.setTotalCount(totalCount);
		pinfo.setPageList(pageList);
		return pinfo;
	}
}
