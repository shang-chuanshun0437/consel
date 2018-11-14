package com.weiyi.lock.response;

import com.weiyi.lock.dao.response.QueryDeviceUserRes;

public class QueryDeviceUserResponse extends BaseResponse
{
    private int count;

    private QueryDeviceUserRes[] queryDeviceUserRes;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public QueryDeviceUserRes[] getQueryDeviceUserRes() {
        return queryDeviceUserRes;
    }

    public void setQueryDeviceUserRes(QueryDeviceUserRes[] queryDeviceUserRes) {
        this.queryDeviceUserRes = queryDeviceUserRes;
    }
}
