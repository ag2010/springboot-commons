package com.hr.mapper.member;

import java.util.List;

import com.hr.bean.member.MemUser;
import com.hr.bean.system.PageInfo;

public interface MemUserMapper {
    public int insert(MemUser memUser);

    public int findAllMemUserByParamCount(MemUser memuser);

    public List<MemUser> findAllMemUserByParam(PageInfo<MemUser> memuser);

    public List<MemUser> findMemUserByUsername(MemUser memUser);
}
