package com.weiyi.lock.service.impl;

import com.weiyi.lock.common.utils.CopyProperties;
import com.weiyi.lock.dao.domain.OpenDoorListDomain;
import com.weiyi.lock.dao.entity.OpenDoorHistory;
import com.weiyi.lock.dao.entity.OpenDoorListDTO;
import com.weiyi.lock.dao.mapper.OpenDoorHistoryMapper;
import com.weiyi.lock.service.api.OpenDoorHistoryService;
import com.weiyi.lock.service.dto.OpenDoorHistoryDTO;
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

    public void addHistory(OpenDoorHistoryDTO openDoorHistoryDTO)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter addHistory() func. the user is:{}",openDoorHistoryDTO.getUserPhone());
        }

        openDoorHistoryMapper.addHistory(openDoorHistoryDTO);
    }

    public List<OpenDoorHistoryDTO> queryOpenHistory(OpenDoorListDTO openDoorListDTO)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryHistoryList() func. the device num :{}",openDoorListDTO.getDeviceNum());
        }
        List<OpenDoorHistoryDTO> openDoorHistoryDTOS = new ArrayList<OpenDoorHistoryDTO>();

        List<OpenDoorHistory> openDoorHistories = openDoorHistoryMapper.queryOpenHistory(openDoorListDTO);

        if (openDoorHistories != null && openDoorHistories.size() > 0)
        {
            for (OpenDoorHistory openDoorHistory : openDoorHistories)
            {
                OpenDoorHistoryDTO openDoorHistoryDTO = new OpenDoorHistoryDTO();
                CopyProperties.copy(openDoorHistoryDTO,openDoorHistory);
                openDoorHistoryDTOS.add(openDoorHistoryDTO);
            }
        }
        return openDoorHistoryDTOS;
    }
}
