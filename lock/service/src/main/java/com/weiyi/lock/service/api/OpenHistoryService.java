package com.weiyi.lock.service.api;

import com.weiyi.lock.dao.entity.OpenDoorHistory;
import com.weiyi.lock.dao.request.GetOpenHistoryRequest;

import java.util.List;

public interface OpenHistoryService
{
    void addOpenHistory(OpenDoorHistory openDoorHistory);

    List<OpenDoorHistory> queryOpenHistoryList(GetOpenHistoryRequest request);

    int queryOpenHistoryListCount(GetOpenHistoryRequest request);
}
