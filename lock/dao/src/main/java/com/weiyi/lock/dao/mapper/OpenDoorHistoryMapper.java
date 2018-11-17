package com.weiyi.lock.dao.mapper;

import com.weiyi.lock.dao.entity.OpenDoorHistory;
import com.weiyi.lock.dao.request.GetOpenHistoryRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OpenDoorHistoryMapper
{
    void addHistory(OpenDoorHistory openDoorHistory);

    List<OpenDoorHistory> queryOpenHistory(GetOpenHistoryRequest request);

    int queryOpenHistoryCount(GetOpenHistoryRequest request);
}
