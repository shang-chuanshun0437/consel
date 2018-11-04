package com.weiyi.lock.service.api;

import com.weiyi.lock.service.response.GetUserInfoResponse;

public interface UserService
{
    void addUser(GetUserInfoResponse user);

    int countByPhone(Long userPhone);

    GetUserInfoResponse queryUserByPhone(Long userPhone);

    void updateUser(GetUserInfoResponse user);

    void updatePassword(Long userPhone,String newPassword,String token);
}
