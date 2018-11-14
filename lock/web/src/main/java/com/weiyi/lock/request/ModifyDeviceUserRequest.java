package com.weiyi.lock.request;

public class ModifyDeviceUserRequest extends BaseRequest
{
    private Long deviceNum;

    private Long needModifyPhone;

    private String expiryDate;

    public Long getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(Long deviceNum) {
        this.deviceNum = deviceNum;
    }

    public Long getNeedModifyPhone() {
        return needModifyPhone;
    }

    public void setNeedModifyPhone(Long needModifyPhone) {
        this.needModifyPhone = needModifyPhone;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
