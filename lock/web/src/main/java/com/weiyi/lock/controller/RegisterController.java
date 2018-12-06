package com.weiyi.lock.controller;

import com.weiyi.lock.common.Result;
import com.weiyi.lock.common.constant.Constant;
import com.weiyi.lock.common.constant.ErrorCode;
import com.weiyi.lock.common.redis.RedisClient;
import com.weiyi.lock.common.utils.TimeUtil;
import com.weiyi.lock.dao.entity.User;
import com.weiyi.lock.dao.entity.VerifyCode;
import com.weiyi.lock.request.CheckIfRegisterRequest;
import com.weiyi.lock.request.CheckVerifyCodeRequest;
import com.weiyi.lock.request.RegisterRequest;
import com.weiyi.lock.request.VerificationCodeRequest;
import com.weiyi.lock.response.CheckIfRegisterResponse;
import com.weiyi.lock.response.CheckVerifyCodeResponse;
import com.weiyi.lock.response.RegisterResponse;
import com.weiyi.lock.response.VerificationCodeResponse;
import com.weiyi.lock.service.api.UserService;
import com.weiyi.lock.service.api.VerifyCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@Controller
@RequestMapping("/user")
public class RegisterController
{
    private Logger logger = LoggerFactory.getLogger(DeviceOutController.class);

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private UserService userService;

    @Autowired
    private VerifyCodeService verifyCodeService;
    /*
     * 获取验证码
     */
    @RequestMapping(value = "/getVerificationCode",method = {RequestMethod.POST})
    @ResponseBody
    public VerificationCodeResponse getVerificationCode(@RequestBody VerificationCodeRequest request)
    {
        VerificationCodeResponse response = new VerificationCodeResponse();
        Result result = new Result();
        response.setResult(result);

        //模拟生成验证码
        //将验证码存入redis 存活期120秒
        Boolean redis = redisClient.set(request.getUserPhone() + Constant.User.VERIFY_CODE,123456,120);

        if(logger.isDebugEnabled()){
            logger.debug("inter getVerificationCode() func,save in redis flag is  :{},key is:{}",redis,request.getUserPhone() + Constant.User.VERIFY_CODE);
        }

        if(redis){
            //将短信记录存入数据库
            VerifyCode verifyCode = new VerifyCode();

            verifyCode.setUserPhone(request.getUserPhone());
            verifyCode.setContent("123456");
            verifyCode.setCreateTime(TimeUtil.getCurrentTime());

            verifyCodeService.addCode(verifyCode);
        }else{
            result.setRetCode(ErrorCode.VERIFY_ERROR);
            result.setRetMsg("get verify code error.");
        }

        return response;
    }

    /*
     * 用户注册
     */
    @RequestMapping(value = "/register",method = {RequestMethod.POST})
    @ResponseBody
    public RegisterResponse register(@RequestBody RegisterRequest request)
    {
        RegisterResponse response = new RegisterResponse();
        Result result = new Result();
        response.setResult(result);

        //校验 验证码 是否正确
        Object code = redisClient.get(request.getUserPhone() + Constant.User.VERIFY_CODE);
        if (code == null || request.getVerificationCode() != ((Integer)code).intValue())
        {
            result.setRetCode(ErrorCode.VERIFY_ERROR);
            result.setRetMsg("verify code is error.");
            return response;
        }

        //校验用户是否已经注册
        int count = userService.countByPhone(request.getUserPhone());
        if (count > 0)
        {
            result.setRetCode(ErrorCode.USER_EXIST);
            result.setRetMsg("user already existed.");
            return response;
        }

        //注册时生成token，存入数据库，为永久token，只有在修改密码和用户登出的时候才会重新生成
        String token = UUID.randomUUID().toString();

        //将用户存入数据库
        User user = new User();
        user.setUserPhone(request.getUserPhone());
        user.setUserName(request.getUserName());
        user.setUserPassword(request.getPassword());
        user.setUserToken(token);
        user.setCreateTime(TimeUtil.getCurrentTime());

        userService.addUser(user);

        //将token存入redis缓存
        redisClient.hset(request.getUserPhone() + "",Constant.User.TOKEN,token);

        return response;
    }

    /*
     * 校验用户是否注册
     */
    @RequestMapping(value = "/checkIfRegister",method = {RequestMethod.POST})
    @ResponseBody
    public CheckIfRegisterResponse checkIfRegister(@RequestBody CheckIfRegisterRequest request)
    {
        CheckIfRegisterResponse response = new CheckIfRegisterResponse();
        Result result = new Result();
        response.setResult(result);

        //校验用户是否已经注册
        int count = userService.countByPhone(request.getUserPhone());
        if (count > 0)
        {
            result.setRetCode(ErrorCode.USER_EXIST);
            result.setRetMsg("user already existed.");
            return response;
        }
        return response;
    }

    /*
     * 校验验证码是否正确
     */
    @RequestMapping(value = "/checkVerifyCode",method = {RequestMethod.POST})
    @ResponseBody
    public CheckVerifyCodeResponse checkVerifyCode(@RequestBody CheckVerifyCodeRequest request)
    {
        CheckVerifyCodeResponse response = new CheckVerifyCodeResponse();
        Result result = new Result();
        response.setResult(result);

        //校验 验证码 是否正确
        Object code = redisClient.get(request.getUserPhone() + Constant.User.VERIFY_CODE);

        if(logger.isDebugEnabled()){
            logger.debug("inter checkVerifyCode() func,the verifyCode is :{}",((Integer)code).intValue());
        }

        if (code == null || request.getVerificationCode() != ((Integer)code).intValue())
        {
            result.setRetCode(ErrorCode.VERIFY_ERROR);
            result.setRetMsg("verify code is error.");
            return response;
        }
        return response;
    }
}
