package com.hr.mapper.system;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hr.bean.system.SysMenu;

@Mapper
public interface SysMenuMapper {
	
	
	/**
	 * 根据ID查询菜单对象
	 * @param menuId
	 * @return
	 */
	public SysMenu findSysMenuById(@Param("menuId")String menuId);

	/**
	 * 查询所有菜单
	 * @return
	 */
	public List<SysMenu> findAllSysMenusBy();
	
	/**
	 * 查询所有一级菜单
	 * @return
	 */
	public List<SysMenu> findAllOneLevel();
	
	/**
	 * 根据父节点查询所有二级菜单
	 * @param pid
	 * @return
	 */
	public List<SysMenu> findAllTwoLevelByPid(int pid);
	
	/**
	 * 根据父节点查询所有三级菜单
	 * @param pid
	 * @return
	 */
	public List<SysMenu> findAllThirdLevelByPid(int pid);
	
	/**
	 * 新增菜单
	 * @param sysMenu
	 * @return
	 */
	public int insertMenu(SysMenu sysMenu);
	/**
	 * 修改菜单
	 * @param sysMenu
	 * @return
	 */
	public int updateMenu(SysMenu sysMenu);
	
	/**
	 * 修改菜单状态
	 * @param sysMenu
	 * @return
	 */
	public int editstatus(SysMenu sysMenu);
	
	/**
	 * 删除一个菜单
	 * @param menu_id
	 * @return
	 */
	public int deleteMenuById(String menu_id);
	
	
	/**
	 * 级联删除子菜单
	 * @param pid
	 * @return
	 */
	public int deleteMenuByPid(String pid);
	
	/**
	 * 级联查询下边所有菜单
	 * @param pid
	 * @return
	 */
	public List<SysMenu> findChildByPid(int pid);
	
	/**
	 * 查询单个菜单信息
	 * @param code
	 * @return
	 */
	public SysMenu findChildByCode(String code);
}
