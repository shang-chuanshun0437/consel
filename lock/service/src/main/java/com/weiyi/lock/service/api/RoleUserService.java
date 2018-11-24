package com.weiyi.lock.service.api;

import com.weiyi.lock.dao.entity.RoleUser;
import com.weiyi.lock.dao.request.GetRoleUserRequest;

import java.util.List;

public interface RoleUserService
{
    void addRole(RoleUser roleUser);

    void deleteRoleUserByPhone(Long userPhone);

    RoleUser queryRoleByPhone(Long userPhone);

    String queryUserRoleByPhone(Long userPhone);

    List<RoleUser> queryRole(GetRoleUserRequest request);

    int queryRoleCount(GetRoleUserRequest request);
}
