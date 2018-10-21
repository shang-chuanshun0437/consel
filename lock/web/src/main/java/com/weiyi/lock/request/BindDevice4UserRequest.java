package com.weiyi.lock.request;

public class BindDevice4UserRequest extends BaseRequest
{
    //待绑定的设备
    private Long deviceNum;

    //待绑定的用户
    private Long needBindPhone;

    public Long getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(Long deviceNum) {
        this.deviceNum = deviceNum;
    }

    public Long getNeedBindPhone() {
        return needBindPhone;
    }

    public void setNeedBindPhone(Long needBindPhone) {
        this.needBindPhone = needBindPhone;
    }
}
