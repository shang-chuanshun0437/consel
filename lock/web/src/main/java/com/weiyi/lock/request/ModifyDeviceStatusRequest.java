package com.weiyi.lock.request;

public class ModifyDeviceStatusRequest extends BaseRequest
{
    private Long deviceNum;

    private Integer status;

    public Long getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(Long deviceNum) {
        this.deviceNum = deviceNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
