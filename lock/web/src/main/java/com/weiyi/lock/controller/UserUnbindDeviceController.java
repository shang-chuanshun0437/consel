package com.weiyi.lock.controller;

import com.weiyi.lock.common.Result;
import com.weiyi.lock.common.constant.ErrorCode;
import com.weiyi.lock.common.utils.TimeUtil;
import com.weiyi.lock.interceptor.SecurityAnnotation;
import com.weiyi.lock.request.UnbindDevice4UserRequest;
import com.weiyi.lock.request.UnbindDeviceRequest;
import com.weiyi.lock.response.UnbindDevice4UserResponse;
import com.weiyi.lock.response.UnbindDeviceResponse;
import com.weiyi.lock.service.api.DeviceService;
import com.weiyi.lock.service.api.UserAssociateDeviceService;
import com.weiyi.lock.service.api.UserService;
import com.weiyi.lock.service.response.GetDeviceInfoResponse;
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
    private DeviceService deviceService;

    @Autowired
    private UserAssociateDeviceService userAssociateDeviceService;

    @Autowired
    private UserService userService;

    /*
    *自己解绑自己的设备:
    * 如果自己是设备的管理员且该设备下还有其他用户，则解绑失败
    */
    @RequestMapping(value = "/unbindDevice",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public UnbindDeviceResponse unbindDevice(@RequestBody UnbindDeviceRequest request)
    {
        UnbindDeviceResponse response = new UnbindDeviceResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter unbindDevice() func ,the user phone:{}",request.getUserPhone());
        }

        //根据设备ID，获取设备
        GetDeviceInfoResponse dbDevice = deviceService.queryDeviceByDeviceNum(request.getDeviceNum());

        //判断设备是否存在，以及是否存在管理员
        if (dbDevice.getDeviceNum() == null || dbDevice.getOwnerPhone() == null)
        {
            result.setRetCode(ErrorCode.UNBIND_DEVICE_ERROR);
            result.setRetMsg("please input correct device num.");
            return response;
        }

        //校验自己是否是管理员、设备下是否有其他用户
        if (dbDevice.getOwnerPhone().equals(request.getUserPhone()))
        {
            int count = dbDevice.getUserCount();
            if(count >= 2)
            {
                result.setRetCode(ErrorCode.OTHER_USERS_EXIST);
                result.setRetMsg("the device has other users.");
                return response;
            }
            //如果是管理员，则只需要将设备表中的owner_phone字段设置为null
            dbDevice.setUserCount(0);
            dbDevice.setOwnerPhone(null);
            dbDevice.setUpdateTime(TimeUtil.getCurrentTime());

            deviceService.updateDevice(dbDevice);
            return response;
        }

        //如果是非管理员则删除用户设备关联表的记录
        userAssociateDeviceService.deleteByPhoneAndNum(request.getUserPhone(),request.getDeviceNum());

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

        //校验用户是否该设备的管理员
        GetDeviceInfoResponse deviceDb = deviceService.queryDeviceByDeviceNum(request.getDeviceNum());
        if(deviceDb == null || deviceDb.getOwnerPhone() == null || !deviceDb.getOwnerPhone().equals(request.getUserPhone()))
        {
            result.setRetCode(ErrorCode.BIND_DEVICE_ERROR);
            result.setRetMsg("the device num is error.");
            return response;
        }

        //更新device表
        deviceDb.setUserCount(deviceDb.getUserCount() - 1);
        deviceService.updateDevice(deviceDb);

        //更新用户设备关联表
        userAssociateDeviceService.deleteByPhoneAndNum(request.getNeedUnBindPhone(),request.getDeviceNum());

        return response;
    }
}
