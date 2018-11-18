package com.weiyi.lock.dao.request;

import com.weiyi.lock.dao.entity.RoleUser;

public class GetRoleUserRequest extends RoleUser
{
    private int currentPage;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
