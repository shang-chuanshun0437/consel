package com.weiyi.lock.dao.entity;

import com.weiyi.lock.common.utils.TimeUtil;

public class UserAssociateDevice
{
    private int id;

    private Long userPhone;

    private Long deviceNum;

    private String createTime;

    private String updateTime;

    private String expiryDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(Long userPhone) {
        this.userPhone = userPhone;
    }

    public Long getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(Long deviceNum) {
        this.deviceNum = deviceNum;
    }

    public String getCreateTime() {
        return TimeUtil.getDateTime(createTime);
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return TimeUtil.getDateTime(updateTime);
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getExpiryDate() {
        return TimeUtil.getDateTime(expiryDate);
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
