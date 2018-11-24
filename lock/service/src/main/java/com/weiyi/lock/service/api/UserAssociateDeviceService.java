package com.weiyi.lock.service.api;

import com.weiyi.lock.dao.entity.UserAssociateDevice;
import com.weiyi.lock.dao.request.QueryDeviceUserReq;
import com.weiyi.lock.dao.response.QueryDeviceUserRes;

import java.util.List;

public interface UserAssociateDeviceService
{
    void bindDevice(UserAssociateDevice userAssociateDevice);

    void deleteByPhoneAndNum(Long userPhone, Long deviceNum);

    void deleteByDeviceNum(Long deviceNum);

    int queryByNumAndPhone(UserAssociateDevice request);

    List<QueryDeviceUserRes> queryDeviceUser(QueryDeviceUserReq request);

    int queryDeviceUserCount(QueryDeviceUserReq request);

    void updateDeviceUser(UserAssociateDevice request);
}
