package com.weiyi.lock.dao.request;

public class QueryDeviceUserReq
{
    private Long deviceNum;

    private String deviceName;

    //待查询的用户手机号
    private Long userPhone;

    //设备管理员账号
    private Long ownerPhone;

    private int currentPage;

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

    public Long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(Long userPhone) {
        this.userPhone = userPhone;
    }

    public Long getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(Long ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
