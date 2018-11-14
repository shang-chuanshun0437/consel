package com.weiyi.lock.request;

public class QueryDeviceOutRequest extends BaseRequest
{
    private Long deviceNum;

    private String version;

    private String deviceName;

    private Long ownerPhone;

    private Integer currentPage;

    private Integer status;

    public Long getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(Long deviceNum) {
        this.deviceNum = deviceNum;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Long getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(Long ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
