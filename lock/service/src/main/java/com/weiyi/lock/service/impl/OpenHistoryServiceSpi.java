package com.weiyi.lock.service.impl;

import com.weiyi.lock.dao.entity.OpenDoorHistory;
import com.weiyi.lock.dao.mapper.OpenDoorHistoryMapper;
import com.weiyi.lock.dao.request.GetOpenHistoryRequest;
import com.weiyi.lock.service.api.OpenHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpenHistoryServiceSpi implements OpenHistoryService
{
    private Logger logger = LoggerFactory.getLogger(OpenHistoryServiceSpi.class);

    @Autowired
    private OpenDoorHistoryMapper openDoorHistoryMapper;

    public void addOpenHistory(OpenDoorHistory openDoorHistory) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter addOpenHistory() func");
        }
        openDoorHistoryMapper.addHistory(openDoorHistory);
    }

    public List<OpenDoorHistory> queryOpenHistoryList(GetOpenHistoryRequest request) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryOpenHistoryList() func");
        }

        return openDoorHistoryMapper.queryOpenHistory(request);
    }

    public int queryOpenHistoryListCount(GetOpenHistoryRequest request) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryOpenHistoryListCount() func");
        }

        return openDoorHistoryMapper.queryOpenHistoryCount(request);
    }
}
