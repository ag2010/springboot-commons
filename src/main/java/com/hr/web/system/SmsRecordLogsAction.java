package com.hr.web.system;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hr.bean.system.PageInfo;
import com.hr.bean.system.SmsRecordLogs;
import com.hr.bean.system.WebResult;
import com.hr.service.system.SmsRecordLogsService;

@Controller
@RequestMapping("/sms")
public class SmsRecordLogsAction extends BaseAction{

	@Autowired
	private SmsRecordLogsService smsRecordLogsService;
	
	
	@RequestMapping("/logList")
	public String userList(){
		return "/system/logs/smsLogList";
	}
	
	
	@ResponseBody
    @RequestMapping(value="/findAllSmsRecordLogs",method = RequestMethod.POST)
    public WebResult<SmsRecordLogs> findAllSmsRecordLogs(PageInfo page, SmsRecordLogs smsRecordLogs) throws ParseException{
		rs = new WebResult<SmsRecordLogs>(OK);
		
		
		

		
		
		page.setParamBean(smsRecordLogs);
		PageInfo<SmsRecordLogs> puinfo = smsRecordLogsService.findAllSmsRecordLogsInfoList(page, smsRecordLogs);
		rs.setCount(puinfo.getTotalCount());
		rs.setData(puinfo.getPageList());
		return rs;
	}
	
	

	
	
	
	

}
