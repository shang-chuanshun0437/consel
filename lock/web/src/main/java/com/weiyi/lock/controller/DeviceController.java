package com.weiyi.lock.controller;

import com.weiyi.lock.common.Result;
import com.weiyi.lock.common.constant.PermissionCode;
import com.weiyi.lock.dao.entity.Device;
import com.weiyi.lock.interceptor.SecurityAnnotation;
import com.weiyi.lock.request.AddDeviceRequest;
import com.weiyi.lock.response.AddDeviceResponse;
import com.weiyi.lock.service.api.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/device")
public class DeviceController
{
    private Logger logger = LoggerFactory.getLogger(DeviceController.class);
    @Autowired
    private DeviceService deviceService;

    @RequestMapping(value = "/addDevice",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation(value = {PermissionCode.ADMIN})
    public AddDeviceResponse addDevice(@RequestBody AddDeviceRequest request)
    {
        AddDeviceResponse response = new AddDeviceResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter addDevice() func ,the request:{}",request);
        }

        Device device = new Device();
        device.setDeviceName(request.getDeviceName());
        device.setDeviceNum(request.getDeviceNum());

        deviceService.insert(device);

        return response;
    }
}
