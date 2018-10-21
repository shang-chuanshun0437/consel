package com.weiyi.lock.response;

import com.weiyi.lock.service.dto.DeviceDTO;

public class QueryDeviceResponse extends BaseResponse
{
    private int count;

    private DeviceDTO[] deviceDTOS;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public DeviceDTO[] getDeviceDTOS() {
        return deviceDTOS;
    }

    public void setDeviceDTOS(DeviceDTO[] deviceDTOS) {
        this.deviceDTOS = deviceDTOS;
    }
}
