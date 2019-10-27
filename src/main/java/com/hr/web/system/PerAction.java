package com.hr.web.system;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hr.bean.system.PerGroupInfo;
import com.hr.bean.system.SysPermissions;
import com.hr.bean.system.WebResult;
import com.hr.bean.system.ZTreeBean;
import com.hr.common.log.system.SysLog;
import com.hr.mapper.system.SysPermissionsMapper;



@Controller
@RequestMapping("/system")
public class PerAction extends BaseAction {

    @Autowired
    private SysPermissionsMapper sysPermissionsMapper;

    @RequestMapping("/perList")
    public String perList() {
        return "/system/per/perList";
    }

    @ResponseBody
    @RequestMapping(value = "/getAllPerGroups", method = RequestMethod.POST)
    public List<PerGroupInfo> getAllPerGroups() {
        List<PerGroupInfo> groups = new ArrayList<PerGroupInfo>();
        List<String> lists = sysPermissionsMapper.findAllPerGroups();
        for (String groupName : lists) {
            PerGroupInfo group = new PerGroupInfo();
            group.setGroupCode(groupName);
            group.setGroupName(groupName);
            groups.add(group);
        }
        return groups;
    }

    @ResponseBody
    @RequestMapping(value = "/perGroupList", method = RequestMethod.POST)
    public List<ZTreeBean> perGroupList() {
        List<ZTreeBean> authTreeList = new ArrayList<ZTreeBean>();
        List<String> lists = sysPermissionsMapper.findAllPerGroups();

        ZTreeBean abean = new ZTreeBean();
        abean.setId("1");
        abean.setpId("0");
        abean.setName("全部");
        abean.setOpen(true);

        authTreeList.add(abean);

        for (String perGroupName : lists) {
            ZTreeBean authBean = new ZTreeBean();
            authBean.setId(perGroupName);
            authBean.setName(perGroupName);
            authBean.setpId("1");
            authBean.setOpen(true);
            authTreeList.add(authBean);
        }

        return authTreeList;
    }

    @ResponseBody
    @RequestMapping(value = "/queryPreList", method = RequestMethod.POST)
    public WebResult<SysPermissions> queryPreList() {
        rs = new WebResult<SysPermissions>(OK);
        
        // 分组信息
        List<String> lists = sysPermissionsMapper.findAllPerGroups();
        
        List<SysPermissions> perLists = sysPermissionsMapper.findAllPermissionsList();
        
        for (int i = 0; i < lists.size(); i++) {
            SysPermissions sp = new SysPermissions();
            sp.setId(99999 + i);
            sp.setPermissions_name(lists.get(i));
            sp.setPermissions_code(lists.get(i));
            sp.setPermissions_group("");
            perLists.add(0, sp);
        }
        
        rs.setData(perLists);
        return rs;
    }

    @ResponseBody
    @SysLog(mod = "系统管理", name = "新增权限")
    @RequestMapping(value = "/saveAddPer", method = RequestMethod.POST)
    public WebResult saveAddPer(SysPermissions sysPermissions) {
        rs = new WebResult();
        int code = sysPermissionsMapper.saveAddResource(sysPermissions);
        if (code > 0) {
            rs.setOK("添加成功");
        } else {
            rs.setNo("添加失败");
        }
        return rs;
    }

    @ResponseBody
    @RequestMapping(value = "/getPerInfo", method = RequestMethod.POST)
    public WebResult getPerInfo(String rsid) {
        rs = new WebResult();

        if (!StringUtils.isEmpty(rsid)) {
            try {
                SysPermissions sysPer = sysPermissionsMapper.findResourceObj(Integer.parseInt(rsid));
                rs.setDataSingle(sysPer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return rs;
    }

    @ResponseBody
    @SysLog(mod = "系统管理", name = "编辑权限")
    @RequestMapping(value = "/monfiyAddPer", method = RequestMethod.POST)
    public WebResult monfiyAddPer(SysPermissions sysPermissions) {
        rs = new WebResult();
        int code = sysPermissionsMapper.updateResources(sysPermissions);
        if (code > 0) {
            rs.setOK("修改成功");
        } else {
            rs.setNo("修改失败");
        }
        return rs;
    }

    @ResponseBody
    @SysLog(mod = "系统管理", name = "删除权限")
    @RequestMapping(value = "/deletePer", method = RequestMethod.POST)
    public WebResult deletePer(String delId) {
        rs = new WebResult();
        if (StringUtils.isEmpty(delId)) {
            rs.setNo("参数非法");
            return rs;
        }
        int i = sysPermissionsMapper.deleteResources(Integer.parseInt(delId));
        if (i >= 1) {
            rs.setOK("删除成功！");
        } else {
            rs.setNo("删除失败！");
        }
        return rs;
    }
}
