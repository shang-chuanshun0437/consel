package com.weiyi.lock.service.api;

import com.weiyi.lock.dao.entity.User;

public interface UserService
{
    void insert(User user);

    int countByPhone(Long userPhone);
}
