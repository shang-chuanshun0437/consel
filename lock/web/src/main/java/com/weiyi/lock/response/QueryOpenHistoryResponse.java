package com.weiyi.lock.response;

import com.weiyi.lock.dao.entity.OpenDoorHistory;

public class QueryOpenHistoryResponse extends BaseResponse
{
    private int count;

    private OpenDoorHistory[] openDoorHistories;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public OpenDoorHistory[] getOpenDoorHistories() {
        return openDoorHistories;
    }

    public void setOpenDoorHistories(OpenDoorHistory[] openDoorHistories) {
        this.openDoorHistories = openDoorHistories;
    }
}
