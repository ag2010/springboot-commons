package com.hr.web.member;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hr.bean.member.MemUser;
import com.hr.bean.system.PageInfo;
import com.hr.bean.system.WebResult;
import com.hr.common.log.system.SysLog;
import com.hr.mapper.member.MemUserMapper;
import com.hr.util.IpUtil;
import com.hr.util.MD5Util;
import com.hr.web.system.BaseAction;


@Controller
@RequestMapping("/member")
public class MemUserAction extends BaseAction {
    @Autowired
    private MemUserMapper memUserMapper;


    @RequestMapping("/userList")
    public String toUserList() {
        return "/system/member/userList";
    }

    @RequestMapping("/userAdd")
    public String toUserAdd(Model model, String uid) {
        MemUser memUser = new MemUser();
        model.addAttribute("memUser", memUser);
        return "/system/member/userAdd";
    }

    /**
     * 查询用户列表
     * 
     * @param page
     * @param memUser
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findAllMemUser", method = RequestMethod.POST)
    public WebResult<MemUser> findAllMemUser(PageInfo page, MemUser memUser) {
        rs = new WebResult<MemUser>(OK);
        page.setParamBean(memUser);
        List<MemUser> list = memUserMapper.findAllMemUserByParam(page);

        rs.setCount(100);
        rs.setData(list);
        return rs;
    }

    /**
     * 新增用户
     * 
     * @param request
     * @param memUser
     * @return
     */
    @ResponseBody
    @SysLog(mod = "会员管理", name = "新增用户")
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public WebResult doAddUser(HttpServletRequest request, MemUser memUser) {
        rs = new WebResult<MemUser>();

        if (memUser != null) {

            List<MemUser> list = memUserMapper.findMemUserByUsername(memUser);

            if (list.size() > 0) {
                rs.setNo("用户名重复，请重新输入！");
                return rs;
            }

            memUser.setPassword(MD5Util.qrsoftMD5(memUser.getPassword()));
            memUser.setCreate_ip(IpUtil.getIpAddr(request));
            memUser.setLast_ip(IpUtil.getIpAddr(request));
            if (StringUtils.isBlank(memUser.getNickname())) {
                memUser.setNickname(memUser.getUsername());
            }
            memUser.setCredit(100); // 初始信用权重100
            memUser.setSalt(""); // 无盐

            memUser.setCertificate_no(memUser.getCertificate_no() == null ? "" : memUser.getCertificate_no());
            memUser.setMobile(memUser.getMobile() == null ? "" : memUser.getMobile());
            memUser.setEmail(memUser.getEmail() == null ? "" : memUser.getEmail());
            memUser.setLogin_count(0);
            memUserMapper.insert(memUser);
            rs.setOK("新增成功！");
        } else {
            rs.setNo("新增失败！");
        }

        return rs;
    }

    /**
     * 编辑用户
     * 
     * @param request
     * @param memUser
     * @return
     */
    @ResponseBody
    @SysLog(mod = "会员管理", name = "编辑用户")
    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
    public WebResult doEditUser(MemUser memUser) {
        return null;
    }
}
