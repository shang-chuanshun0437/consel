package com.weiyi.lock.controller;

import com.weiyi.lock.common.Result;
import com.weiyi.lock.common.constant.Constant;
import com.weiyi.lock.common.constant.ErrorCode;
import com.weiyi.lock.common.utils.TimeUtil;
import com.weiyi.lock.dao.entity.DeviceOut;
import com.weiyi.lock.dao.entity.UserAssociateDevice;
import com.weiyi.lock.dao.request.QueryDeviceUserReq;
import com.weiyi.lock.dao.response.QueryDeviceUserRes;
import com.weiyi.lock.interceptor.SecurityAnnotation;
import com.weiyi.lock.request.*;
import com.weiyi.lock.response.*;
import com.weiyi.lock.service.api.DeviceOutService;
import com.weiyi.lock.service.api.UserAssociateDeviceService;
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
@RequestMapping("/user/device")
public class UserAssociateDeviceController
{
    private Logger logger = LoggerFactory.getLogger(UserAssociateDeviceController.class);

    @Autowired
    private UserAssociateDeviceService userAssociateDeviceService;

    @Autowired
    private DeviceOutService deviceOutService;

    /*
     *根据查询条件，查询设备管理员下的所有用户列表
     */
    @RequestMapping(value = "/manage/queryUserList",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public QueryDeviceUserResponse queryUserList(@RequestBody QueryDeviceUsersRequest request)
    {
        QueryDeviceUserResponse response = new QueryDeviceUserResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryUserList() func ,the user is:{}", request.getUserPhone());
        }

        //填充查询数据
        if (request.getCurrentPage() == null || request.getCurrentPage().intValue() <= 0)
        {
            request.setCurrentPage(1);
        }
        QueryDeviceUserReq queryDeviceUserReq = new QueryDeviceUserReq();

        queryDeviceUserReq.setDeviceNum(request.getDeviceNum());
        queryDeviceUserReq.setDeviceName(request.getDeviceName());
        queryDeviceUserReq.setCurrentPage( (request.getCurrentPage() - 1 )*Constant.PAGE_SIZE);
        queryDeviceUserReq.setOwnerPhone(request.getUserPhone());
        queryDeviceUserReq.setUserPhone(request.getNeedPhone());
        //查询总数量
        int total = userAssociateDeviceService.queryDeviceUserCount(queryDeviceUserReq);
        response.setCount(total);

        List<QueryDeviceUserRes> queryDeviceUserRes = userAssociateDeviceService.queryDeviceUser(queryDeviceUserReq);

        if (queryDeviceUserRes != null && queryDeviceUserRes.size() > 0)
        {
            response.setQueryDeviceUserRes(queryDeviceUserRes.toArray(new QueryDeviceUserRes[queryDeviceUserRes.size()]));
        }

        return response;
    }

    /*
     *设备管理员修改设备下的用户钥匙有效期
     */
    @RequestMapping(value = "/modify/user",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public ModifyDeviceUserResponse modifyUser(@RequestBody ModifyDeviceUserRequest request)
    {
        ModifyDeviceUserResponse response = new ModifyDeviceUserResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter modifyUser() func ,the user is:{}", request.getUserPhone());
        }

        //校验是否为该设备的管理员
        DeviceOut deviceOut = deviceOutService.queryDeviceByDeviceNum(request.getDeviceNum());
        if(deviceOut == null || deviceOut.getOwnerPhone() == null || !deviceOut.getOwnerPhone().equals(request.getUserPhone()))
        {
            result.setRetMsg("the device num is error.");
            result.setRetCode(ErrorCode.DEVICE_NUM_ERROR);
            return response;
        }
        //填充修改数据
        UserAssociateDevice userAssociateDevice = new UserAssociateDevice();

        userAssociateDevice.setDeviceNum(request.getDeviceNum());
        userAssociateDevice.setExpiryDate(request.getExpiryDate());
        userAssociateDevice.setUserPhone(request.getNeedModifyPhone());
        userAssociateDevice.setUpdateTime(TimeUtil.getCurrentTime());

        userAssociateDeviceService.updateDeviceUser(userAssociateDevice);
        return response;
    }
}
