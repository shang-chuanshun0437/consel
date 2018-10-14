package com.weiyi.lock.request;

public class AddDeviceRequest
{
    private Long deviceNum;

    private String deviceName;

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

    @Override
    public String toString() {
        return "AddDeviceRequest{" +
                "deviceNum=" + deviceNum +
                ", deviceName='" + deviceName + '\'' +
                '}';
    }
}
