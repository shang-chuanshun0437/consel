package com.weiyi.lock.service.api;

import com.weiyi.lock.dao.entity.DeviceOut;
import com.weiyi.lock.dao.request.QueryManageDeviceOutReq;
import com.weiyi.lock.dao.request.QueryUnManageDeviceOutReq;
import com.weiyi.lock.dao.response.QueryUnManageDeviceOutRes;
import com.weiyi.lock.service.request.BindDevice4UserReq;

import java.util.List;

public interface DeviceOutService
{
    void addDevice(DeviceOut deviceOut);

    int queryCountByDeviceNum(Long deviceNum);

    DeviceOut queryDeviceByDeviceNum(Long deviceNum);

    List<DeviceOut> queryManageDevice(QueryManageDeviceOutReq queryManageDeviceOutReq);

    int queryManageDeviceCount(QueryManageDeviceOutReq queryManageDeviceOutReq);

    int queryUnManageDeviceCount(QueryUnManageDeviceOutReq request);

    List<QueryUnManageDeviceOutRes> queryUnManageDevice(QueryUnManageDeviceOutReq request);

    void updateDevice(DeviceOut deviceOut);

    void bindDevice(Long deviceNum,String deviceName,Long userPhone);

    void bindDevice4User(BindDevice4UserReq request);

    void unBindDevice(Long deviceNum,Long userPhone);

    void unBindDevice4User(Long deviceNum,Long unBindUser,Long ownerPhone);
}
