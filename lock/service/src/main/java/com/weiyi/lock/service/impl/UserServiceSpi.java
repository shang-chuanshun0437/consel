package com.weiyi.lock.service.impl;

import com.weiyi.lock.common.redis.RedisClient;
import com.weiyi.lock.common.utils.CopyProperties;
import com.weiyi.lock.dao.entity.User;
import com.weiyi.lock.dao.mapper.UserMapper;
import com.weiyi.lock.service.api.UserService;
import com.weiyi.lock.service.response.GetUserInfoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceSpi implements UserService
{
    private Logger logger = LoggerFactory.getLogger(UserServiceSpi.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisClient redisClient;

    public void addUser(GetUserInfoResponse getUserInfoResponse)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter insert() func,phoneNum:{}", getUserInfoResponse.getUserPhone());
        }

        User user = new User();

        CopyProperties.copy(user, getUserInfoResponse);
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

    public GetUserInfoResponse queryUserByPhone(Long userPhone)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryUserByPhone() func,phoneNum:{}",userPhone);
        }

        User user = userMapper.queryUserByPhone(userPhone);

        GetUserInfoResponse getUserInfoResponse = new GetUserInfoResponse();
        CopyProperties.copy(getUserInfoResponse,user);

        return getUserInfoResponse;
    }

    public void updateUser(GetUserInfoResponse getUserInfoResponse)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter updateUser() func,phoneNum:{}", getUserInfoResponse.getUserPhone());
        }
        User user = new User();
        CopyProperties.copy(user, getUserInfoResponse);
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
}
