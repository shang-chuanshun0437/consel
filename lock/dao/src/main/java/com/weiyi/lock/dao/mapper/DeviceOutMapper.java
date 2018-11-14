package com.weiyi.lock.dao.mapper;

import com.weiyi.lock.dao.entity.DeviceOut;
import com.weiyi.lock.dao.request.QueryManageDeviceOutReq;
import com.weiyi.lock.dao.request.QueryUnManageDeviceOutReq;
import com.weiyi.lock.dao.response.QueryUnManageDeviceOutRes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DeviceOutMapper
{
    void addDevice(DeviceOut deviceOut);

    int queryCountByDeviceNum(Long deviceNum);

    DeviceOut queryDeviceByDeviceNum(Long deviceNum);

    void updateDevice(DeviceOut deviceOut);

    void deleteDevice(Long deviceNum);

    List<DeviceOut> queryManageDevice(QueryManageDeviceOutReq queryManageDeviceReq);

    int queryManageDeviceCount(QueryManageDeviceOutReq queryManageDeviceReq);

    int queryUnManageDeviceCount(QueryUnManageDeviceOutReq request);

    List<QueryUnManageDeviceOutRes> queryUnManageDevice(QueryUnManageDeviceOutReq request);

    void updateOwner(DeviceOut deviceOut);

}
