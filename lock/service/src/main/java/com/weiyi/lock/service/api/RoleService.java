package com.weiyi.lock.service.api;

import com.weiyi.lock.service.response.GetRoleInfoResponse;

public interface RoleService
{
    void addRole(GetRoleInfoResponse getRoleInfoResponse);

    GetRoleInfoResponse queryRoleByPhone(Long userPhone);

    String queryUserRoleByPhone(Long userPhone);
}
