package com.hr.web.system;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hr.bean.system.PageInfo;
import com.hr.bean.system.SysLoginLogs;
import com.hr.bean.system.WebResult;
import com.hr.service.system.SysLoginLogService;

@Controller
@RequestMapping("/sys/log")
public class SysLoginLogsAction extends BaseAction{

	@Autowired
	private SysLoginLogService sysLoginLogService;
	
	
	@RequestMapping("/loglist")
	public String userList(){
		return "/system/logs/loginList";
	}
	
	
	@ResponseBody
    @RequestMapping(value="/findAllLoginSysLogs",method = RequestMethod.POST)
    public WebResult<SysLoginLogs> findAllSysLoginLogs(PageInfo page,SysLoginLogs sysLoginLogs) throws ParseException{
		rs = new WebResult<SysLoginLogs>(OK);
	
			if(null==sysLoginLogs.getStart() || "".equals(sysLoginLogs.getStart())){
				SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				sysLoginLogs.setStart(sdf.format(sdf.parse("1970-01-01 00:00:00")));
			}
			
			if(null==sysLoginLogs.getEnd() || "".equals(sysLoginLogs.getEnd())) {
				SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				sysLoginLogs.setEnd(sdf.format(sdf.parse("2099-12-31 23:59:59")));
			}
		
		
		
		
		rs = new WebResult<SysLoginLogs>(OK);
		page.setParamBean(sysLoginLogs);
		PageInfo<SysLoginLogs> puinfo = sysLoginLogService.findAllSysLoginLogsInfoList(page, sysLoginLogs);
		rs.setCount(puinfo.getTotalCount());
		rs.setData(puinfo.getPageList());
		return rs;
	}
	
	

	
	
	
	

}
