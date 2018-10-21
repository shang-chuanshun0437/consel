package com.weiyi.lock.service.api;

import com.weiyi.lock.service.dto.UserAssociateDeviceDTO;

public interface UserAssociateDeviceService
{
    void bindDevice(UserAssociateDeviceDTO userAssociateDeviceDTO);

    void deleteByPhoneAndNum(Long userPhone, Long deviceNum);

    int queryDeviceCountByNum(Long deviceNum);
}
