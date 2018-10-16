package com.weiyi.lock.service.impl;

import com.weiyi.lock.common.utils.CopyProperties;
import com.weiyi.lock.dao.entity.Device;
import com.weiyi.lock.service.api.DeviceService;
import com.weiyi.lock.dao.mapper.DeviceMapper;
import com.weiyi.lock.service.domain.DeviceDTO;
import com.weiyi.lock.service.domain.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceServiceSpi implements DeviceService
{
    private Logger logger = LoggerFactory.getLogger(DeviceServiceSpi.class);

    @Autowired
    private DeviceMapper deviceMapper;

    public void addDevice(DeviceDTO deviceDTO)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter device insert() func,request:{}",deviceDTO);
        }

        Device device = new Device();
        CopyProperties.copy(device,deviceDTO);
        deviceMapper.addDevice(device);
    }

    public int queryCountByDeviceNum(Long deviceNum)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryCountByDeviceNum() func,deviceNum:{}",deviceNum);
        }
        return deviceMapper.queryCountByDeviceNum(deviceNum);
    }

    public DeviceDTO queryDeviceByDeviceNum(Long deviceNum)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryDeviceByDeviceNum() func,deviceNum:{}",deviceNum);
        }
        DeviceDTO deviceDTO = new DeviceDTO();
        Device device = deviceMapper.queryDeviceByDeviceNum(deviceNum);

        CopyProperties.copy(deviceDTO,device);
        return deviceDTO;
    }

    public void updateDevice(DeviceDTO deviceDTO)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryCountByDeviceNum() func,deviceNum:{}",deviceDTO.getDeviceNum());
        }

        Device device = new Device();

        CopyProperties.copy(device,deviceDTO);

        deviceMapper.updateDevice(device);
    }
}
