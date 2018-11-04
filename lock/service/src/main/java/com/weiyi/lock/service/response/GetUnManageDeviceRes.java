package com.weiyi.lock.service.response;


import com.weiyi.lock.dao.entity.Device;

public class GetUnManageDeviceRes extends Device
{
    private String expiryDate;

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
