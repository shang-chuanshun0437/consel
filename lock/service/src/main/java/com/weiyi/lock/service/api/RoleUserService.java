package com.weiyi.lock.service.api;

import com.weiyi.lock.dao.entity.RoleUser;

public interface RoleUserService
{
    void addRole(RoleUser roleUser);

    RoleUser queryRoleByPhone(Long userPhone);

    String queryUserRoleByPhone(Long userPhone);
}
