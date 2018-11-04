package com.weiyi.lock.request;

public class QueryDeviceUsersRequest extends BaseRequest
{
    private Long deviceNum;

    private String deviceName;

    private Integer currentPage;

    private Long needPhone;

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

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Long getNeedPhone() {
        return needPhone;
    }

    public void setNeedPhone(Long needPhone) {
        this.needPhone = needPhone;
    }
}
