package com.weiyi.lock.dao.mapper;

import com.weiyi.lock.dao.entity.Role;
import com.weiyi.lock.dao.request.GetRoleRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleMapper
{
    void addRole(Role role);

    void updateRole(Role role);

    void deleteRole(int id);

    List<Role> queryRole(GetRoleRequest request);

    int queryRoleCount(GetRoleRequest request);
}
