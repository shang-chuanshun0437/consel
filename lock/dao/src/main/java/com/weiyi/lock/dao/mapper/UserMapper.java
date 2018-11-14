package com.weiyi.lock.dao.mapper;

import com.weiyi.lock.dao.entity.User;
import com.weiyi.lock.dao.request.QueryAllUserListReq;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserMapper
{
    void addUser(User user);

    int countByPhone(Long userPhone);

    User queryUserByPhone(Long userPhone);

    void updateUser(User user);

    int queryAllUserCount(QueryAllUserListReq request);

    List<User> queryAllUser(QueryAllUserListReq request);
}
