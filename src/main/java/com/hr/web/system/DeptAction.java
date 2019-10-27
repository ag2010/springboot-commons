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

import com.hr.bean.system.ZTreeBean;
import com.hr.common.log.system.SysLog;
import com.hr.bean.system.SysDept;
import com.hr.bean.system.WebResult;
import com.hr.mapper.system.SysDeptMapper;

@Controller
@RequestMapping("/system")
public class DeptAction extends BaseAction{

	
	@Autowired
	private SysDeptMapper sysDeptMapper;
	
	@RequestMapping("/deptList")
	public String deptList(Model model){
		return "/system/dept/deptList";
	}
	
	@RequestMapping("/toEditDept")
	public String toEditDept(Model model,String id){
		SysDept sysDept=new SysDept();
		if(!StringUtils.isEmpty(id)){
			sysDept=sysDeptMapper.findSysDeptById(id);
			if(sysDept!=null && !StringUtils.isEmpty(sysDept.getDept_path())){
				int pPathIndex=sysDept.getDept_path().lastIndexOf("/");
				String pPath="";
				if(pPathIndex>0){
					pPath=sysDept.getDept_path().substring(0,pPathIndex);
				}else{
					pPath="";
				}
				sysDept.setDept_path(pPath);
			}
			
		}
		model.addAttribute("deptInfo", sysDept);
		return "/system/dept/deptEdit";
	}
	
	@ResponseBody
    @RequestMapping(value="/queryDeptList",method = RequestMethod.POST)
	public WebResult queryDeptList(String uid){
		rs=new WebResult(OK);
		List<SysDept> allDeptList=sysDeptMapper.fildAllDepts();
		rs.setData(allDeptList);
		return rs;
	}
	
	@ResponseBody
	@SysLog(mod="系统管理",name="新增部门")
    @RequestMapping(value="/saveAddDept",method = RequestMethod.POST)
	public WebResult saveAddDept(SysDept sysDept) {
		rs=new WebResult();
		int code=sysDeptMapper.saveDept(sysDept);
		if(code>0) {
			rs.setOK("添加成功");
		}else {
			rs.setNo("添加失败");
		}
		return rs;
	}
	
	
	@ResponseBody
	@SysLog(mod="系统管理",name="编辑部门")
    @RequestMapping(value="/monfiyAddDept",method = RequestMethod.POST)
	public WebResult monfiyAddDept(SysDept sysDept) {
		rs=new WebResult();
		int code=sysDeptMapper.updateDept(sysDept);
		if(code>0) {
			rs.setOK("修改成功");
		}else {
			rs.setNo("修改失败");
		}
		return rs;
	}
	
	@ResponseBody
	@SysLog(mod="系统管理",name="删除部门")
    @RequestMapping(value="/deleteDept",method = RequestMethod.POST)
	public WebResult deleteUser(String delId){
		rs=new WebResult();
		if(StringUtils.isEmpty(delId)) {
			rs.setNo("参数非法");
			return rs;
		}
		
		int i=sysDeptMapper.delteDept(Integer.parseInt(delId));
		if(i>=1){
			List<SysDept> list=sysDeptMapper.listSubDeptByParentId(Integer.parseInt(delId));
			if(null!=list&&list.size()>0) {
				int j=sysDeptMapper.delteDeptByPid(Integer.parseInt(delId));
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
	
	
	@ResponseBody
    @RequestMapping(value="/allDeptTree",method = RequestMethod.POST)
	public List<ZTreeBean> allDeptTree(){
		List<ZTreeBean> authTreeList=new ArrayList<ZTreeBean>();
		
		List<SysDept> allDeptList=sysDeptMapper.fildAllDepts();
		
		ZTreeBean rootBean=new ZTreeBean();
		rootBean.setId("0");
		rootBean.setpId("0");
		rootBean.setName("根部门");
		rootBean.setOpen(true);
		authTreeList.add(rootBean);
		
		for(SysDept sysDept:allDeptList){
			ZTreeBean authTreeBean=new ZTreeBean();
			authTreeBean.setId(sysDept.getId()+"");
			authTreeBean.setpId(sysDept.getPid()+"");
			authTreeBean.setName(sysDept.getDept_name());
			authTreeBean.setOpen(true);
			
			authTreeList.add(authTreeBean);
		}
		
		return authTreeList;
	}
	
	
	@ResponseBody
    @RequestMapping(value="/allDeptTreeNoRoot",method = RequestMethod.POST)
	public List<ZTreeBean> allDeptTreeNoRoot(){
		List<ZTreeBean> authTreeList=new ArrayList<ZTreeBean>();
		
		List<SysDept> allDeptList=sysDeptMapper.fildAllDepts();
		for(SysDept sysDept:allDeptList){
			ZTreeBean authTreeBean=new ZTreeBean();
			authTreeBean.setId(sysDept.getId()+"");
			authTreeBean.setpId(sysDept.getPid()+"");
			authTreeBean.setName(sysDept.getDept_name());
			authTreeBean.setOpen(true);
			
			authTreeList.add(authTreeBean);
		}
		
		return authTreeList;
	}
	
}
