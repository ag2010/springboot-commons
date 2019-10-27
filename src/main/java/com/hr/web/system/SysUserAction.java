package com.hr.web.system;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hr.bean.system.PageInfo;
import com.hr.bean.system.SysDept;
import com.hr.bean.system.SysRole;
import com.hr.bean.system.SysUsers;
import com.hr.bean.system.WebResult;
import com.hr.common.log.system.SysLog;
import com.hr.mapper.system.SysDeptMapper;
import com.hr.mapper.system.SysRoleMapper;
import com.hr.mapper.system.SysUsersMapper;
import com.hr.util.IpUtil;
import com.hr.util.MD5Util;
import com.hr.util.StringUtil;

@Controller
@RequestMapping("/system")
public class SysUserAction extends BaseAction{

	@Autowired
	private SysUsersMapper sysUsersMapper;
	@Autowired
	private SysDeptMapper sysDeptMapper;
	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	
	@RequestMapping("/userList")
	public String userList(){
		return "/system/user/userList";
	}
	
	
	@RequestMapping("/password")
	public String password(){
		return "/system/systemSetting/password";
	}
	@RequestMapping("/toAddUser")
	public String toAddUser(Model model,String uid){
		SysUsers sysUsers=new SysUsers();
		SysDept sysDept=new SysDept();
		if(!StringUtils.isEmpty(uid)) {
			sysUsers=sysUsersMapper.findSysUsersById(uid);
			
			if(sysUsers!=null && sysUsers.getId()>0) {
				
				sysDept=sysDeptMapper.findSysDeptById(sysUsers.getDept_id()+"");
				
			}
			
		}
		
		List<SysRole> allRools=sysRoleMapper.listAllRoles();
		if(allRools==null) {
			allRools=new ArrayList<SysRole>();
		}
		
		model.addAttribute("sysuser", sysUsers);
		model.addAttribute("sysDept", sysDept);
		model.addAttribute("roleList", allRools);
		return "/system/user/userAdd";
	}
	
	
	
	@ResponseBody
	@SysLog(mod="系统管理",name="新增员工")
    @RequestMapping(value="/addUser",method = RequestMethod.POST)
	
	public WebResult addUser(HttpServletRequest request,SysUsers sysUsers){
		rs=new WebResult();
		int i=0;
		if(sysUsers!=null && !StringUtils.isEmpty(sysUsers.getUser_name())) {
			
			 List<SysUsers> userLists=sysUsersMapper.findSysUsersByUserName(sysUsers.getUser_name());
		        
	        if(userLists!=null && userLists.size()>0){
	        		rs.setNo("不能添加重复用户");
	        		return rs;
	        }
			Timestamp time=new Timestamp(System.currentTimeMillis());
			sysUsers.setCreate_time(time);
			sysUsers.setPasswd(MD5Util.qrsoftMD5("123456"));
			sysUsers.setCreate_ip(IpUtil.getIpAddr(request));
			sysUsers.setStatus(1);
			i=sysUsersMapper.insertUser(sysUsers);
		}
		if(i>=1){
			rs.setOK("添加成功！");
		}else{
			rs.setNo("添加失败！");
		}
		return rs;
	}
	
	
	@ResponseBody
	@SysLog(mod="系统管理",name="编辑员工")
    @RequestMapping(value="/editUser",method = RequestMethod.POST)
	public WebResult editUser(SysUsers sysUsers){
		rs=new WebResult();
		int i=sysUsersMapper.updateUserInfo(sysUsers);
		if(i>=1){
			rs.setOK("修改成功！");
		}else{
			rs.setNo("修改失败！");
		}
		return rs;
	}
	
	@ResponseBody
	@SysLog(mod="系统管理",name="删除员工")
    @RequestMapping(value="/deleteUser",method = RequestMethod.POST)
	public WebResult deleteUser(String uid){
		rs=new WebResult();
		int i=sysUsersMapper.deleteUserById(uid);
		if(i>=1){
			rs.setOK("删除成功！");
		}else{
			rs.setNo("删除失败！");
		}
		return rs;
	}
	
	@ResponseBody
    @RequestMapping(value="/findAllSysUsers",method = RequestMethod.POST)
    public WebResult<SysUsers> findAllSysUsers(PageInfo page,SysUsers sysuParam){
		rs=new WebResult<SysUsers>(OK);
		page.setParamBean(sysuParam);		
		int pcount=sysUsersMapper.findSysUsersByParamPageCount(sysuParam);
		List<SysUsers> userList=sysUsersMapper.findSysUsersByParam(page);
		
		rs.setCount(pcount);
		rs.setData(userList);
    	return rs;
	}
	
	
	@RequestMapping("/toPersonalData")
	public String toPersonalData(Model model){
		SysUsers sysUsers = (SysUsers) SecurityUtils.getSubject().getPrincipal();
		        if(sysUsers!=null && sysUsers.getId()>0) {
				SysUsers sysUsers1 = sysUsersMapper.findSysUsersById(String.valueOf(sysUsers.getId()));
					
				SysDept findSysDeptById = sysDeptMapper.findSysDeptById(String.valueOf(sysUsers1.getDept_id()));
				sysUsers1.setDept_name(findSysDeptById.getDept_name());
				
				SysRole findSysRoleById = sysRoleMapper.findSysRoleById(sysUsers1.getRole_id());
				
				sysUsers1.setRole_name(findSysRoleById.getRole_name());
				
				System.out.println(sysUsers1);
				
				model.addAttribute("sysuser", sysUsers1);
							return "/system/personaldata/personalData";
						}else {
							
						return null;
					
		}
			
			
			
			
		
	}
	
	
	@RequestMapping("/toChangePwd")
	public String changePwd(Model model){
		SysUsers sysUsers = (SysUsers) SecurityUtils.getSubject().getPrincipal();
		        if(sysUsers!=null && sysUsers.getId()>0) {
				SysUsers sysUsers1 = sysUsersMapper.findSysUsersById(String.valueOf(sysUsers.getId()));
					
				SysDept findSysDeptById = sysDeptMapper.findSysDeptById(String.valueOf(sysUsers1.getDept_id()));
				sysUsers1.setDept_name(findSysDeptById.getDept_name());
				
				SysRole findSysRoleById = sysRoleMapper.findSysRoleById(sysUsers1.getRole_id());
				
				sysUsers1.setRole_name(findSysRoleById.getRole_name());
				
				model.addAttribute("sysuser", sysUsers1);
							return "/system/personaldata/changePwd";
						}else {
							
						return null;
					
		}
			
			
			
			
		
	}
	
	@ResponseBody
    @RequestMapping(value="/editPwd",method = RequestMethod.POST)
	public WebResult editPwd(SysUsers sysUsers){
		rs=new WebResult();
		SysUsers sysUser = (SysUsers) SecurityUtils.getSubject().getPrincipal();
        if(sysUser!=null && sysUser.getId()>0) {
		SysUsers sysUsers1 = sysUsersMapper.findSysUsersById(String.valueOf(sysUser.getId()));
		if(!MD5Util.qrsoftMD5(sysUsers.getPasswd()).equals(sysUsers1.getPasswd())) {
			rs.setNo("原密码不正确");
			     
			    
			}else {
				
				if(StringUtil.strEquals(sysUsers.getComfirmPwd(), sysUsers.getNew_pwd())) {
					String qrsoftMD5 = MD5Util.qrsoftMD5(sysUsers.getNew_pwd());
					sysUsers.setNew_pwd(qrsoftMD5);
					int modifyPwd = sysUsersMapper.modifyPwd(sysUsers);
					if(modifyPwd > 0) {
						rs.setOK("修改成功！");
						//SecurityUtils.getSubject().logout();
						
					}else {
						rs.setNo("修改失败！");
					}
					
				}else {
					rs.setNo("确认密码不正确！");
				}
				
				
				
				
				
			}
		}else {
			rs.setNo("修改失败！");
		}
		
        return rs;
	}
	
	
	
	
	

}
