package com.weiyi.lock.dao.mapper;

import com.weiyi.lock.dao.entity.DeviceIn;
import com.weiyi.lock.dao.request.QueryDeviceInListReq;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DeviceInMapper
{
    void addDevice(DeviceIn deviceIn);

    void deleteDevice(Long deviceNum);

    void updateDevice(DeviceIn deviceIn);

    int queryCountByDeviceNum(Long deviceNum);

    int queryDeviceInListCount(QueryDeviceInListReq queryDeviceInListReq);

    List<DeviceIn> queryDeviceInList(QueryDeviceInListReq queryDeviceInListReq);

}
