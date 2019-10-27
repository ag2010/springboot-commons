package com.hr.mapper.system;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hr.bean.system.PageInfo;
import com.hr.bean.system.SysUsers;

@Mapper
public interface SysUsersMapper {

	
	/**
	 * 添加用户
	 * @param sysUsers
	 * @return
	 */
	public int insertUser(SysUsers sysUsers);
	
	/**
	 * 修改用户
	 * @param sysUsers
	 * @return
	 */
	public int updateUserInfo(SysUsers sysUsers);
	
	
	/**
	 * 删除一个用户
	 * @param uid
	 * @return
	 */
	public int deleteUserById(String uid);
	
	
	//public int batchDeleteUser(String[] uids);
	
	
	/**
	 * 分页搜索用户列表
	 * @param sysUsers
	 * @return
	 */
	public int findSysUsersByParamPageCount(SysUsers sysUsers);
	public List<SysUsers> findSysUsersByParam(PageInfo<SysUsers> sysUsers);
	
	
	
	/**
	 * 查询所有用户
	 * @return
	 */
	public List<SysUsers> findAllSysUsers();
	
	/**
	 * 根据用户名查找用户信息
	 * @param userName
	 * @return
	 */
	public List<SysUsers> findSysUsersByUserName(String userName);
	
	/**
	 * 根据uid获取用户
	 * @param uid
	 * @return
	 */
	public SysUsers findSysUsersById(String uid);
	
	/**
	 * 登录
	 * @param loginName
	 * @param pwd
	 * @return
	 */
	public SysUsers login(String loginName,String pwd);
	
	
	public int modifyPwd(SysUsers sysUsers);
	
}
