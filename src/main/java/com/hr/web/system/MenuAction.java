package com.hr.web.system;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hr.bean.system.SysMenu;
import com.hr.bean.system.WebResult;
import com.hr.bean.system.ZTreeBean;
import com.hr.common.log.system.SysLog;
import com.hr.mapper.system.SysMenuMapper;

@Controller
@RequestMapping("/system")
public class MenuAction extends BaseAction{
	
	@Autowired
	private SysMenuMapper sysMenuMapper;
	
	@RequestMapping("/menuList")
	public String menuList(Model model){
		return "/system/menu/menuList";
	}
	
	@ResponseBody
    @RequestMapping(value="/queryMenuList",method = RequestMethod.POST)
	public WebResult queryMenuList(String uid){
		rs=new WebResult(OK);
		List<SysMenu> menuList=sysMenuMapper.findAllSysMenusBy();
		rs.setData(menuList);
		return rs;
	}
	
	
	@RequestMapping("/toEditMenu")
	public String toEditMenu(Model model,String id){
		SysMenu sysMenu=new SysMenu();
		if(!StringUtils.isEmpty(id)){
			sysMenu=sysMenuMapper.findSysMenuById(id);
			if(sysMenu!=null){
				int pid=sysMenu.getPid();
				String pPath="";
				if(pid>0){
					pPath=findMenuPath(pid,"");
				}else{
					pPath="";
				}
				sysMenu.setMenu_path(pPath);
			}
		}
		if(StringUtils.isEmpty(sysMenu.getIconCls())) {
			sysMenu.setIconCls("&#xe857;");
		}
		model.addAttribute("menuInfo", sysMenu);
		return "/system/menu/menuEdit";
	}
	
	/**
	 * 递归获取父节点数据
	 * @param pid
	 * @param menuName
	 * @return
	 */
	private String findMenuPath(int pid,String menuName){
		if(menuName==null){
			menuName="";
		}
		SysMenu smenu=sysMenuMapper.findSysMenuById(pid+"");
		if(smenu==null){
			return menuName;
		}
		menuName=smenu.getMenu_name();
		if(smenu.getPid()>0){
			String pMenuName=findMenuPath(smenu.getPid(),menuName);
			menuName=pMenuName+"/"+menuName;
		}
		return menuName;
	}
	
	
	@ResponseBody
	@SysLog(mod="系统管理",name="新增菜单")
    @RequestMapping(value="/saveAddMenu",method = RequestMethod.POST)
	public WebResult saveAddMenu(SysMenu sysMenu) {
		rs=new WebResult();
		int code=sysMenuMapper.insertMenu(sysMenu);
		if(code>0) {
			rs.setOK("添加成功");
		}else {
			rs.setNo("添加失败");
		}
		return rs;
	}
	
	@SysLog(mod="系统管理",name="编辑菜单")
	@ResponseBody
    @RequestMapping(value="/monfiyAddMenu",method = RequestMethod.POST)
	public WebResult monfiyAddMenu(SysMenu sysMenu) {
		rs=new WebResult();
		int code=sysMenuMapper.updateMenu(sysMenu);
		if(code>0) {
			rs.setOK("修改成功");
		}else {
			rs.setNo("修改失败");
		}
		return rs;
	}
	
	@ResponseBody
    @RequestMapping(value="/allMenuTree",method = RequestMethod.POST)
	public List<ZTreeBean> allMenuTree(){
		List<ZTreeBean> authTreeList=new ArrayList<ZTreeBean>();
		
		List<SysMenu> allMenuList=sysMenuMapper.findAllSysMenusBy();
		
		ZTreeBean rootBean=new ZTreeBean();
		rootBean.setId("0");
		rootBean.setpId("0");
		rootBean.setName("根菜单");
		rootBean.setOpen(true);
		authTreeList.add(rootBean);
		
		for(SysMenu sysMenu:allMenuList){
			ZTreeBean authTreeBean=new ZTreeBean();
			authTreeBean.setId(sysMenu.getId()+"");
			authTreeBean.setpId(sysMenu.getPid()+"");
			authTreeBean.setName(sysMenu.getMenu_name());
			authTreeBean.setOpen(true);
			
			authTreeList.add(authTreeBean);
		}
		
		return authTreeList;
	}
	
	
	@ResponseBody
	@SysLog(mod="系统管理",name="删除菜单")
    @RequestMapping(value="/deleteMenu",method = RequestMethod.POST)
	public WebResult deleteMenu(String delId){
		rs=new WebResult();
		if(StringUtils.isEmpty(delId)) {
			rs.setNo("参数非法");
			return rs;
		}
		int i=sysMenuMapper.deleteMenuById(delId);
		
		
		if(i>=1){
			List<SysMenu>list=sysMenuMapper.findChildByPid(Integer.valueOf(delId));
			if(null!=list&&list.size()>0) {
				int j=sysMenuMapper.deleteMenuByPid(delId);
				if(j>=1) {
					rs.setOK("删除成功！");
				}else {
					rs.setNo("删除失败！");
				}
			}
			rs.setOK("删除成功！");
		}else{
			rs.setNo("删除失败！");
		}
		return rs;
	}


}
