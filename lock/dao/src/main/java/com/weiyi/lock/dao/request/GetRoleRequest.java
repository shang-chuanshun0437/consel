package com.weiyi.lock.dao.request;

import com.weiyi.lock.dao.entity.Role;

public class GetRoleRequest extends Role
{
    private int currentPage;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
