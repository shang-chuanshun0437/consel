package com.weiyi.lock.controller;

import com.weiyi.lock.common.Result;
import com.weiyi.lock.common.constant.Constant;
import com.weiyi.lock.common.constant.ErrorCode;
import com.weiyi.lock.common.exception.LockAssert;
import com.weiyi.lock.common.exception.LockException;
import com.weiyi.lock.common.redis.RedisClient;
import com.weiyi.lock.dao.entity.User;
import com.weiyi.lock.interceptor.SecurityAnnotation;
import com.weiyi.lock.request.LoginRequest;
import com.weiyi.lock.request.LogoutRequest;
import com.weiyi.lock.response.LoginResponse;
import com.weiyi.lock.response.LogoutResponse;
import com.weiyi.lock.service.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class LoginController
{
    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RedisClient redisClient;

    /*
    *使用手机号和密码登录
    */
    @RequestMapping(value = "/login",method = {RequestMethod.POST})
    @ResponseBody
    public LoginResponse login(@RequestBody LoginRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter login() func,phone num:{}",request.getUserPhone());
        }

        LoginResponse response = new LoginResponse();
        Result result = new Result();
        response.setResult(result);

        //根据phone获取数据库用户信息
        User dbUser = userService.queryUserByPhone(request.getUserPhone());

        try
        {
            check(request,dbUser);
        }
        catch (LockException e)
        {
            result.setRetCode(e.getCode());
            result.setRetMsg(e.getMsg());
            return response;
        }
        //若数据库中token为空，则生成新的token
        String token = dbUser.getUserToken();
        if(StringUtils.isEmpty(token))
        {
            token = UUID.randomUUID().toString();
            dbUser.setUserToken(token);
            userService.updateUser(dbUser);
        }
        //将token存入redis
        redisClient.hset(request.getUserPhone() + "",Constant.User.TOKEN,token);

        response.setToken(token);
        response.setUserPhone(request.getUserPhone());
        return response;
    }

    /*
     *用户登出
     */
    @RequestMapping(value = "/logout",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public LogoutResponse logout(@RequestBody @Valid LogoutRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter logout() func,phone num:{}", request.getUserPhone());
        }

        LogoutResponse response = new LogoutResponse();
        Result result = new Result();
        response.setResult(result);

        //删除redis中的token
        redisClient.hdel(request.getUserPhone() + "",Constant.User.TOKEN);

        //删除数据库中的token
        User dbUser = userService.queryUserByPhone(request.getUserPhone());
        dbUser.setUserToken(null);
        userService.updateUser(dbUser);
        return response;
    }

    private void check(LoginRequest request,User dbUser)
    {
        //校验用户和密码
        String password = request.getPassword();
        LockAssert.isTrue(dbUser != null,ErrorCode.USER_NOT_EXIST,"user not exist.");
        LockAssert.isTrue(password.equals(dbUser.getUserPassword()),ErrorCode.PASSWORD_ERROR,"password is error.");
    }
}
