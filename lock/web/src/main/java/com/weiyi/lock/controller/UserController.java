package com.weiyi.lock.controller;

import com.weiyi.lock.common.Result;
import com.weiyi.lock.common.constant.Constant;
import com.weiyi.lock.common.constant.ErrorCode;
import com.weiyi.lock.common.exception.LockAssert;
import com.weiyi.lock.common.exception.LockException;
import com.weiyi.lock.common.redis.RedisClient;
import com.weiyi.lock.common.utils.CopyProperties;
import com.weiyi.lock.dao.entity.User;
import com.weiyi.lock.interceptor.SecurityAnnotation;
import com.weiyi.lock.request.*;
import com.weiyi.lock.response.*;
import com.weiyi.lock.service.api.UserService;
import com.weiyi.lock.service.request.ChangeAccountReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.UUID;

/*
* 用户修改操作控制器
*/
@Controller
@RequestMapping("/user")
public class UserController
{
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RedisClient redisClient;

    /*
     *查询用户的基本信息
     */
    @RequestMapping(value = "/query/userInfo",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public QueryUserInfoResponse queryUserInfo(@RequestBody QueryUserInfoRequest request)
    {
        QueryUserInfoResponse response = new QueryUserInfoResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryUserInfo() func ,the user phone:{}",request.getUserPhone());
        }

        User user = userService.queryUserByPhone(request.getUserPhone());

        response.setUser(user);

        return response;
    }

    /*
     *校验用户密码
     */
    @RequestMapping(value = "/query/checkPassword",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public CheckPasswordResponse checkPassword(@RequestBody CheckPasswordRequest request)
    {
        CheckPasswordResponse response = new CheckPasswordResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter checkPassword() func ,the user phone:{}",request.getUserPhone());
        }
        try {
            checkPassword(request.getUserPhone(),request.getPassword());
        }catch (LockException e){
            result.setRetCode(e.getCode());
            result.setRetMsg(e.getMsg());
        }

        return response;
    }

    /*
    *更新用户的基本信息
    */
    @RequestMapping(value = "/updateUser",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public UpdateUserResponse updateUser(@RequestBody UpdateUserRequest request)
    {
        UpdateUserResponse response = new UpdateUserResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter updateUser() func ,the user phone:{}",request.getUserPhone());
        }

        User user = new User();

        user.setUserPhone(request.getUserPhone());
        user.setUserName(request.getUserName());
        user.setUserEmail(request.getUserEmail());
        user.setUserAddress(request.getUserAddress());

        userService.updateUser(user);
        return response;
    }

    /*
     * 修改用户密码
     */
    @RequestMapping(value = "/modifyPassword",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public ModifyPasswordResponse modifyPassword(@RequestBody @Valid ModifyPasswordRequest request)
    {
        ModifyPasswordResponse response = new ModifyPasswordResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter modifyPassword() func ,the user is:{}", request.getUserPhone());
        }

        //根据手机号查询用户信息
        User dbUser = userService.queryUserByPhone(request.getUserPhone());
        if(dbUser == null || dbUser.getUserPassword() == null || !dbUser.getUserPassword().equals(request.getOldPassword()))
        {
            result.setRetCode(ErrorCode.USER_NOT_EXIST);
            result.setRetMsg("user phone or password error.");
            return response;
        }

        String token = UUID.randomUUID().toString();
        //将新密码和token存入数据库
        userService.updatePassword(request.getUserPhone(),request.getNewPassword(),token);

        //将token存入redis缓存
        redisClient.hset(request.getUserPhone() + "",Constant.User.TOKEN,token);

        response.setToken(token);

        return response;
    }

    /*
     * 用户忘记密码
     */
    @RequestMapping(value = "/forgetPassword",method = {RequestMethod.POST})
    @ResponseBody
    public ForgetPasswordResponse forgetPassword(@RequestBody ForgetPasswordRequest request)
    {
        ForgetPasswordResponse response = new ForgetPasswordResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter forgetPassword() func ,the user is:{}", request.getUserPhone());
        }

        //根据手机号查询用户信息
        User dbUser = userService.queryUserByPhone(request.getUserPhone());
        if(dbUser == null || dbUser.getUserPhone() == null )
        {
            result.setRetCode(ErrorCode.USER_NOT_EXIST);
            result.setRetMsg("user phone or password error.");
            return response;
        }

        //校验 验证码 是否正确
        Object code = redisClient.get(request.getUserPhone() + Constant.User.VERIFY_CODE);
        if (code == null || request.getVerificationCode() != ((Integer)code).intValue())
        {
            result.setRetCode(ErrorCode.VERIFY_ERROR);
            result.setRetMsg("verify code is error.");
            return response;
        }

        String token = UUID.randomUUID().toString();

        //将新密码和token存入数据库
        dbUser.setUserPassword(request.getPassword());
        dbUser.setUserToken(token);

        userService.updateUser(dbUser);

        //将token存入redis缓存
        redisClient.hset(request.getUserPhone() + "",Constant.User.TOKEN,token);

        response.setToken(token);

        return response;
    }

    /*
     * 用户注销账号
     */
    @RequestMapping(value = "/destroyUser",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public DestroyUserResponse destroyUser(@RequestBody DestroyUserRequest request)
    {
        DestroyUserResponse response = new DestroyUserResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter destroyUser() func ,the user is:{}", request.getUserPhone());
        }

        try{
            userService.destroyUser(request.getUserPhone(),request.getPassword());
        }catch (LockException e){
            result.setRetMsg(e.getMsg());
            result.setRetCode(e.getCode());
            return response;
        }

        return response;
    }

    /*
     * 更换账号
     */
    @RequestMapping(value = "/changeAccount",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public ChangeAccountResponse changeAccount(@RequestBody ChangeAccountRequest request)
    {
        ChangeAccountResponse response = new ChangeAccountResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter changeAccount() func ,the user is:{}", request.getUserPhone());
        }

        try{
            ChangeAccountReq changeAccountReq = new ChangeAccountReq();

            CopyProperties.copy(changeAccountReq,request);

            //校验验证码是否正确
            Object code = redisClient.get(request.getNewPhone() + Constant.User.VERIFY_CODE);
            LockAssert.isTrue(code != null && request.getVerificationCode() == ((Integer)code).intValue(),
                    ErrorCode.VERIFY_ERROR,"verify code is error.");

            User user = userService.changeAccount(changeAccountReq);
            response.setUser(user);
        }catch (LockException e){
            result.setRetMsg(e.getMsg());
            result.setRetCode(e.getCode());
        }
        return response;
    }

    private void checkPassword(Long userPhone,String password){
        User user = userService.queryUserByPhone(userPhone);
        if (user == null || user.getUserPhone() == null){
            throw new LockException(ErrorCode.USER_NOT_EXIST,"user not exist.");
        }

        if (!user.getUserPassword().equals(password)){
            throw new LockException(ErrorCode.PASSWORD_ERROR,"password error.");
        }
    }
}
