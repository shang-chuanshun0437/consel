package com.weiyi.lock.controller;

import com.weiyi.lock.common.Result;
import com.weiyi.lock.common.exception.LockException;
import com.weiyi.lock.interceptor.SecurityAnnotation;
import com.weiyi.lock.request.UnbindDevice4UserRequest;
import com.weiyi.lock.request.UnbindDeviceRequest;
import com.weiyi.lock.response.UnbindDevice4UserResponse;
import com.weiyi.lock.response.UnbindDeviceResponse;
import com.weiyi.lock.service.api.DeviceOutService;
import com.weiyi.lock.service.api.UserAssociateDeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/*
* 用户解绑设备控制器
*/
@Controller
@RequestMapping("/user/device")
public class UserUnbindDeviceController
{
    private Logger logger = LoggerFactory.getLogger(UserUnbindDeviceController.class);

    @Autowired
    private DeviceOutService deviceOutService;

    /*
    *自己解绑自己的设备:
    * 如果自己是设备的管理员且该设备下还有其他用户，则解绑失败
    */
    @RequestMapping(value = "/unbindDevice",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    @Transactional
    public UnbindDeviceResponse unbindDevice(@RequestBody UnbindDeviceRequest request)
    {
        UnbindDeviceResponse response = new UnbindDeviceResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter unbindDevice() func ,the user phone:{}",request.getUserPhone());
        }

        try {
            deviceOutService.unBindDevice(request.getDeviceNum(),request.getUserPhone());
        }catch (LockException e){
            result.setRetMsg(e.getMsg());
            result.setRetCode(e.getCode());
        }

        return response;
    }

    /*
     * 设备管理员删除设备下的其他用户：
     * 1、必须是该设备的管理员
     */
    @RequestMapping(value = "/unbindDevice4User",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    @Transactional
    public UnbindDevice4UserResponse unbindDevice4User(@RequestBody UnbindDevice4UserRequest request)
    {
        UnbindDevice4UserResponse response = new UnbindDevice4UserResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter unbindDevice4User() func ,the device num:{}", request.getDeviceNum());
        }

        deviceOutService.unBindDevice4User(request.getDeviceNum(),request.getNeedUnBindPhone(),request.getUserPhone());

        return response;
    }
}
