package com.weiyi.lock.response;

import com.weiyi.lock.dao.entity.Role;

public class QueryRoleResponse extends BaseResponse
{
    private int count;

    private Role[] roles;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Role[] getRoles() {
        return roles;
    }

    public void setRoles(Role[] roles) {
        this.roles = roles;
    }
}
