package com.weiyi.lock.service.impl;

import com.weiyi.lock.common.utils.CopyProperties;
import com.weiyi.lock.dao.request.QueryManageDeviceReq;
import com.weiyi.lock.dao.request.QueryUnManageDeviceReq;
import com.weiyi.lock.dao.response.QueryUnManageDeviceRes;
import com.weiyi.lock.dao.entity.Device;
import com.weiyi.lock.service.api.DeviceService;
import com.weiyi.lock.dao.mapper.DeviceMapper;
import com.weiyi.lock.service.request.GetUnManageDeviceRequest;
import com.weiyi.lock.service.response.GetDeviceInfoResponse;
import com.weiyi.lock.service.request.GetManageDeviceRequest;
import com.weiyi.lock.service.response.GetUnManageDeviceRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceServiceSpi implements DeviceService
{
    private Logger logger = LoggerFactory.getLogger(DeviceServiceSpi.class);

    @Autowired
    private DeviceMapper deviceMapper;

    public void addDevice(Device device)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter device insert() func,request:{}", device);
        }

        deviceMapper.addDevice(device);
    }

    public int queryCountByDeviceNum(Long deviceNum)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryCountByDeviceNum() func,deviceNum:{}",deviceNum);
        }
        return deviceMapper.queryCountByDeviceNum(deviceNum);
    }

    public GetDeviceInfoResponse queryDeviceByDeviceNum(Long deviceNum)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryDeviceByDeviceNum() func,deviceNum:{}",deviceNum);
        }
        GetDeviceInfoResponse getDeviceInfoResponse = new GetDeviceInfoResponse();
        Device device = deviceMapper.queryDeviceByDeviceNum(deviceNum);

        CopyProperties.copy(getDeviceInfoResponse,device);
        return getDeviceInfoResponse;
    }

    /*
    *过滤出符合条件的设备列表
    */
    public List<GetDeviceInfoResponse> queryManageDevice(GetManageDeviceRequest getManageDeviceRequest)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryDeviceByDeviceNum() func,deviceNum:{}", getManageDeviceRequest.getDeviceNum());
        }

        QueryManageDeviceReq queryManageDeviceReq = new QueryManageDeviceReq();

        CopyProperties.copy(queryManageDeviceReq, getManageDeviceRequest);
        List<Device> devices = deviceMapper.queryManageDevice(queryManageDeviceReq);

        //转换数据
        List<GetDeviceInfoResponse> getDeviceInfoResponses = new ArrayList<GetDeviceInfoResponse>();
        if (devices != null && devices.size() > 0)
        {
            for (Device device : devices)
            {
                GetDeviceInfoResponse getDeviceInfoResponse = new GetDeviceInfoResponse();
                CopyProperties.copy(getDeviceInfoResponse,device);
                getDeviceInfoResponses.add(getDeviceInfoResponse);
            }
            return getDeviceInfoResponses;
        }

        return null;
    }

    public int queryManageDeviceCount(GetManageDeviceRequest getManageDeviceRequest) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryDeviceListCount() func,deviceNum:{}", getManageDeviceRequest.getDeviceNum());
        }

        QueryManageDeviceReq queryManageDeviceReq = new QueryManageDeviceReq();

        CopyProperties.copy(queryManageDeviceReq, getManageDeviceRequest);
        return deviceMapper.queryManageDeviceCount(queryManageDeviceReq);
    }

    public int queryUnManageDeviceCount(GetUnManageDeviceRequest request) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryUnManageDeviceCount() func,userPhone:{}", request.getUserPhone());
        }

        QueryUnManageDeviceReq queryUnManageDeviceReq = new QueryUnManageDeviceReq();

        CopyProperties.copy(queryUnManageDeviceReq, request);
        return deviceMapper.queryUnManageDeviceCount(queryUnManageDeviceReq);
    }

    public List<GetUnManageDeviceRes> queryUnManageDevice(GetUnManageDeviceRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter userQueryDeviceList() func,user phone:{}",request.getUserPhone());
        }

        QueryUnManageDeviceReq queryUnManageDeviceReq = new QueryUnManageDeviceReq();
        CopyProperties.copy(queryUnManageDeviceReq,request);

        List<QueryUnManageDeviceRes> list = deviceMapper.queryUnManageDevice(queryUnManageDeviceReq);

        List<com.weiyi.lock.service.response.GetUnManageDeviceRes> getUnManageDeviceResList = new ArrayList<com.weiyi.lock.service.response.GetUnManageDeviceRes>();

        if (list != null && list.size() > 0)
        {
            for(QueryUnManageDeviceRes temp : list)
            {
                com.weiyi.lock.service.response.GetUnManageDeviceRes getUnManageDeviceRes = new com.weiyi.lock.service.response.GetUnManageDeviceRes();
                CopyProperties.copy(getUnManageDeviceRes,temp);
                getUnManageDeviceResList.add(getUnManageDeviceRes);
            }

        }
        return getUnManageDeviceResList;
    }

    public void updateDevice(Device device)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryCountByDeviceNum() func,deviceNum:{}", device.getDeviceNum());
        }

        deviceMapper.updateDevice(device);
    }

    public void deleteDevice(Long deviceNum)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter deleteDevice() func,deviceNum:{}",deviceNum);
        }

        deviceMapper.deleteDevice(deviceNum);
    }

    public void updateOwner(Long deviceNum,Long userPhone)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter updateOwner() func,deviceNum:{}",deviceNum);
        }

        Device device = new Device();
        device.setDeviceNum(deviceNum);
        device.setOwnerPhone(userPhone);

        deviceMapper.updateOwner(device);
    }
}
