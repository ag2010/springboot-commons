package com.hr.mapper.system;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hr.bean.system.SysRole;
@Mapper
public interface SysRoleMapper {
	
	public SysRole findSysRoleById(int roleId);
	
	public List<SysRole> listAllRoles();

	public int deleteRoleById(String role_id);
	
	public int insertRole(SysRole sysRole);
	
	public List<SysRole> findRoleMenusByCode(String menu_code);
	
	public List<SysRole> findRoleResourceByCode(String oldCode);
	
	public int saveRoleMenus(String rid,String role_menu);
	
	public int saveRoleResources(@Param("rid")String rid,@Param("role_permissions")String role_permissions);
	
	public int editRole(SysRole sysRole);
	
	public int editRoleStatus(@Param("roleId")String roleId,@Param("status")int status);
	
	public int editRoleMenuPers(@Param("roleId")String roleId,@Param("menuPers")String menuPers);
	
}
