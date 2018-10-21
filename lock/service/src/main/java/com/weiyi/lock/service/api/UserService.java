package com.weiyi.lock.service.api;

import com.weiyi.lock.service.dto.UserDTO;

public interface UserService
{
    void addUser(UserDTO user);

    int countByPhone(Long userPhone);

    UserDTO queryUserByPhone(Long userPhone);

    void updateUser(UserDTO user);

    void updatePassword(Long userPhone,String newPassword,String token);
}
