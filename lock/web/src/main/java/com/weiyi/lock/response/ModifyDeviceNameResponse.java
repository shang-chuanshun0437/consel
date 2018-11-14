package com.weiyi.lock.response;

import com.weiyi.lock.dao.entity.DeviceOut;

public class ModifyDeviceNameResponse extends BaseResponse
{
    private int count;

    private DeviceOut[] deviceOuts;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public DeviceOut[] getDeviceOuts() {
        return deviceOuts;
    }

    public void setDeviceOuts(DeviceOut[] deviceOuts) {
        this.deviceOuts = deviceOuts;
    }
}
