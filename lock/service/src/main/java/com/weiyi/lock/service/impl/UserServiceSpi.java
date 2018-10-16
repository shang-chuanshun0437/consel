package com.weiyi.lock.service.impl;

import com.weiyi.lock.common.redis.RedisClient;
import com.weiyi.lock.common.utils.CopyProperties;
import com.weiyi.lock.dao.entity.Device;
import com.weiyi.lock.dao.entity.User;
import com.weiyi.lock.dao.mapper.DeviceMapper;
import com.weiyi.lock.dao.mapper.UserMapper;
import com.weiyi.lock.service.api.DeviceService;
import com.weiyi.lock.service.api.UserService;
import com.weiyi.lock.service.domain.UserDTO;
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

    public void addUser(UserDTO userDTO)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter insert() func,phoneNum:{}",userDTO.getUserPhone());
        }

        User user = new User();

        CopyProperties.copy(user,userDTO);
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

    public UserDTO queryUserByPhone(Long userPhone)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryUserByPhone() func,phoneNum:{}",userPhone);
        }

        User user = userMapper.queryUserByPhone(userPhone);

        UserDTO userDTO = new UserDTO();
        CopyProperties.copy(userDTO,user);

        return userDTO;
    }

    public void updateUser(UserDTO userDTO)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter updateUser() func,phoneNum:{}",userDTO.getUserPhone());
        }
        User user = new User();
        CopyProperties.copy(user,userDTO);
        userMapper.updateUser(user);
    }
}
