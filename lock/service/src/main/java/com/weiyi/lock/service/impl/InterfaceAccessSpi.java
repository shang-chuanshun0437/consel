package com.weiyi.lock.service.impl;

import com.weiyi.lock.dao.entity.InterfaceAccess;
import com.weiyi.lock.dao.mapper.InterfaceAccessMapper;
import com.weiyi.lock.dao.request.GetInterfaceAccessRequest;
import com.weiyi.lock.service.api.InterfaceAccessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class InterfaceAccessSpi implements InterfaceAccessService
{
    private Logger logger = LoggerFactory.getLogger(InterfaceAccessSpi.class);

    @Autowired
    private InterfaceAccessMapper interfaceAccessMapper;

    public void addRecord(InterfaceAccess interfaceAccess) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter addRecord() func,the interface is :{}",interfaceAccess.getInterfaceName());
        }

        interfaceAccessMapper.addRecord(interfaceAccess);
    }

    public void deleteRecord(int id) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter deleteRecord() func,the id is :{}",id);
        }

        interfaceAccessMapper.deleteRecord(id);
    }

    public void updateRecord(InterfaceAccess interfaceAccess) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter updateRecord() func,the interface is :{}",interfaceAccess.getInterfaceName());
        }

        interfaceAccessMapper.updateRecord(interfaceAccess);
    }

    public List<InterfaceAccess> queryList(GetInterfaceAccessRequest request) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryList() func,the interface is :{}",request.getInterfaceName());
        }

        return interfaceAccessMapper.queryList(request);
    }

    public InterfaceAccess queryInterfaceAccess(Long userPhone, String interfaceName) {
        GetInterfaceAccessRequest request = new GetInterfaceAccessRequest();

        request.setUserPhone(userPhone);
        request.setCurrentPage(0);
        request.setInterfaceName(interfaceName);

        List<InterfaceAccess> interfaceAccesses = queryList(request);
        if(interfaceAccesses != null && interfaceAccesses.size() > 0)
        {
            return interfaceAccesses.get(0);
        }

        return null;
    }

    public int queryListCount(GetInterfaceAccessRequest request) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryListCount() func,the interface is :{}",request.getInterfaceName());
        }

        return interfaceAccessMapper.queryListCount(request);
    }
}
