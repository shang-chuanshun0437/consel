package com.weiyi.lock.service.impl;

import com.weiyi.lock.dao.entity.User;
import com.weiyi.lock.dao.mapper.UserMapper;
import com.weiyi.lock.dao.request.QueryAllUserListReq;
import com.weiyi.lock.service.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceSpi implements UserService
{
    private Logger logger = LoggerFactory.getLogger(UserServiceSpi.class);

    @Autowired
    private UserMapper userMapper;

    public void addUser(User user)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter insert() func,phoneNum:{}", user.getUserPhone());
        }
        userMapper.addUser(user);
    }

    public int countByPhone(Long userPhone)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter insert() func,phoneNum:{}",userPhone);
        }

        int count = userMapper.countByPhone(userPhone);

        return count;
    }

    public User queryUserByPhone(Long userPhone)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryUserByPhone() func,phoneNum:{}",userPhone);
        }

        User user = userMapper.queryUserByPhone(userPhone);

        return user;
    }

    public void updateUser(User user)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter updateUser() func,phoneNum:{}", user.getUserPhone());
        }
        userMapper.updateUser(user);
    }

    public void updatePassword(Long userPhone,String newPassword,String token)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter updatePassword() func,phoneNum:{}",userPhone);
        }
        User user = new User();

        user.setUserToken(token);
        user.setUserPhone(userPhone);
        user.setUserPassword(newPassword);

        userMapper.updateUser(user);
    }

    public int queryAllUserCount(QueryAllUserListReq request) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryAllUserCount() func,phoneNum:{}",request);
        }

        return userMapper.queryAllUserCount(request);
    }

    public List<User> queryAllUser(QueryAllUserListReq request) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryAllUser() func,phoneNum:{}",request);
        }

        return userMapper.queryAllUser(request);
    }
}
