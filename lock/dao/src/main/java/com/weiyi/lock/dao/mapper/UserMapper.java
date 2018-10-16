package com.weiyi.lock.dao.mapper;

import com.weiyi.lock.dao.entity.Device;
import com.weiyi.lock.dao.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserMapper
{
    void insert(User user);

    void deleteByPhoneNum(Long userPhone);

    int countByPhone(Long userPhone);

    User queryUserByPhone(Long userPhone);

    void updateUser(User user);
}
