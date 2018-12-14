package com.weiyi.lock.admin;

import com.weiyi.lock.common.Result;
import com.weiyi.lock.common.constant.Constant;
import com.weiyi.lock.common.constant.ErrorCode;
import com.weiyi.lock.common.utils.CopyProperties;
import com.weiyi.lock.common.utils.TimeUtil;
import com.weiyi.lock.dao.entity.DeviceOut;
import com.weiyi.lock.dao.entity.OrderSell;
import com.weiyi.lock.dao.request.QueryManageDeviceOutReq;
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
public class H5DeviceOutController
{
    private Logger logger = LoggerFactory.getLogger(H5DeviceOutController.class);

    @Autowired
    private DeviceOutService deviceOutService;

    /*
     * 后台人员修改设备 TODO
     * 如果该设备下有用户则禁止修改
     */
    @RequestMapping(value = "/updateDevice",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation(value = {"ADMIN"})
    public UpdateDeviceResponse updateDevice(@RequestBody UpdateDeviceRequest request)
    {
        UpdateDeviceResponse response = new UpdateDeviceResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter updateDevice() func ,the device num:{}", request.getDeviceNum());
        }

        if (request.getStatus() != null)
        {
            if (request.getStatus().intValue() > 1 || request.getStatus().intValue() < 0)
            {
                request.setStatus(1);
            }
        }

        //去查询设备，如果设备处于启用状态且不是修改设备的状态，则不允许后台人员进行修改
        DeviceOut deviceDb = deviceOutService.queryDeviceByDeviceNum(request.getDeviceNum());

        if (deviceDb.getDeviceNum() == null || (deviceDb.getStatus() == Constant.ENABLE && request.getStatus() == null))
        {
            result.setRetMsg("the device num is error.");
            result.setRetCode(ErrorCode.DEVICE_NUM_ERROR);
            return response;
        }

        DeviceOut deviceOut = new DeviceOut();

        deviceOut.setDeviceNum(request.getDeviceNum());
        deviceOut.setBluetoothMac(request.getBluetoothMac());
        deviceOut.setVersion(request.getVersion());
        deviceOut.setUpdateTime(TimeUtil.getCurrentTime());
        deviceOut.setStatus(request.getStatus());

        deviceOutService.updateDevice(deviceOut);
        return response;
    }

    /*
    *查询符合条件的设备列表,后台管理人员
    */
    @RequestMapping(value = "/queryDeviceList",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation(value = {"ADMIN"})
    public QueryDeviceResponse queryDeviceList(@RequestBody QueryDeviceOutRequest request)
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
        QueryManageDeviceOutReq queryManageDeviceOutReq = new QueryManageDeviceOutReq();

        queryManageDeviceOutReq.setDeviceNum(request.getDeviceNum());
        queryManageDeviceOutReq.setDeviceName(request.getDeviceName());
        queryManageDeviceOutReq.setCurrentPage((request.getCurrentPage() - 1) * Constant.PAGE_SIZE);
        queryManageDeviceOutReq.setVersion(request.getVersion());
        queryManageDeviceOutReq.setOwnerPhone(request.getOwnerPhone());
        queryManageDeviceOutReq.setStatus(request.getStatus());

        //查询数量
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
     *退换货
     */
    @RequestMapping(value = "/replaceDevice",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation(value = {"ADMIN"})
    public ReplaceDeviceResponse replaceDevice(@RequestBody ReplaceDeviceRequest request)
    {
        ReplaceDeviceResponse response = new ReplaceDeviceResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter deleteDevice() func ,the device num:{}", request.getDeviceNum());
        }

        OrderSell orderSell = new OrderSell();

        CopyProperties.copy(orderSell,request);

        deviceOutService.replaceDevice(orderSell);

        return response;
    }
}
