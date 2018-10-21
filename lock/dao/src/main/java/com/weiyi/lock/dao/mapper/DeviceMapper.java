package com.weiyi.lock.dao.mapper;

import com.weiyi.lock.dao.domain.DeviceListDomain;
import com.weiyi.lock.dao.entity.Device;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DeviceMapper
{
    void addDevice(Device device);

    int queryCountByDeviceNum(Long deviceNum);

    Device queryDeviceByDeviceNum(Long deviceNum);

    void updateDevice(Device device);

    void deleteDevice(Long deviceNum);

    List<Device> queryDeviceList(DeviceListDomain deviceListDomain);

    List<Device> userQueryDeviceList(Long userPhone);

    void updateOwner(Device device);

}
