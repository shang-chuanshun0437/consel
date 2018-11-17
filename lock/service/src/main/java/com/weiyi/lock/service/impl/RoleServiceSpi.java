package com.weiyi.lock.service.impl;

import com.weiyi.lock.dao.entity.Role;
import com.weiyi.lock.dao.mapper.RoleMapper;
import com.weiyi.lock.dao.request.GetRoleRequest;
import com.weiyi.lock.service.api.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceSpi implements RoleService
{
    private Logger logger = LoggerFactory.getLogger(RoleServiceSpi.class);

    @Autowired
    private RoleMapper roleMapper;

    public void addRole(Role role) {
        if(logger.isDebugEnabled())
        {
            logger.debug("inter addRole() func");
        }
        roleMapper.addRole(role);
    }

    public List<Role> queryRole(GetRoleRequest request) {
        if(logger.isDebugEnabled())
        {
            logger.debug("inter queryRole() func");
        }
        return roleMapper.queryRole(request);
    }

    public int queryRoleCount(GetRoleRequest request) {
        if(logger.isDebugEnabled())
        {
            logger.debug("inter queryRole() func");
        }
        return roleMapper.queryRoleCount(request);
    }

    public void updateRole(Role role) {
        if(logger.isDebugEnabled())
        {
            logger.debug("inter queryRole() func");
        }
        roleMapper.updateRole(role);
    }

    public void deleteRole(int id) {
        if(logger.isDebugEnabled())
        {
            logger.debug("inter deleteRole() func");
        }
        roleMapper.deleteRole(id);
    }
}
