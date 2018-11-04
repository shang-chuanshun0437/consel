package com.weiyi.lock.service.impl;

import com.weiyi.lock.common.utils.CopyProperties;
import com.weiyi.lock.dao.entity.OpenDoorHistory;
import com.weiyi.lock.dao.entity.OpenDoorListDTO;
import com.weiyi.lock.dao.mapper.OpenDoorHistoryMapper;
import com.weiyi.lock.service.api.OpenDoorHistoryService;
import com.weiyi.lock.service.response.GetOpenDoorHistoryInfoRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OpenDoorHistorySpi implements OpenDoorHistoryService
{
    private Logger logger = LoggerFactory.getLogger(OpenDoorHistorySpi.class);

    @Autowired
    private OpenDoorHistoryMapper openDoorHistoryMapper;

    public void addHistory(GetOpenDoorHistoryInfoRes getOpenDoorHistoryInfoRes)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter addHistory() func. the user is:{}", getOpenDoorHistoryInfoRes.getUserPhone());
        }

        openDoorHistoryMapper.addHistory(getOpenDoorHistoryInfoRes);
    }

    public List<GetOpenDoorHistoryInfoRes> queryOpenHistory(OpenDoorListDTO openDoorListDTO)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryHistoryList() func. the device num :{}",openDoorListDTO.getDeviceNum());
        }
        List<GetOpenDoorHistoryInfoRes> getOpenDoorHistoryInfoRess = new ArrayList<GetOpenDoorHistoryInfoRes>();

        List<OpenDoorHistory> openDoorHistories = openDoorHistoryMapper.queryOpenHistory(openDoorListDTO);

        if (openDoorHistories != null && openDoorHistories.size() > 0)
        {
            for (OpenDoorHistory openDoorHistory : openDoorHistories)
            {
                GetOpenDoorHistoryInfoRes getOpenDoorHistoryInfoRes = new GetOpenDoorHistoryInfoRes();
                CopyProperties.copy(getOpenDoorHistoryInfoRes,openDoorHistory);
                getOpenDoorHistoryInfoRess.add(getOpenDoorHistoryInfoRes);
            }
        }
        return getOpenDoorHistoryInfoRess;
    }
}
