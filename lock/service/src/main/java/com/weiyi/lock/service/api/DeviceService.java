package com.weiyi.lock.service.api;

import com.weiyi.lock.dao.entity.Device;
import com.weiyi.lock.service.request.GetUnManageDeviceRequest;
import com.weiyi.lock.service.response.GetDeviceInfoResponse;
import com.weiyi.lock.service.request.GetManageDeviceRequest;
import com.weiyi.lock.service.response.GetUnManageDeviceRes;

import java.util.List;

public interface DeviceService
{
    void addDevice(Device device);

    int queryCountByDeviceNum(Long deviceNum);

    GetDeviceInfoResponse queryDeviceByDeviceNum(Long deviceNum);

    List<GetDeviceInfoResponse> queryManageDevice(GetManageDeviceRequest getManageDeviceRequest);

    int queryManageDeviceCount(GetManageDeviceRequest getManageDeviceRequest);

    int queryUnManageDeviceCount(GetUnManageDeviceRequest request);

    List<GetUnManageDeviceRes> queryUnManageDevice(GetUnManageDeviceRequest request);

    void updateDevice(Device device);

    void deleteDevice(Long deviceNum);

    void updateOwner(Long deviceNum,Long userPhone);
}
