package com.weiyi.lock.dao.mapper;

import com.weiyi.lock.dao.entity.UserAssociateDevice;
import org.springframework.stereotype.Service;

@Service
public interface UserAssociateDeviceMapper
{
    void bindDevice(UserAssociateDevice userAssociateDevice);

    int queryDeviceCountByNum(Long deviceNum);

    void deleteByPhoneAndNum(UserAssociateDevice userAssociateDevice);
}
