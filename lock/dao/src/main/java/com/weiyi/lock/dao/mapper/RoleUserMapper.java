package com.weiyi.lock.dao.mapper;

import com.weiyi.lock.dao.entity.RoleUser;
import org.springframework.stereotype.Service;

@Service
public interface RoleUserMapper
{
    void addRole(RoleUser roleUser);

    RoleUser queryRoleByPhone(Long userPhone);

    String queryUserRoleByPhone(Long userPhone);
}
