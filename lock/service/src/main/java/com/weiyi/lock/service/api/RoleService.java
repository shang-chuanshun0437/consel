package com.weiyi.lock.service.api;

import com.weiyi.lock.service.dto.RoleDTO;

public interface RoleService
{
    void addRole(RoleDTO roleDTO);

    RoleDTO queryRoleByPhone(Long userPhone);

    String queryUserRoleByPhone(Long userPhone);
}
