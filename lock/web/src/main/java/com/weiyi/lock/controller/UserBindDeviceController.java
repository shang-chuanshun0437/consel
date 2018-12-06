package com.weiyi.lock.controller;

import com.weiyi.lock.common.Result;
import com.weiyi.lock.common.exception.LockException;
import com.weiyi.lock.common.utils.CopyProperties;
import com.weiyi.lock.interceptor.SecurityAnnotation;
import com.weiyi.lock.request.*;
import com.weiyi.lock.response.*;
import com.weiyi.lock.service.api.DeviceOutService;
import com.weiyi.lock.service.request.BindDevice4UserReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.Lock;

/*
* 用户绑定设备控制器
*/
@Controller
@RequestMapping("/user/device")
public class UserBindDeviceController
{
    private Logger logger = LoggerFactory.getLogger(UserBindDeviceController.class);

    @Autowired
    private DeviceOutService deviceOutService;

    /*
    *绑定设备
    * 如果设备已存在管理员，则绑定失败
    */
    @RequestMapping(value = "/bindDevice",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public BindDeviceResponse bindDevice(@RequestBody BindDeviceRequest request)
    {
        BindDeviceResponse response = new BindDeviceResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter bindDevice() func ,the user phone:{}",request.getUserPhone());
        }

        try {
            deviceOutService.bindDevice(request.getDeviceNum(),request.getDeviceName(),request.getUserPhone());
        }catch (LockException e)
        {
            result.setRetCode(e.getCode());
            result.setRetMsg(e.getMsg());
        }

        return response;
    }

    /*
     * 设备管理员为其他用户绑定设备：
     * 1、必须是该设备的管理员
     * 2、待绑定的用户必须注册
     */
    @RequestMapping(value = "/bindDevice4User",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public BindDevice4UserResponse bindDevice4User(@RequestBody BindDevice4UserRequest request)
    {
        BindDevice4UserResponse response = new BindDevice4UserResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter bindDevice4User() func ,the device num:{}", request.getDeviceNum());
        }

        BindDevice4UserReq bindDevice4UserReq = new BindDevice4UserReq();
        CopyProperties.copy(bindDevice4UserReq,request);

        try {
            deviceOutService.bindDevice4User(bindDevice4UserReq);
        }catch (LockException e){
            result.setRetCode(e.getCode());
            result.setRetMsg(e.getMsg());
        }

        return response;
    }
}
