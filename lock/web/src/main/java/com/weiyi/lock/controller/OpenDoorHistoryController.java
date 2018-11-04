package com.weiyi.lock.controller;

import com.weiyi.lock.common.Result;
import com.weiyi.lock.common.constant.ErrorCode;
import com.weiyi.lock.common.constant.PermissionCode;
import com.weiyi.lock.common.utils.TimeUtil;
import com.weiyi.lock.dao.entity.OpenDoorListDTO;
import com.weiyi.lock.interceptor.SecurityAnnotation;
import com.weiyi.lock.request.*;
import com.weiyi.lock.response.*;
import com.weiyi.lock.service.api.DeviceService;
import com.weiyi.lock.service.api.OpenDoorHistoryService;
import com.weiyi.lock.service.response.GetOpenDoorHistoryInfoRes;
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
@RequestMapping("/open/history")
public class OpenDoorHistoryController
{
    private Logger logger = LoggerFactory.getLogger(OpenDoorHistoryController.class);

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private OpenDoorHistoryService openDoorHistoryService;

    /*
    *新增一条开门记录
    */
    @RequestMapping(value = "/addHistory",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public AddOpenHistoryResponse addHistory(@RequestBody AddOpenHistoryRequest request)
    {
        AddOpenHistoryResponse response = new AddOpenHistoryResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter addHistory() func ,the user is:{}",request.getUserPhone());
        }

        //首先判断设备是否存在
        {
            int count = deviceService.queryCountByDeviceNum(request.getDeviceNum());
            if (count <= 0)
            {
                result.setRetCode(ErrorCode.DEVICE_NOT_EXIST);
                result.setRetMsg("device not exist.");
                return response;
            }
        }

        //组装数据
        GetOpenDoorHistoryInfoRes getOpenDoorHistoryInfoRes = new GetOpenDoorHistoryInfoRes();

        getOpenDoorHistoryInfoRes.setUserPhone(request.getUserPhone());
        getOpenDoorHistoryInfoRes.setDeviceNum(request.getDeviceNum());
        getOpenDoorHistoryInfoRes.setDeviceName(request.getDeviceName());
        getOpenDoorHistoryInfoRes.setUserName(request.getUserName());
        getOpenDoorHistoryInfoRes.setOpenTime(TimeUtil.getCurrentTime());

        openDoorHistoryService.addHistory(getOpenDoorHistoryInfoRes);

        return response;
    }

    /*
     * 查询开门记录，后台管理人员使用
     */
    @RequestMapping(value = "/query/openHistoryList",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation(value = {PermissionCode.DEVICE})
    public QueryOpenHistoryResponse queryOpenHistory(@RequestBody QueryOpenHistoryRequest request)
    {
        QueryOpenHistoryResponse response = new QueryOpenHistoryResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryOpenHistory() func ,the device num:{}", request.getDeviceNum());
        }

        //组装查询条件
        OpenDoorListDTO openDoorListDTO = new OpenDoorListDTO();

        openDoorListDTO.setDeviceNum(request.getDeviceNum());
        openDoorListDTO.setUserPhone(request.getQueryUser());
        openDoorListDTO.setPageIndex(request.getPageIndex());
        openDoorListDTO.setStartTime(request.getStartTime());
        openDoorListDTO.setEndTime(request.getEndTime());
        openDoorListDTO.setVersion(request.getVersion());

        List<GetOpenDoorHistoryInfoRes> getOpenDoorHistoryInfoRes = openDoorHistoryService.queryOpenHistory(openDoorListDTO);

        if(getOpenDoorHistoryInfoRes != null && getOpenDoorHistoryInfoRes.size() > 0)
        {
            response.setGetOpenDoorHistoryInfoRes(getOpenDoorHistoryInfoRes.toArray(new GetOpenDoorHistoryInfoRes[getOpenDoorHistoryInfoRes.size()]));
        }
        return response;
    }
    /*
     * 查询开门记录，普通登录的用户使用
     */
    @RequestMapping(value = "/user/query/openHistoryList",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public QueryOpenHistoryResponse userQueryOpenHistory(@RequestBody QueryOpenHistoryRequest request)
    {
        QueryOpenHistoryResponse response = new QueryOpenHistoryResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter userQueryOpenHistory() func ,the device num:{}", request.getDeviceNum());
        }
        //组装查询条件
        OpenDoorListDTO openDoorListDTO = new OpenDoorListDTO();

        openDoorListDTO.setDeviceNum(request.getDeviceNum());
        openDoorListDTO.setUserPhone(request.getUserPhone());
        openDoorListDTO.setPageIndex(request.getPageIndex());
        openDoorListDTO.setStartTime(request.getStartTime());
        openDoorListDTO.setEndTime(request.getEndTime());
        openDoorListDTO.setVersion(request.getVersion());

        List<GetOpenDoorHistoryInfoRes> getOpenDoorHistoryInfoRes = openDoorHistoryService.queryOpenHistory(openDoorListDTO);

        if(getOpenDoorHistoryInfoRes != null && getOpenDoorHistoryInfoRes.size() > 0)
        {
            response.setGetOpenDoorHistoryInfoRes(getOpenDoorHistoryInfoRes.toArray(new GetOpenDoorHistoryInfoRes[getOpenDoorHistoryInfoRes.size()]));
        }
        return response;
    }
}
