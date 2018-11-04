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
import com.weiyi.lock.service.request.GetManageDeviceRequest;
import com.weiyi.lock.service.request.GetUnManageDeviceRequest;
import com.weiyi.lock.service.response.GetDeviceInfoResponse;
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
@RequestMapping("/user/device")
public class UserAssociateDeviceController
{
    private Logger logger = LoggerFactory.getLogger(UserAssociateDeviceController.class);

    @Autowired
    private DeviceService deviceService;

    /*
     *根据查询条件，查询设备管理员下的所有用户列表
     */
    @RequestMapping(value = "/manage/queryUserList",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public QueryManageDeviceResponse queryUserList(@RequestBody QueryDeviceUsersRequest request)
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
}
