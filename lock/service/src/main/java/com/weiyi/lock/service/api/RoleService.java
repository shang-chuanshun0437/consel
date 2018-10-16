package com.weiyi.lock.service.api;

import com.weiyi.lock.dao.entity.Role;
import com.weiyi.lock.service.domain.RoleDTO;

import java.util.List;

public interface RoleService
{
    void addRole(RoleDTO roleDTO);

    RoleDTO queryRoleByPhone(Long userPhone);

    String queryUserRoleByPhone(Long userPhone);
}
