package com.weiyi.lock.request;

public class UnbindDevice4UserRequest extends BaseRequest
{
    //待解绑的设备编号
    private Long deviceNum;

    //待解绑的用户
    private Long needUnBindPhone;

    public Long getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(Long deviceNum) {
        this.deviceNum = deviceNum;
    }

    public Long getNeedUnBindPhone() {
        return needUnBindPhone;
    }

    public void setNeedUnBindPhone(Long needUnBindPhone) {
        this.needUnBindPhone = needUnBindPhone;
    }
}
