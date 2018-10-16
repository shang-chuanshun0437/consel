package com.weiyi.lock.service.api;

import com.weiyi.lock.dao.entity.User;
import com.weiyi.lock.service.domain.UserDTO;

public interface UserService
{
    void addUser(UserDTO user);

    int countByPhone(Long userPhone);

    UserDTO queryUserByPhone(Long userPhone);

    void updateUser(UserDTO user);
}
