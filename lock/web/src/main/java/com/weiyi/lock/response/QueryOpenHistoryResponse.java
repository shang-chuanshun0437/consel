package com.weiyi.lock.response;

import com.weiyi.lock.service.dto.OpenDoorHistoryDTO;

public class QueryOpenHistoryResponse extends BaseResponse
{
    private int count;

    private OpenDoorHistoryDTO[] openDoorHistoryDTOS;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public OpenDoorHistoryDTO[] getOpenDoorHistoryDTOS() {
        return openDoorHistoryDTOS;
    }

    public void setOpenDoorHistoryDTOS(OpenDoorHistoryDTO[] openDoorHistoryDTOS) {
        this.openDoorHistoryDTOS = openDoorHistoryDTOS;
    }
}
