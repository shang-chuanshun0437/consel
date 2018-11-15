package com.weiyi.lock.admin;

import com.weiyi.lock.common.Result;
import com.weiyi.lock.common.constant.Constant;
import com.weiyi.lock.common.exception.LockException;
import com.weiyi.lock.common.utils.CopyProperties;
import com.weiyi.lock.common.utils.TimeUtil;
import com.weiyi.lock.dao.entity.DeviceIn;
import com.weiyi.lock.dao.entity.OrderSell;
import com.weiyi.lock.dao.request.QueryDeviceInListReq;
import com.weiyi.lock.interceptor.SecurityAnnotation;
import com.weiyi.lock.request.*;
import com.weiyi.lock.request.AddDeviceInRequest;
import com.weiyi.lock.response.*;
import com.weiyi.lock.service.api.DeviceInService;
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
public class H5DeviceInController
{
    private Logger logger = LoggerFactory.getLogger(H5DeviceInController.class);

    @Autowired
    private DeviceInService deviceInService;

    /*
    *设备入库，设备编号唯一
    * 如果设备已存在，则添加失败
    */
    @RequestMapping(value = "/deviceIn/addDevice",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public AddDeviceInResponse addDevice(@RequestBody AddDeviceInRequest request)
    {
        AddDeviceInResponse response = new AddDeviceInResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter addDevice() func ,the request:{}",request);
        }

        DeviceIn deviceIn = new DeviceIn();

        deviceIn.setDeviceNum(request.getDeviceNum());
        deviceIn.setBluetoothMac(request.getBluetoothMac());
        deviceIn.setVersion(request.getVersion());
        deviceIn.setCreateTime(TimeUtil.getCurrentTime());
        deviceIn.setFlag(0);
        deviceIn.setUserPhone(request.getUserPhone());
        deviceIn.setUserName(request.getUserName());

        try{
            deviceInService.addDevice(deviceIn);
        }catch (LockException e)
        {
            result.setRetMsg(e.getMsg());
            result.setRetCode(e.getCode());
        }

        return response;
    }

    /*
     * 根据查询条件，查询入库设备列表
     */
    @RequestMapping(value = "/deviceIn/queryList",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public QueryDeviceInResponse queryDeviceInList(@RequestBody QueryDeviceInRequest request)
    {
        QueryDeviceInResponse response = new QueryDeviceInResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter addDevice() func ,the request:{}",request);
        }

        QueryDeviceInListReq queryDeviceInListReq = new QueryDeviceInListReq();

        if (request.getCurrentPage() == null || request.getCurrentPage().intValue() <= 0)
        {
            request.setCurrentPage(1);
        }
        queryDeviceInListReq.setCurrentPage((request.getCurrentPage() - 1) * Constant.PAGE_SIZE);
        queryDeviceInListReq.setDeviceNum(request.getDeviceNum());

        int total = deviceInService.queryDeviceInListCount(queryDeviceInListReq);
        response.setCount(total);

        List<DeviceIn> deviceIns = deviceInService.queryDeviceInList(queryDeviceInListReq);
        if(deviceIns != null && deviceIns.size() > 0)
        {
            response.setDeviceIns(deviceIns.toArray(new DeviceIn[deviceIns.size()]));
        }
        return response;
    }

    /*
     *删除设备
     */
    @RequestMapping(value = "/deviceIn/deleteDevice",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public DeleteDeviceInResponse deleteDevice(@RequestBody DeleteDeviceInRequest request)
    {
        DeleteDeviceInResponse response = new DeleteDeviceInResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter deleteDevice() func ,the request:{}",request);
        }

        deviceInService.deleteDevice(request.getDeviceNum());

        return response;
    }

    /*
     *更新设备
     */
    @RequestMapping(value = "/deviceIn/updateDevice",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public UpdateDeviceInResponse updateDevice(@RequestBody UpdateDeviceInRequest request)
    {
        UpdateDeviceInResponse response = new UpdateDeviceInResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter updateDevice() func ,the request:{}",request);
        }

        DeviceIn deviceIn = new DeviceIn();

        deviceIn.setBluetoothMac(request.getBluetoothMac());
        deviceIn.setVersion(request.getVersion());
        deviceIn.setDeviceNum(request.getDeviceNum());

        deviceInService.updateDevice(deviceIn);

        return response;
    }

    /*
     *设备出库
     * 1、将入库设备表的flag置1
     * 2、
     */
    @RequestMapping(value = "/deviceIn/outOfDevice",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public OutOfDeviceInResponse outOfDevice(@RequestBody OutOfDeviceInRequest request)
    {
        OutOfDeviceInResponse response = new OutOfDeviceInResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter outOfDevice() func ,the request:{}",request);
        }

        OrderSell orderSell = new OrderSell();
        CopyProperties.copy(orderSell,request);

        try{
            deviceInService.deviceInOutOf(orderSell);
        }catch (LockException e)
        {
            result.setRetCode(e.getCode());
            result.setRetMsg(e.getMsg());
        }

        return response;
    }
}
