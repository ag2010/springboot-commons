package com.hr.service.system;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.hr.bean.system.PageInfo;
import com.hr.bean.system.SysLogs;
import com.hr.mapper.system.SysLogsMapper;

@Service
public class SysLogService {
	 
	@Autowired
	private SysLogsMapper sysLogsMapper;
	
	public PageInfo<SysLogs> findAllSysLogsInfoList(PageInfo page,@RequestParam(value="param.stutas", required=true, defaultValue="-1") SysLogs param) throws ParseException {
	/*	if(null==param.getStart() || "".equals(param.getStart())){
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			param.setStart(sdf.format(sdf.parse("1970-01-01 00:00:00")));
		}
		
		if(null==param.getEnd() || "".equals(param.getEnd())) {
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			param.setEnd(sdf.format(sdf.parse("2099-12-31 23:59:59")));
		}*/
		
		PageInfo<SysLogs> pinfo=new PageInfo<SysLogs>();
		page.setParamBean(param);
		int totalCount=sysLogsMapper.findSysLogsByParamPageCount(param);
		List<SysLogs> pageList=sysLogsMapper.findSysLogsByParam(page);
		pinfo.setTotalCount(totalCount);
		pinfo.setPageList(pageList);
		return pinfo;
	}
}
