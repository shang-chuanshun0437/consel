package com.weiyi.lock.dao.request;


public class ChangeDeviceUserRequest
{
    private Long userPhone;

    private Long newUserPhone;

    private String updateTime;

    public Long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(Long userPhone) {
        this.userPhone = userPhone;
    }

    public Long getNewUserPhone() {
        return newUserPhone;
    }

    public void setNewUserPhone(Long newUserPhone) {
        this.newUserPhone = newUserPhone;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
