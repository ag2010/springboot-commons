package com.hr.web.system;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hr.bean.system.SysMenu;
import com.hr.bean.system.SysPermissions;
import com.hr.bean.system.SysRole;
import com.hr.bean.system.WebResult;
import com.hr.bean.system.ZTreeBean;
import com.hr.common.log.system.SysLog;
import com.hr.mapper.system.SysMenuMapper;
import com.hr.mapper.system.SysPermissionsMapper;
import com.hr.mapper.system.SysRoleMapper;
import com.hr.util.AuthUtil;




@Controller
@RequestMapping("/system")
public class RoleAction extends BaseAction{
	
	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	@Autowired
	private SysMenuMapper sysMenuMapper;
	
	@Autowired
	private SysPermissionsMapper sysPermissionsMapper;	
	

	
	@RequestMapping("/roleList")
	public String roleList(Model model){
		return "/system/role/roleList";
	}
	
	
	@ResponseBody
    @RequestMapping(value="/queryRoleList",method = RequestMethod.POST)
	public WebResult queryRoleList(){
		rs=new WebResult(OK);
		return rs;
	}
	
	@ResponseBody
    @RequestMapping(value="/permissionsTreeList",method = RequestMethod.POST)
	public List<ZTreeBean> permissionsTreeList(int roleId){
		List<ZTreeBean> authTreeList=new ArrayList<ZTreeBean>();
		
		SysRole sysRole=sysRoleMapper.findSysRoleById(roleId);
		
		if(sysRole==null){
			return authTreeList;
		}
		String roleMenus=sysRole.getRole_menu();
		List<SysMenu> sysMenuLists=sysMenuMapper.findAllSysMenusBy();
		for(SysMenu sysMenu:sysMenuLists){
			ZTreeBean authTreeBean=new ZTreeBean();
			authTreeBean.setId(sysMenu.getId()+"");
			authTreeBean.setpId(sysMenu.getPid()+"");
			authTreeBean.setCode(sysMenu.getCode());
			authTreeBean.setPcode(sysMenu.getParent_code());
			authTreeBean.setName(sysMenu.getMenu_name());
			if(AuthUtil.hasAuth(roleMenus, sysMenu.getCode())){
				authTreeBean.setChecked(true);
			}else{
				authTreeBean.setChecked(false);
			}
			authTreeList.add(authTreeBean);
		}
		
		return authTreeList;
	}
	
	
	@RequestMapping("/toPerAuthPage")
	public String toPerAuthPage(Model model,int roleId){
	
		TreeMap<String,List<SysPermissions>>  sysPerMaps=new TreeMap<String,List<SysPermissions>>();
		String roleName="";
		SysRole sysRole=sysRoleMapper.findSysRoleById(roleId);
		if(sysRole!=null && sysRole.getId()>0){
			roleName=sysRole.getRole_name();
			String rolePers=sysRole.getRole_permissions();
			List<SysPermissions> allPerLists=sysPermissionsMapper.findAllPerListGroups();
			
			for(SysPermissions sysPer:allPerLists){
				List<SysPermissions> perLists=sysPerMaps.get(sysPer.getPermissions_group());
				if(perLists==null){
					perLists=new ArrayList<SysPermissions>();
				}
				if(AuthUtil.hasAuth(rolePers, sysPer.getPermissions_code())){
					sysPer.setHasPer(true);
				}else{
					sysPer.setHasPer(false);
				}
				perLists.add(sysPer);
				sysPerMaps.put(sysPer.getPermissions_group(), perLists);
			}
			
		}
		model.addAttribute("roleName", roleName);
		model.addAttribute("roleId", roleId);
		model.addAttribute("sysPerMaps", sysPerMaps);
		return "/system/role/rolePerAuth";
	}
	
	
	@ResponseBody
    @RequestMapping(value="/acceptRolePerAction",method = RequestMethod.POST)
	public WebResult acceptRolePerAction(int roleId,String rolePer,boolean isAccept) {
		rs=new WebResult();
		int code=0;
		SysRole sysRole=sysRoleMapper.findSysRoleById(roleId);
		if(sysRole!=null && sysRole.getId()>0){
			String pers=sysRole.getRole_permissions();
			String newPers="";
			if(isAccept){//新增
				if(!StringUtils.isEmpty(pers)){
					newPers=pers+","+rolePer;
				}else{
					newPers=rolePer;
				}
			}else{//移除
				if(!StringUtils.isEmpty(pers)){
					if(pers.equals(rolePer)){
						newPers="";
					}else{
						if(pers.startsWith(rolePer+",")){
							newPers=pers.replace(rolePer+",","");
						}else if(pers.endsWith(","+rolePer)){
							newPers=pers.replace(","+rolePer,"");
						}else{
							newPers=pers.replace(rolePer+",","");
						}
					}
					
				}else{
					newPers="";
				}
			}
			
			code=sysRoleMapper.saveRoleResources(roleId+"",newPers);
		}
		
		
		if(code>0) {
			rs.setOK("保存成功");
		}else {
			rs.setNo("保存失败");
		}
		return rs;
	}
	
	
	
	@ResponseBody
    @RequestMapping(value="/operatePermissionsTreeList",method = RequestMethod.POST)
	public List<ZTreeBean> operatePermissionsTreeList(int roleId){
		List<ZTreeBean> authTreeList=new ArrayList<ZTreeBean>();
		
		SysRole sysRole=sysRoleMapper.findSysRoleById(roleId);
		
		if(sysRole==null){
			return authTreeList;
		}
		String rolePermissions=sysRole.getRole_permissions();

		
		return authTreeList;
	}

	
	
	
	@ResponseBody
	@SysLog(mod="系统管理",name="新增角色")
    @RequestMapping(value="/saveAddRole",method = RequestMethod.POST)
	public WebResult saveAddRole(SysRole sysRole) {
		rs=new WebResult();
		int code=sysRoleMapper.insertRole(sysRole);
		if(code>0) {
			rs.setOK("添加成功");
		}else {
			rs.setNo("添加失败");
		}
		return rs;
	}
	
	
	@ResponseBody
	@SysLog(mod="系统管理",name="编辑角色")
    @RequestMapping(value="/monfiyAddRole",method = RequestMethod.POST)
	public WebResult monfiyAddRole(SysRole sysRole) {
		rs=new WebResult();
		int code=sysRoleMapper.editRole(sysRole);
		if(code>0) {
			rs.setOK("修改成功");
		}else {
			rs.setNo("修改失败");
		}
		return rs;
	}
	
	@ResponseBody
	@SysLog(mod="系统管理",name="删除角色")
    @RequestMapping(value="/deleteRole",method = RequestMethod.POST)
	public WebResult deleteRole(String delId){
		rs=new WebResult();
		if(StringUtils.isEmpty(delId)) {
			rs.setNo("参数非法");
			return rs;
		}
		int i=sysRoleMapper.deleteRoleById(delId);
		if(i>=1){
			rs.setOK("删除成功！");
		}else{
			rs.setNo("删除失败！");
		}
		return rs;
	}
	
	
	@ResponseBody
    @RequestMapping(value="/editRoleStatus",method = RequestMethod.POST)
	public WebResult editRoleStatus(String roid,int status) {
		rs=new WebResult();
		int code=sysRoleMapper.editRoleStatus(roid, status);
		if(code>0) {
			rs.setOK("修改成功");
		}else {
			rs.setNo("修改失败");
		}
		return rs;
	}
	
	
	@ResponseBody
	@SysLog(mod="系统管理",name="菜单授权")
    @RequestMapping(value="/authorizeMenuAction",method = RequestMethod.POST)
	public WebResult authorizeMenuAction(String roid,String menuPers) {
		rs=new WebResult();
		int code=sysRoleMapper.editRoleMenuPers(roid, menuPers);
		if(code>0) {
			rs.setOK("授权成功");
		}else {
			rs.setNo("授权失败");
		}
		return rs;
	}
	
}
