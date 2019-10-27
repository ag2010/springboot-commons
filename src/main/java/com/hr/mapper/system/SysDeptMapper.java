package com.hr.mapper.system;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hr.bean.system.SysDept;

@Mapper
public interface SysDeptMapper {
	
	public List<SysDept> fildAllDepts();
	
	public List<SysDept> listAllParentDept();
	
	public SysDept findSysDeptById(String deptId);
	
	public int saveDept(SysDept dept);
	
	public int updateDept(SysDept dept);
	
	public int delteDept(int deptId);

	public List<SysDept> listSubDeptByParentId(int parentId);
	
	public int delteDeptByPid(int pId);
	


}
