package com.weiyi.lock.service.impl;

import com.weiyi.lock.dao.entity.RoleUser;
import com.weiyi.lock.dao.mapper.RoleUserMapper;
import com.weiyi.lock.service.api.RoleUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleUserServiceSpi implements RoleUserService
{
    private Logger logger = LoggerFactory.getLogger(RoleUserServiceSpi.class);

    @Autowired
    private RoleUserMapper roleUserMapper;

    public void addRole(RoleUser roleUser)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter roleUser addRole() func,user phone:{}", roleUser.getUserPhone());
        }

        roleUserMapper.addRole(roleUser);
    }

    public RoleUser queryRoleByPhone(Long userPhone)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter roleUser addRole() func,user phone:{}",userPhone);
        }

        return roleUserMapper.queryRoleByPhone(userPhone);
    }

    public String queryUserRoleByPhone(Long userPhone) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter role queryUserRoleByPhone() func,user phone:{}",userPhone);
        }
        return roleUserMapper.queryUserRoleByPhone(userPhone);
    }
}
