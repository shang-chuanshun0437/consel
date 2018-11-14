package com.weiyi.lock.dao.request;

import com.weiyi.lock.dao.entity.DeviceOut;

public class QueryUnManageDeviceOutReq extends DeviceOut
{
    private int currentPage;

    private Long userPhone;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public Long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(Long userPhone) {
        this.userPhone = userPhone;
    }
}
