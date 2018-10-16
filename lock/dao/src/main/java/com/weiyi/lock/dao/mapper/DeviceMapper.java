package com.weiyi.lock.dao.mapper;

import com.weiyi.lock.dao.entity.Device;
import org.springframework.stereotype.Service;

@Service
public interface DeviceMapper
{
    void addDevice(Device device);

    int queryCountByDeviceNum(Long deviceNum);

    Device queryDeviceByDeviceNum(Long deviceNum);

    void updateDevice(Device device);
}
