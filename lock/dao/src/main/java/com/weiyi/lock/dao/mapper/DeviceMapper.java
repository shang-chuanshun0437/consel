package com.weiyi.lock.dao.mapper;

import com.weiyi.lock.dao.request.QueryManageDeviceReq;
import com.weiyi.lock.dao.request.QueryUnManageDeviceReq;
import com.weiyi.lock.dao.response.QueryUnManageDeviceRes;
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

    List<Device> queryManageDevice(QueryManageDeviceReq queryManageDeviceReq);

    int queryManageDeviceCount(QueryManageDeviceReq queryManageDeviceReq);

    int queryUnManageDeviceCount(QueryUnManageDeviceReq request);

    List<QueryUnManageDeviceRes> queryUnManageDevice(QueryUnManageDeviceReq request);

    void updateOwner(Device device);

}
