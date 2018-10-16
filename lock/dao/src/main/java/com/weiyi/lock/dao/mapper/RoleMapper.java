package com.weiyi.lock.dao.mapper;

import com.weiyi.lock.dao.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleMapper
{
    void addRole(Role role);

    Role queryRoleByPhone(Long userPhone);

    String queryUserRoleByPhone(Long userPhone);
}
