package com.weiyi.lock.response;

import com.weiyi.lock.dao.response.QueryUnManageDeviceOutRes;

public class QueryUnManageDeviceResponse extends BaseResponse
{
    private int count;

    private QueryUnManageDeviceOutRes[] queryUnManageDeviceOutRes;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public QueryUnManageDeviceOutRes[] getQueryUnManageDeviceOutRes() {
        return queryUnManageDeviceOutRes;
    }

    public void setQueryUnManageDeviceOutRes(QueryUnManageDeviceOutRes[] queryUnManageDeviceOutRes) {
        this.queryUnManageDeviceOutRes = queryUnManageDeviceOutRes;
    }
}
