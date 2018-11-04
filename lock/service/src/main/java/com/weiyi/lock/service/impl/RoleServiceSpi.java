package com.weiyi.lock.service.impl;

import com.weiyi.lock.common.utils.CopyProperties;
import com.weiyi.lock.dao.entity.Role;
import com.weiyi.lock.dao.mapper.RoleMapper;
import com.weiyi.lock.service.api.RoleService;
import com.weiyi.lock.service.response.GetRoleInfoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceSpi implements RoleService
{
    private Logger logger = LoggerFactory.getLogger(RoleServiceSpi.class);

    @Autowired
    private RoleMapper roleMapper;

    public void addRole(GetRoleInfoResponse getRoleInfoResponse)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter role addRole() func,user phone:{}", getRoleInfoResponse.getUserPhone());
        }
        Role role = new Role();

        CopyProperties.copy(role, getRoleInfoResponse);

        roleMapper.addRole(role);
    }

    public GetRoleInfoResponse queryRoleByPhone(Long userPhone)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter role addRole() func,user phone:{}",userPhone);
        }

        Role role = roleMapper.queryRoleByPhone(userPhone);

        GetRoleInfoResponse getRoleInfoResponse = new GetRoleInfoResponse();
        CopyProperties.copy(getRoleInfoResponse,role);

        return getRoleInfoResponse;
    }

    public String queryUserRoleByPhone(Long userPhone) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter role queryUserRoleByPhone() func,user phone:{}",userPhone);
        }
        return roleMapper.queryUserRoleByPhone(userPhone);
    }
}
