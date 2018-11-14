package com.weiyi.lock.dao.request;

import com.weiyi.lock.dao.entity.DeviceIn;

public class QueryDeviceInListReq extends DeviceIn
{
    private int currentPage;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
