package com.weiyi.lock.controller;

import com.weiyi.lock.common.Result;
import com.weiyi.lock.common.constant.ErrorCode;
import com.weiyi.lock.common.constant.PermissionCode;
import com.weiyi.lock.common.utils.TimeUtil;
import com.weiyi.lock.interceptor.SecurityAnnotation;
import com.weiyi.lock.request.*;
import com.weiyi.lock.response.*;
import com.weiyi.lock.service.api.DeviceService;
import com.weiyi.lock.service.dto.DeviceDTO;
import com.weiyi.lock.service.dto.DeviceListDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/*
* 后台管理人员，对设备的操作
*/
@Controller
@RequestMapping("/device")
public class DeviceController
{
    private Logger logger = LoggerFactory.getLogger(DeviceController.class);

    @Autowired
    private DeviceService deviceService;

    /*
    *添加设备，设备编号唯一
    * 如果设备已存在，则添加失败
    */
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

    /*
     * 修改设备
     * 如果该设备下有用户则禁止修改
     */
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

        //去查询设备，如果设备下已存在ownerPhone，则不允许后台人员进行修改
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

    /*
    *根据设备编号删除设备
    * 如果该设备下有用户，则不能删除
    */
    @RequestMapping(value = "/deleteDevice",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation(value = {PermissionCode.DEVICE})
    public DeleteDeviceResponse deleteDevice(@RequestBody DeleteDeviceRequest request)
    {
        DeleteDeviceResponse response = new DeleteDeviceResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter updateDevice() func ,the device num:{}", request.getDeviceNum());
        }

        //去查询设备，如果设备下已存在ownerPhone，则不允许后台人员删除设备
        DeviceDTO deviceDb = deviceService.queryDeviceByDeviceNum(request.getDeviceNum());
        if(deviceDb == null || deviceDb.getOwnerPhone() != null)
        {
            result.setRetCode(ErrorCode.OWNER_USER_EXIST);
            result.setRetMsg("the device has owner user.");
            return response;
        }

        //根据设备编号，删除设备
        deviceService.deleteDevice(request.getDeviceNum());

        return response;
    }

    /*
    *查询符合条件的设备列表,后台管理人员
    */
    @RequestMapping(value = "/queryDeviceList",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation(value = {PermissionCode.DEVICE})
    public QueryDeviceResponse queryDeviceList(@RequestBody QueryDeviceRequest request)
    {
        QueryDeviceResponse response = new QueryDeviceResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter deleteDevice() func ,the device num:{}", request.getDeviceNum());
        }

        //填充查询数据
        DeviceListDTO deviceListDTO = new DeviceListDTO();

        deviceListDTO.setDeviceNum(request.getDeviceNum());
        deviceListDTO.setVersion(request.getVersion());
        deviceListDTO.setStartTime(request.getStartTime());
        deviceListDTO.setEndTime(request.getEndTime());
        deviceListDTO.setOwnerPhone(request.getOwnerPhone());

        List<DeviceDTO> deviceDTOS = deviceService.queryDeviceList(deviceListDTO);

        if (deviceDTOS != null && deviceDTOS.size() > 0)
        {
            response.setDeviceDTOS(deviceDTOS.toArray(new DeviceDTO[deviceDTOS.size()]));
        }

        return response;
    }

    /*
     *根据查询条件，查询用户管理的设备
     */
    @RequestMapping(value = "/admin/queryDeviceList",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public AdminQueryDeviceResponse adminQueryDeviceList(@RequestBody AdminQueryDeviceRequest request)
    {
        AdminQueryDeviceResponse response = new AdminQueryDeviceResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter deleteDevice() func ,the user is:{}", request.getUserPhone());
        }

        //填充查询数据
        DeviceListDTO deviceListDTO = new DeviceListDTO();

        deviceListDTO.setDeviceNum(request.getDeviceNum());
        deviceListDTO.setVersion(request.getVersion());
        deviceListDTO.setStartTime(request.getStartTime());
        deviceListDTO.setEndTime(request.getEndTime());
        deviceListDTO.setOwnerPhone(request.getUserPhone());

        List<DeviceDTO> deviceDTOS = deviceService.queryDeviceList(deviceListDTO);

        if (deviceDTOS != null && deviceDTOS.size() > 0)
        {
            response.setDeviceDTOS(deviceDTOS.toArray(new DeviceDTO[deviceDTOS.size()]));
        }

        return response;
    }

    /*
     *根据查询条件，查询用户下的设备（不包括管理的设备）
     */
    @RequestMapping(value = "/user/queryDeviceList",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public UserQueryDeviceResponse userQueryDeviceList(@RequestBody UserQueryDeviceRequest request)
    {
        UserQueryDeviceResponse response = new UserQueryDeviceResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter deleteDevice() func ,the user is:{}", request.getUserPhone());
        }

        List<DeviceDTO> deviceDTOS = deviceService.userQueryDeviceList(request.getUserPhone());

        if (deviceDTOS != null && deviceDTOS.size() > 0)
        {
            response.setDeviceDTOS(deviceDTOS.toArray(new DeviceDTO[deviceDTOS.size()]));
        }

        return response;
    }
}
