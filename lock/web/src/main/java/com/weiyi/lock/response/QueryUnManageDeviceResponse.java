package com.weiyi.lock.response;

import com.weiyi.lock.service.response.GetUnManageDeviceRes;

public class QueryUnManageDeviceResponse extends BaseResponse
{
    private int count;

    private GetUnManageDeviceRes[] getUnManageDeviceRes;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public GetUnManageDeviceRes[] getGetUnManageDeviceRes() {
        return getUnManageDeviceRes;
    }

    public void setGetUnManageDeviceRes(GetUnManageDeviceRes[] getUnManageDeviceRes) {
        this.getUnManageDeviceRes = getUnManageDeviceRes;
    }
}
