package com.weiyi.lock.response;

import com.weiyi.lock.dao.entity.DeviceIn;

public class QueryDeviceInResponse extends BaseResponse
{
    private int count;

    private DeviceIn[] deviceIns;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public DeviceIn[] getDeviceIns() {
        return deviceIns;
    }

    public void setDeviceIns(DeviceIn[] deviceIns) {
        this.deviceIns = deviceIns;
    }
}
