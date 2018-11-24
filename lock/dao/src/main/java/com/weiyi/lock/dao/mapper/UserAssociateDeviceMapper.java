package com.weiyi.lock.dao.mapper;

import com.weiyi.lock.dao.entity.UserAssociateDevice;
import com.weiyi.lock.dao.request.QueryDeviceUserReq;
import com.weiyi.lock.dao.response.QueryDeviceUserRes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserAssociateDeviceMapper
{
    void bindDevice(UserAssociateDevice userAssociateDevice);

    void deleteByPhoneAndNum(UserAssociateDevice userAssociateDevice);

    void deleteByDeviceNum(Long deviceNum);

    int queryCountByNumAndPhone(UserAssociateDevice userAssociateDevice);

    List<QueryDeviceUserRes> queryDeviceUser(QueryDeviceUserReq request);

    int queryDeviceUserCount(QueryDeviceUserReq request);

    void updateDeviceUser(UserAssociateDevice request);
}
