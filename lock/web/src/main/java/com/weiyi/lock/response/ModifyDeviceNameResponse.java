package com.weiyi.lock.response;

import com.weiyi.lock.service.response.GetDeviceInfoResponse;

public class ModifyDeviceNameResponse extends BaseResponse
{
    private int count;

    private GetDeviceInfoResponse[] getDeviceInfoResponses;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public GetDeviceInfoResponse[] getGetDeviceInfoResponses() {
        return getDeviceInfoResponses;
    }

    public void setGetDeviceInfoResponses(GetDeviceInfoResponse[] getDeviceInfoResponses) {
        this.getDeviceInfoResponses = getDeviceInfoResponses;
    }
}
