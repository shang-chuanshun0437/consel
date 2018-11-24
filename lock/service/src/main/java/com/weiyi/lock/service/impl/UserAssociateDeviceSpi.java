package com.weiyi.lock.service.impl;

import com.weiyi.lock.dao.entity.UserAssociateDevice;
import com.weiyi.lock.dao.mapper.UserAssociateDeviceMapper;
import com.weiyi.lock.dao.request.QueryDeviceUserReq;
import com.weiyi.lock.dao.response.QueryDeviceUserRes;
import com.weiyi.lock.service.api.UserAssociateDeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAssociateDeviceSpi implements UserAssociateDeviceService
{
    private Logger logger = LoggerFactory.getLogger(UserAssociateDeviceSpi.class);

    @Autowired
    private UserAssociateDeviceMapper mapper;

    public void bindDevice(UserAssociateDevice userAssociateDevice)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter bindDevice() func,the device num:{}", userAssociateDevice.getDeviceNum());
        }

        mapper.bindDevice(userAssociateDevice);
    }

    public void deleteByPhoneAndNum(Long userPhone, Long deviceNum)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter deleteByPhoneAndNum() func,the device num:{}",deviceNum);
        }

        UserAssociateDevice userAssociateDevice = new UserAssociateDevice();
        userAssociateDevice.setDeviceNum(deviceNum);
        userAssociateDevice.setUserPhone(userPhone);

        mapper.deleteByPhoneAndNum(userAssociateDevice);
    }

    public void deleteByDeviceNum(Long deviceNum) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter deleteByDeviceNum() func,the device num:{}",deviceNum);
        }
        mapper.deleteByDeviceNum(deviceNum);
    }

    public int queryByNumAndPhone(UserAssociateDevice request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter deleteByPhoneAndNum() func,the device num:{}",request.getDeviceNum());
        }

        return mapper.queryCountByNumAndPhone(request);
    }

    public List<QueryDeviceUserRes> queryDeviceUser(QueryDeviceUserReq request) {

        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryDeviceUser() func,the owner phone{}",request.getOwnerPhone());
        }
        List<QueryDeviceUserRes> queryDeviceUserRes = mapper.queryDeviceUser(request);

        return queryDeviceUserRes;
    }

    public int queryDeviceUserCount(QueryDeviceUserReq request) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryDeviceUserCount() func,the owner phone{}",request.getOwnerPhone());
        }
        return mapper.queryDeviceUserCount(request);
    }

    public void updateDeviceUser(UserAssociateDevice request) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter updateDeviceUser() func,the device num:{}",request.getDeviceNum());
        }
        mapper.updateDeviceUser(request);
    }

}
