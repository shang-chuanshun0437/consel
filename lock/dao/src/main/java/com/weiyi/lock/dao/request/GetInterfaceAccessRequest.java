package com.weiyi.lock.dao.request;

import com.weiyi.lock.dao.entity.InterfaceAccess;

public class GetInterfaceAccessRequest extends InterfaceAccess
{
    private int currentPage;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
