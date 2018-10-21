package com.weiyi.lock.service.impl;

import com.weiyi.lock.dao.entity.UserAssociateDevice;
import com.weiyi.lock.dao.mapper.UserAssociateDeviceMapper;
import com.weiyi.lock.service.api.UserAssociateDeviceService;
import com.weiyi.lock.service.dto.UserAssociateDeviceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAssociateDeviceSpi implements UserAssociateDeviceService
{
    private Logger logger = LoggerFactory.getLogger(UserAssociateDeviceSpi.class);

    @Autowired
    private UserAssociateDeviceMapper mapper;

    public void bindDevice(UserAssociateDeviceDTO userAssociateDeviceDTO)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter bindDevice() func,the device num:{}",userAssociateDeviceDTO.getDeviceNum());
        }

        mapper.bindDevice(userAssociateDeviceDTO);
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

    public int queryDeviceCountByNum(Long deviceNum)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryDeviceCountByNum() func,the device num:{}",deviceNum);
        }

        return mapper.queryDeviceCountByNum(deviceNum);
    }
}
