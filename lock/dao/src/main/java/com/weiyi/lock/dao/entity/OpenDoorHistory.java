package com.weiyi.lock.dao.entity;

import com.weiyi.lock.common.utils.TimeUtil;

public class OpenDoorHistory
{
    private Integer id;

    private Long userPhone;

    private String userName;

    private Long deviceNum;

    private String deviceName;

    private String openTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getOpenTime() {
        return TimeUtil.getDateTime(openTime);
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
