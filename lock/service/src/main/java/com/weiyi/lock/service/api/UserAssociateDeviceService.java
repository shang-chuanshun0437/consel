package com.weiyi.lock.service.api;

import com.weiyi.lock.service.request.AddDevice4UserRequest;
import com.weiyi.lock.service.response.GetUserDeviceByNumRes;

public interface UserAssociateDeviceService
{
    void bindDevice(AddDevice4UserRequest addDevice4UserRequest);

    void deleteByPhoneAndNum(Long userPhone, Long deviceNum);

    GetUserDeviceByNumRes queryByNumAndPhone(Long deviceNum, Long userPhone);
}
