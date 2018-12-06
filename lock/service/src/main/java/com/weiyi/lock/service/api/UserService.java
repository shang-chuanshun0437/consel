package com.weiyi.lock.service.api;

import com.weiyi.lock.dao.entity.User;
import com.weiyi.lock.dao.request.QueryAllUserListReq;
import com.weiyi.lock.service.request.ChangeAccountReq;

import java.util.List;

public interface UserService
{
    void addUser(User user);

    int countByPhone(Long userPhone);

    User queryUserByPhone(Long userPhone);

    void updateUser(User user);

    void updatePassword(Long userPhone,String newPassword,String token);

    int queryAllUserCount(QueryAllUserListReq request);

    List<User> queryAllUser(QueryAllUserListReq request);

    void destroyUser(Long userPhone,String password);

    User changeAccount(ChangeAccountReq request);
}
