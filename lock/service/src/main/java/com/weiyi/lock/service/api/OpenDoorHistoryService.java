package com.weiyi.lock.service.api;

import com.weiyi.lock.dao.entity.OpenDoorListDTO;
import com.weiyi.lock.service.dto.OpenDoorHistoryDTO;

import java.util.List;

public interface OpenDoorHistoryService
{
    void addHistory(OpenDoorHistoryDTO openDoorHistoryDTO);

    List<OpenDoorHistoryDTO> queryOpenHistory(OpenDoorListDTO openDoorListDTO);
}
