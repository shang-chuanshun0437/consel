package com.weiyi.lock.response;

import com.weiyi.lock.dao.response.GetAllUserDevice;

public class QueryAllDeviceResponse extends BaseResponse
{
    private int count;

    private GetAllUserDevice[] getAllUserDevices;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public GetAllUserDevice[] getGetAllUserDevices() {
        return getAllUserDevices;
    }

    public void setGetAllUserDevices(GetAllUserDevice[] getAllUserDevices) {
        this.getAllUserDevices = getAllUserDevices;
    }
}
