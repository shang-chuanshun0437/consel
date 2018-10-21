package com.weiyi.lock.service.impl;

import com.weiyi.lock.common.utils.CopyProperties;
import com.weiyi.lock.dao.domain.DeviceListDomain;
import com.weiyi.lock.dao.entity.Device;
import com.weiyi.lock.service.api.DeviceService;
import com.weiyi.lock.dao.mapper.DeviceMapper;
import com.weiyi.lock.service.dto.DeviceDTO;
import com.weiyi.lock.service.dto.DeviceListDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    /*
    *过滤出符合条件的设备列表
    */
    public List<DeviceDTO> queryDeviceList(DeviceListDTO deviceListDTO)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryDeviceByDeviceNum() func,deviceNum:{}",deviceListDTO.getDeviceNum());
        }

        DeviceListDomain deviceListDomain = new DeviceListDomain();

        CopyProperties.copy(deviceListDomain,deviceListDTO);
        List<Device> devices = deviceMapper.queryDeviceList(deviceListDomain);

        //转换数据
        List<DeviceDTO> deviceDTOS = new ArrayList<DeviceDTO>();
        if (devices != null && devices.size() > 0)
        {
            for (Device device : devices)
            {
                DeviceDTO deviceDTO = new DeviceDTO();
                CopyProperties.copy(deviceDTO,device);
                deviceDTOS.add(deviceDTO);
            }
            return deviceDTOS;
        }

        return null;
    }

    public List<DeviceDTO> userQueryDeviceList(Long userPhone)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter userQueryDeviceList() func,user phone:{}",userPhone);
        }

        Device device = new Device();

        deviceMapper.updateDevice(device);
        return null;
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

    public void deleteDevice(Long deviceNum)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter deleteDevice() func,deviceNum:{}",deviceNum);
        }

        deviceMapper.deleteDevice(deviceNum);
    }

    public void updateOwner(Long deviceNum,Long userPhone)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter updateOwner() func,deviceNum:{}",deviceNum);
        }

        Device device = new Device();
        device.setDeviceNum(deviceNum);
        device.setOwnerPhone(userPhone);

        deviceMapper.updateOwner(device);
    }
}
