package com.weiyi.lock.service.api;

import com.weiyi.lock.dao.entity.DeviceIn;
import com.weiyi.lock.dao.request.QueryDeviceInListReq;

import java.util.List;

public interface DeviceInService
{
    void addDevice(DeviceIn deviceIn);

    void deleteDevice(Long deviceNum);

    void updateDevice(DeviceIn deviceIn);

    int queryCountByDeviceNum(Long deviceNum);

    List<DeviceIn> queryDeviceInList(QueryDeviceInListReq request);

    int queryDeviceInListCount(QueryDeviceInListReq request);

    void deviceInOutOf(QueryDeviceInListReq request);
}
