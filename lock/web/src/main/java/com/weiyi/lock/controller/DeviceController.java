package com.weiyi.lock.controller;

import com.weiyi.lock.common.Result;
import com.weiyi.lock.common.constant.Constant;
import com.weiyi.lock.common.constant.ErrorCode;
import com.weiyi.lock.common.constant.PermissionCode;
import com.weiyi.lock.common.utils.TimeUtil;
import com.weiyi.lock.interceptor.SecurityAnnotation;
import com.weiyi.lock.request.*;
import com.weiyi.lock.response.*;
import com.weiyi.lock.service.api.DeviceService;
import com.weiyi.lock.service.request.GetUnManageDeviceRequest;
import com.weiyi.lock.service.response.GetDeviceInfoResponse;
import com.weiyi.lock.service.request.GetManageDeviceRequest;
import com.weiyi.lock.service.response.GetUnManageDeviceRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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

        GetDeviceInfoResponse device = new GetDeviceInfoResponse();
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
        GetDeviceInfoResponse deviceDb = deviceService.queryDeviceByDeviceNum(request.getDeviceNum());
        if(deviceDb == null || deviceDb.getOwnerPhone() != null)
        {
            result.setRetCode(ErrorCode.OWNER_USER_EXIST);
            result.setRetMsg("the device has owner user.");
            return response;
        }
        GetDeviceInfoResponse getDeviceInfoResponse = new GetDeviceInfoResponse();

        getDeviceInfoResponse.setDeviceNum(request.getDeviceNum());
        getDeviceInfoResponse.setBluetoothMac(request.getBluetoothMac());
        getDeviceInfoResponse.setVersion(request.getVersion());
        getDeviceInfoResponse.setUpdateTime(TimeUtil.getCurrentTime());

        deviceService.updateDevice(getDeviceInfoResponse);
        return response;
    }

    /*后台管理人员
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
        GetDeviceInfoResponse deviceDb = deviceService.queryDeviceByDeviceNum(request.getDeviceNum());
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
        if (request.getCurrentPage() == null || request.getCurrentPage().intValue() <= 0)
        {
            request.setCurrentPage(1);
        }
        GetManageDeviceRequest getManageDeviceRequest = new GetManageDeviceRequest();

        getManageDeviceRequest.setDeviceNum(request.getDeviceNum());
        getManageDeviceRequest.setDeviceName(request.getDeviceName());
        getManageDeviceRequest.setCurrentPage(request.getCurrentPage());
        getManageDeviceRequest.setVersion(request.getVersion());
        getManageDeviceRequest.setOwnerPhone(request.getOwnerPhone());

        List<GetDeviceInfoResponse> getDeviceInfoResponses = deviceService.queryManageDevice(getManageDeviceRequest);

        if (getDeviceInfoResponses != null && getDeviceInfoResponses.size() > 0)
        {
            response.setGetDeviceInfoResponses(getDeviceInfoResponses.toArray(new GetDeviceInfoResponse[getDeviceInfoResponses.size()]));
        }

        return response;
    }

    /*
     *根据查询条件，查询用户管理的设备
     */
    @RequestMapping(value = "/manage/queryDeviceList",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public QueryManageDeviceResponse queryManageDeviceList(@RequestBody QueryManageDeviceRequest request)
    {
        QueryManageDeviceResponse response = new QueryManageDeviceResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryManageDeviceList() func ,the user is:{}", request.getUserPhone());
        }

        //填充查询数据
        if (request.getCurrentPage() == null || request.getCurrentPage().intValue() <= 0)
        {
            request.setCurrentPage(1);
        }
        GetManageDeviceRequest getManageDeviceRequest = new GetManageDeviceRequest();

        getManageDeviceRequest.setDeviceNum(request.getDeviceNum());
        getManageDeviceRequest.setDeviceName(request.getDeviceName());
        getManageDeviceRequest.setCurrentPage( (request.getCurrentPage() - 1 )*Constant.PAGE_SIZE);
        getManageDeviceRequest.setOwnerPhone(request.getUserPhone());

        //查询总数量
        int total = deviceService.queryManageDeviceCount(getManageDeviceRequest);
        response.setCount(total);

        List<GetDeviceInfoResponse> getDeviceInfoResponses = deviceService.queryManageDevice(getManageDeviceRequest);

        if (getDeviceInfoResponses != null && getDeviceInfoResponses.size() > 0)
        {
            response.setGetDeviceInfoResponses(getDeviceInfoResponses.toArray(new GetDeviceInfoResponse[getDeviceInfoResponses.size()]));
        }

        return response;
    }

    /*
     *根据查询条件，查询用户下的设备（不包括管理的设备）
     */
    @RequestMapping(value = "/unManage/queryDeviceList",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public QueryUnManageDeviceResponse queryUnManageDevice(@RequestBody QueryUnManageDeviceRequest request)
    {
        QueryUnManageDeviceResponse response = new QueryUnManageDeviceResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryUnManageDeviceList() func ,the user is:{}", request.getUserPhone());
        }

        //填充查询数据
        if (request.getCurrentPage() == null || request.getCurrentPage().intValue() <= 0)
        {
            request.setCurrentPage(1);
        }
        GetUnManageDeviceRequest getManageDeviceRequest = new GetUnManageDeviceRequest();

        getManageDeviceRequest.setDeviceNum(request.getDeviceNum());
        getManageDeviceRequest.setDeviceName(request.getDeviceName());
        getManageDeviceRequest.setCurrentPage( (request.getCurrentPage() - 1 )*Constant.PAGE_SIZE);
        getManageDeviceRequest.setUserPhone(request.getUserPhone());
        getManageDeviceRequest.setOwnerPhone(request.getOwnerPhone());

        //查询总数量
        int total = deviceService.queryUnManageDeviceCount(getManageDeviceRequest);
        response.setCount(total);

        //查询设备列表
        List<GetUnManageDeviceRes> getUnManageDeviceRes = deviceService.queryUnManageDevice(getManageDeviceRequest);

        if (getUnManageDeviceRes != null && getUnManageDeviceRes.size() > 0)
        {
            response.setGetUnManageDeviceRes(getUnManageDeviceRes.toArray(new GetUnManageDeviceRes[getUnManageDeviceRes.size()]));
        }

        return response;
    }

    /*
     *设备管理员，修改设备名称
     */
    @RequestMapping(value = "/modify/deviceName",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public ModifyDeviceNameResponse modifyDeviceName(@RequestBody ModifyDeviceNameRequest request)
    {
        ModifyDeviceNameResponse response = new ModifyDeviceNameResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter modifyDeviceName() func ,the device num:{}", request.getDeviceNum());
        }

        //去查询设备，如果设备下已存在ownerPhone，则不允许后台人员进行修改
        GetDeviceInfoResponse deviceDb = deviceService.queryDeviceByDeviceNum(request.getDeviceNum());
        if(deviceDb.getDeviceNum() == null || deviceDb.getOwnerPhone() == null || !deviceDb.getOwnerPhone().equals(request.getUserPhone()))
        {
            result.setRetCode(ErrorCode.DEVICE_NUM_ERROR);
            result.setRetMsg("the device num is error.");
            return response;
        }
        GetDeviceInfoResponse getDeviceInfoResponse = new GetDeviceInfoResponse();

        getDeviceInfoResponse.setDeviceNum(request.getDeviceNum());
        getDeviceInfoResponse.setDeviceName(request.getDeviceName());
        getDeviceInfoResponse.setUpdateTime(TimeUtil.getCurrentTime());

        deviceService.updateDevice(getDeviceInfoResponse);
        return response;
    }
}
