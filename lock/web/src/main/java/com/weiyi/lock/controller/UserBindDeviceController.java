package com.weiyi.lock.controller;

import com.weiyi.lock.common.Result;
import com.weiyi.lock.common.constant.ErrorCode;
import com.weiyi.lock.common.utils.TimeUtil;
import com.weiyi.lock.interceptor.SecurityAnnotation;
import com.weiyi.lock.request.*;
import com.weiyi.lock.response.*;
import com.weiyi.lock.service.api.DeviceService;
import com.weiyi.lock.service.api.UserAssociateDeviceService;
import com.weiyi.lock.service.api.UserService;
import com.weiyi.lock.service.request.AddDevice4UserRequest;
import com.weiyi.lock.service.request.GetUserDeviceByNumReq;
import com.weiyi.lock.service.response.GetDeviceInfoResponse;
import com.weiyi.lock.service.response.GetUserAssociateDeviceInfoRes;
import com.weiyi.lock.service.response.GetUserDeviceByNumRes;
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
* 用户绑定设备控制器
*/
@Controller
@RequestMapping("/user/device")
public class UserBindDeviceController
{
    private Logger logger = LoggerFactory.getLogger(UserBindDeviceController.class);

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private UserAssociateDeviceService userAssociateDeviceService;

    @Autowired
    private UserService userService;
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

        //根据设备ID，获取设备
        GetDeviceInfoResponse dbDevice = deviceService.queryDeviceByDeviceNum(request.getDeviceNum());

        //判断设备是否存在，以及是否存在管理员
        if (dbDevice.getDeviceNum() == null || dbDevice.getOwnerPhone() != null)
        {
            result.setRetCode(ErrorCode.BIND_DEVICE_ERROR);
            result.setRetMsg("please input correct device num.");
            return response;
        }

        dbDevice.setOwnerPhone(request.getUserPhone());
        dbDevice.setDeviceName(request.getDeviceName());
        dbDevice.setUpdateTime(TimeUtil.getCurrentTime());
        dbDevice.setUserCount(dbDevice.getUserCount() + 1);
        //更新设备表
        deviceService.updateDevice(dbDevice);

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
    @Transactional
    public BindDevice4UserResponse bindDevice4User(@RequestBody BindDevice4UserRequest request)
    {
        BindDevice4UserResponse response = new BindDevice4UserResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter bindDevice4User() func ,the device num:{}", request.getDeviceNum());
        }

        //校验待绑定的用户是否注册
        int count = userService.countByPhone(request.getNeedBindPhone());

        if (count <= 0)
        {
            result.setRetCode(ErrorCode.USER_NOT_EXIST);
            result.setRetMsg("the need bind user not register.");
            return response;
        }

        //校验用户是否该设备的管理员
        GetDeviceInfoResponse deviceDb = deviceService.queryDeviceByDeviceNum(request.getDeviceNum());
        if(deviceDb == null || deviceDb.getOwnerPhone() == null || !deviceDb.getOwnerPhone().equals(request.getUserPhone()))
        {
            result.setRetCode(ErrorCode.BIND_DEVICE_ERROR);
            result.setRetMsg("the device num is error.");
            return response;
        }

        //如果管理员绑定自己
        if(request.getUserPhone().equals(request.getNeedBindPhone()))
        {
            return response;
        }

        //查询该用户是否已经绑定
        GetUserDeviceByNumReq getUserDeviceByNumReq = new GetUserDeviceByNumReq();
        getUserDeviceByNumReq.setDeviceNum(request.getDeviceNum());
        getUserDeviceByNumReq.setUserPhone(request.getNeedBindPhone());

        int total = userAssociateDeviceService.queryByNumAndPhone(getUserDeviceByNumReq);
        if (total <= 0)
        {
            //用户未绑定该设备
            //更新device表
            deviceDb.setUserCount(deviceDb.getUserCount() + 1);
            deviceService.updateDevice(deviceDb);
        }

        //更新用户设备关联表
        AddDevice4UserRequest addDevice4UserRequest = new AddDevice4UserRequest();

        addDevice4UserRequest.setUserPhone(request.getNeedBindPhone());
        addDevice4UserRequest.setDeviceNum(request.getDeviceNum());
        addDevice4UserRequest.setCreateTime(TimeUtil.getCurrentTime());
        addDevice4UserRequest.setUpdateTime(addDevice4UserRequest.getCreateTime());
        addDevice4UserRequest.setExpiryDate(request.getExpiryDate());

        userAssociateDeviceService.bindDevice(addDevice4UserRequest);
        return response;
    }
}
