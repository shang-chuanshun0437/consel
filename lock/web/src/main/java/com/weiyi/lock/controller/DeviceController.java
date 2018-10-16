package com.weiyi.lock.controller;

import com.weiyi.lock.common.Result;
import com.weiyi.lock.common.constant.ErrorCode;
import com.weiyi.lock.common.constant.PermissionCode;
import com.weiyi.lock.common.utils.TimeUtil;
import com.weiyi.lock.interceptor.SecurityAnnotation;
import com.weiyi.lock.request.AddDeviceRequest;
import com.weiyi.lock.request.UpdateDeviceRequest;
import com.weiyi.lock.response.AddDeviceResponse;
import com.weiyi.lock.response.UpdateDeviceResponse;
import com.weiyi.lock.service.api.DeviceService;
import com.weiyi.lock.service.domain.DeviceDTO;
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
    @SecurityAnnotation(value = {PermissionCode.DEVICE})
    public AddDeviceResponse addDevice(@RequestBody AddDeviceRequest request)
    {
        AddDeviceResponse response = new AddDeviceResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter addDevice() func ,the request:{}",request);
        }

        //首先判断设备是否已存在
        {
            int count = deviceService.queryCountByDeviceNum(request.getDeviceNum());
            if (count >= 1)
            {
                result.setRetCode(ErrorCode.DEVICE_EXIST);
                result.setRetMsg("device already exist.");
                return response;
            }
        }

        DeviceDTO device = new DeviceDTO();
        device.setDeviceName(request.getDeviceName());
        device.setDeviceNum(request.getDeviceNum());
        device.setBluetoothMac(request.getBluetoothMac());
        device.setVersion(request.getVersion());
        device.setCreateTime(TimeUtil.getCurrentTime());
        device.setUpdateTime(device.getCreateTime());

        deviceService.addDevice(device);

        return response;
    }

    @RequestMapping(value = "/updateDevice",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation(value = {PermissionCode.DEVICE})
    public UpdateDeviceResponse updateDevice(@RequestBody UpdateDeviceRequest request)
    {
        UpdateDeviceResponse response = new UpdateDeviceResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter updateDevice() func ,the device num:{}", request.getDeviceNum());
        }

        //去查询设备，如果设备下已存在管理员，则不允许后台人员进行修改
        DeviceDTO deviceDb = deviceService.queryDeviceByDeviceNum(request.getDeviceNum());
        if(deviceDb == null || deviceDb.getOwnerPhone() != null)
        {
            result.setRetCode(ErrorCode.OWNER_USER_EXIST);
            result.setRetMsg("the device has owner user.");
            return response;
        }
        DeviceDTO deviceDTO = new DeviceDTO();

        deviceDTO.setDeviceNum(request.getDeviceNum());
        deviceDTO.setBluetoothMac(request.getBluetoothMac());
        deviceDTO.setVersion(request.getVersion());
        deviceDTO.setUpdateTime(TimeUtil.getCurrentTime());

        deviceService.updateDevice(deviceDTO);
        return response;
    }
}
