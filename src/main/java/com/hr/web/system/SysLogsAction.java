package com.hr.web.system;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hr.bean.system.PageInfo;
import com.hr.bean.system.SysLogs;
import com.hr.bean.system.WebResult;
import com.hr.service.system.SysLogService;

@Controller
@RequestMapping("/sys/log")
public class SysLogsAction extends BaseAction{

	@Autowired
	private SysLogService sysLogService;
	
	
	@RequestMapping("/list")
	public String userList(){
		return "/system/logs/opList";
	}
	
	
	@ResponseBody
    @RequestMapping(value="/findAllSysLogs",method = RequestMethod.POST)
    public WebResult<SysLogs> findAllSysLogs(PageInfo page, SysLogs sysLogs) throws ParseException{
		rs = new WebResult<SysLogs>(OK);
		
		
		

			if(null==sysLogs.getStart() || "".equals(sysLogs.getStart())){
				SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				sysLogs.setStart(sdf.format(sdf.parse("1970-01-01 00:00:00")));
			}
			
			if(null==sysLogs.getEnd() || "".equals(sysLogs.getEnd())) {
				SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				sysLogs.setEnd(sdf.format(sdf.parse("2099-12-31 23:59:59")));
			}
		
		
		
		page.setParamBean(sysLogs);
		PageInfo<SysLogs> puinfo = sysLogService.findAllSysLogsInfoList(page, sysLogs);
		rs.setCount(puinfo.getTotalCount());
		rs.setData(puinfo.getPageList());
		return rs;
	}
	
	

	
	
	
	

}
