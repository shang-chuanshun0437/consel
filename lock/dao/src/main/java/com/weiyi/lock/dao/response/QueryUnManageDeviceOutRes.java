package com.weiyi.lock.dao.response;

import com.weiyi.lock.dao.entity.DeviceOut;

public class QueryUnManageDeviceOutRes extends DeviceOut
{
    private String expiryDate;

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
