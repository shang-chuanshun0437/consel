package com.weiyi.lock.dao.mapper;

import com.weiyi.lock.dao.entity.RoleUser;
import com.weiyi.lock.dao.request.GetRoleUserRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleUserMapper
{
    void addRole(RoleUser roleUser);

    RoleUser queryRoleByPhone(Long userPhone);

    List<RoleUser> queryRole(GetRoleUserRequest request);

    int queryRoleCount(GetRoleUserRequest request);

    String queryUserRoleByPhone(Long userPhone);
}
