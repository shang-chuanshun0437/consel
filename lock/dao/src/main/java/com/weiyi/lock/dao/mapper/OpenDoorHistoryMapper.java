package com.weiyi.lock.dao.mapper;

import com.weiyi.lock.dao.domain.OpenDoorListDomain;
import com.weiyi.lock.dao.entity.OpenDoorHistory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OpenDoorHistoryMapper
{
    void addHistory(OpenDoorHistory openDoorHistory);

    List<OpenDoorHistory> queryOpenHistory(OpenDoorListDomain openDoorListDomain);
}
