package com.weiyi.lock.response;

import com.weiyi.lock.service.response.GetOpenDoorHistoryInfoRes;

public class QueryOpenHistoryResponse extends BaseResponse
{
    private int count;

    private GetOpenDoorHistoryInfoRes[] getOpenDoorHistoryInfoRes;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public GetOpenDoorHistoryInfoRes[] getGetOpenDoorHistoryInfoRes() {
        return getOpenDoorHistoryInfoRes;
    }

    public void setGetOpenDoorHistoryInfoRes(GetOpenDoorHistoryInfoRes[] getOpenDoorHistoryInfoRes) {
        this.getOpenDoorHistoryInfoRes = getOpenDoorHistoryInfoRes;
    }
}
