package com.weiyi.lock.service.api;

import com.weiyi.lock.dao.entity.OpenDoorListDTO;
import com.weiyi.lock.service.response.GetOpenDoorHistoryInfoRes;

import java.util.List;

public interface OpenDoorHistoryService
{
    void addHistory(GetOpenDoorHistoryInfoRes getOpenDoorHistoryInfoRes);

    List<GetOpenDoorHistoryInfoRes> queryOpenHistory(OpenDoorListDTO openDoorListDTO);
}
