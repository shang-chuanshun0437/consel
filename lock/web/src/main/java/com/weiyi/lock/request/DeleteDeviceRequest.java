package com.weiyi.lock.request;

public class DeleteDeviceRequest extends BaseRequest
{
    private Long deviceNum;

    public Long getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(Long deviceNum) {
        this.deviceNum = deviceNum;
    }
}
