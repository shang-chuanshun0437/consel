package com.weiyi.lock.request;

public class ModifyDeviceNameRequest extends BaseRequest
{
    private String deviceName;

    private Long deviceNum;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Long getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(Long deviceNum) {
        this.deviceNum = deviceNum;
    }
}
