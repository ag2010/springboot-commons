package com.hr.mapper.system;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hr.bean.system.PageInfo;
import com.hr.bean.system.SysPermissions;

@Mapper
public interface SysPermissionsMapper {

    public List<String> findAllPerGroups();

    public List<SysPermissions> findAllPerListGroups();

    public SysPermissions findResourceByCode(String resourceCode);

    public int listAllPermissionsBySearchCount(SysPermissions sysPermissions);

    public List<SysPermissions> findAllPermissionsList();

    public List<SysPermissions> listAllPermissionsBySearch(PageInfo<SysPermissions> sysPermissions);

    public List<SysPermissions> listAllPermissions();

    public List<SysPermissions> listAllPermissionsByCode(String menu_code);

    public int saveAddResource(SysPermissions sysPermissions);

    public int updateResources(SysPermissions sysPermissions);

    public int deleteResources(int rsid);

    public SysPermissions findResourceObj(int rsid);

}
