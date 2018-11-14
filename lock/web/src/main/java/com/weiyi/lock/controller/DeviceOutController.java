package com.weiyi.lock.controller;

import com.weiyi.lock.common.Result;
import com.weiyi.lock.common.constant.Constant;
import com.weiyi.lock.common.constant.ErrorCode;
import com.weiyi.lock.common.constant.PermissionCode;
import com.weiyi.lock.common.exception.LockException;
import com.weiyi.lock.common.utils.TimeUtil;
import com.weiyi.lock.dao.entity.DeviceOut;
import com.weiyi.lock.dao.request.QueryManageDeviceOutReq;
import com.weiyi.lock.dao.request.QueryUnManageDeviceOutReq;
import com.weiyi.lock.dao.response.QueryUnManageDeviceOutRes;
import com.weiyi.lock.interceptor.SecurityAnnotation;
import com.weiyi.lock.request.*;
import com.weiyi.lock.response.*;
import com.weiyi.lock.service.api.DeviceOutService;
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
public class DeviceOutController
{
    private Logger logger = LoggerFactory.getLogger(DeviceOutController.class);

    @Autowired
    private DeviceOutService deviceOutService;

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

        DeviceOut deviceOut = new DeviceOut();
        deviceOut.setDeviceName(request.getDeviceName());
        deviceOut.setDeviceNum(request.getDeviceNum());
        deviceOut.setBluetoothMac(request.getBluetoothMac());
        deviceOut.setVersion(request.getVersion());
        deviceOut.setCreateTime(TimeUtil.getCurrentTime());
        deviceOut.setUpdateTime(deviceOut.getCreateTime());

        try {
            deviceOutService.addDevice(deviceOut);
        }catch (LockException e)
        {
            result.setRetCode(e.getCode());
            result.setRetMsg(e.getMsg());
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
        QueryManageDeviceOutReq queryManageDeviceOutReq = new QueryManageDeviceOutReq();

        queryManageDeviceOutReq.setDeviceNum(request.getDeviceNum());
        queryManageDeviceOutReq.setDeviceName(request.getDeviceName());
        queryManageDeviceOutReq.setCurrentPage( (request.getCurrentPage() - 1 )*Constant.PAGE_SIZE);
        queryManageDeviceOutReq.setOwnerPhone(request.getUserPhone());
        if(request.getStatus() != null)
        {
            queryManageDeviceOutReq.setStatus(request.getStatus());
        }
        //查询总数量
        int total = deviceOutService.queryManageDeviceCount(queryManageDeviceOutReq);
        response.setCount(total);

        List<DeviceOut> deviceOuts = deviceOutService.queryManageDevice(queryManageDeviceOutReq);

        if (deviceOuts != null && deviceOuts.size() > 0)
        {
            response.setDeviceOuts(deviceOuts.toArray(new DeviceOut[deviceOuts.size()]));
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
        QueryUnManageDeviceOutReq queryUnManageDeviceOutReq = new QueryUnManageDeviceOutReq();

        queryUnManageDeviceOutReq.setDeviceNum(request.getDeviceNum());
        queryUnManageDeviceOutReq.setDeviceName(request.getDeviceName());
        queryUnManageDeviceOutReq.setCurrentPage( (request.getCurrentPage() - 1 )*Constant.PAGE_SIZE);
        queryUnManageDeviceOutReq.setUserPhone(request.getUserPhone());
        queryUnManageDeviceOutReq.setOwnerPhone(request.getOwnerPhone());

        //查询总数量
        int total = deviceOutService.queryUnManageDeviceCount(queryUnManageDeviceOutReq);
        response.setCount(total);

        //查询设备列表
        List<QueryUnManageDeviceOutRes> queryUnManageDeviceOutRes = deviceOutService.queryUnManageDevice(queryUnManageDeviceOutReq);

        if (queryUnManageDeviceOutRes != null && queryUnManageDeviceOutRes.size() > 0)
        {
            response.setQueryUnManageDeviceOutRes(queryUnManageDeviceOutRes.toArray(new QueryUnManageDeviceOutRes[queryUnManageDeviceOutRes.size()]));
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

        //去查询设备，判断ownerPhone是否和请求中的userPhone相同
        DeviceOut deviceDb = deviceOutService.queryDeviceByDeviceNum(request.getDeviceNum());
        if(deviceDb.getDeviceNum() == null || deviceDb.getOwnerPhone() == null || !deviceDb.getOwnerPhone().equals(request.getUserPhone()))
        {
            result.setRetCode(ErrorCode.DEVICE_NUM_ERROR);
            result.setRetMsg("the device num is error.");
            return response;
        }
        DeviceOut deviceOut = new DeviceOut();

        deviceOut.setDeviceNum(request.getDeviceNum());
        deviceOut.setDeviceName(request.getDeviceName());
        deviceOut.setUpdateTime(TimeUtil.getCurrentTime());

        deviceOutService.updateDevice(deviceOut);
        return response;
    }
}
