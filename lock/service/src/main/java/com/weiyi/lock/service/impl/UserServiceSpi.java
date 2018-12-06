package com.weiyi.lock.service.impl;

import com.weiyi.lock.common.constant.Constant;
import com.weiyi.lock.common.constant.ErrorCode;
import com.weiyi.lock.common.exception.LockAssert;
import com.weiyi.lock.common.redis.RedisClient;
import com.weiyi.lock.common.utils.TimeUtil;
import com.weiyi.lock.dao.entity.User;
import com.weiyi.lock.dao.mapper.UserAssociateDeviceMapper;
import com.weiyi.lock.dao.mapper.UserMapper;
import com.weiyi.lock.dao.request.ChangeDeviceUserRequest;
import com.weiyi.lock.dao.request.ChangeOwnerRequest;
import com.weiyi.lock.dao.request.QueryAllUserListReq;
import com.weiyi.lock.service.api.DeviceOutService;
import com.weiyi.lock.service.api.UserService;
import com.weiyi.lock.service.request.ChangeAccountReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceSpi implements UserService
{
    private Logger logger = LoggerFactory.getLogger(UserServiceSpi.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserAssociateDeviceMapper userAssociateDeviceMapper;

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private DeviceOutService deviceOutService;

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

    @Transactional
    public void destroyUser(Long userPhone, String password) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryAllUser() func,phoneNum:{}",userPhone);
        }

        User dbUser = queryUserByPhone(userPhone);

        LockAssert.isTrue(dbUser != null && dbUser.getUserPhone() != null,ErrorCode.USER_NOT_EXIST,"user not exist");
        LockAssert.isTrue(dbUser.getUserPassword().equals(password),ErrorCode.PARAMETER_ERROR,"password is error");

        //删除users表中的信息
        userMapper.deleteByPhoneNum(userPhone);

        //删除user_device表中的信息
        userAssociateDeviceMapper.deleteByPhone(userPhone);
    }

    @Transactional
    public User changeAccount(ChangeAccountReq request) {
        //校验新手机号是否注册
        int count = countByPhone(request.getNewPhone());
        LockAssert.isTrue(count <= 0,ErrorCode.USER_EXIST,"user exist");

        //校验原用户的密码是否正确
        User dbUser = queryUserByPhone(request.getUserPhone());
        LockAssert.isTrue(dbUser != null && dbUser.getUserPhone() != null,ErrorCode.USER_NOT_EXIST,"user not exist");
        LockAssert.isTrue(dbUser.getUserPassword().equals(request.getPassword()),ErrorCode.PASSWORD_ERROR,"password error.");

        //更新users表
        String token = UUID.randomUUID().toString();

        dbUser.setUserToken(token);
        dbUser.setUserPassword(request.getNewPassword());
        dbUser.setUserPhone(request.getNewPhone());

        userMapper.deleteByPhoneNum(request.getUserPhone());
        userMapper.addUser(dbUser);

        //更新device_out表
        ChangeOwnerRequest changeOwnerRequest = new ChangeOwnerRequest();

        changeOwnerRequest.setOwnerPhone(request.getUserPhone());
        changeOwnerRequest.setNewOwnerPhone(request.getNewPhone());

        deviceOutService.changeOwner(changeOwnerRequest);

        //更新user_device表
        ChangeDeviceUserRequest changeDeviceUserRequest = new ChangeDeviceUserRequest();

        changeDeviceUserRequest.setUserPhone(request.getUserPhone());
        changeDeviceUserRequest.setNewUserPhone(request.getNewPhone());
        changeDeviceUserRequest.setUpdateTime(TimeUtil.getCurrentTime());

        userAssociateDeviceMapper.changeDeviceUser(changeDeviceUserRequest);

        //更新redis
        redisClient.hset(request.getNewPhone() + "",Constant.User.TOKEN,token);

        return dbUser;
    }
}
