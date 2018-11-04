package com.weiyi.lock.dao.response;

import com.weiyi.lock.dao.entity.Device;

public class QueryUnManageDeviceRes extends Device
{
    private String expiryDate;

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
