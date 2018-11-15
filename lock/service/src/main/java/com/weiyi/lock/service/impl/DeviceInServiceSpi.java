package com.weiyi.lock.service.impl;

import com.weiyi.lock.common.constant.ErrorCode;
import com.weiyi.lock.common.exception.LockAssert;
import com.weiyi.lock.common.utils.CopyProperties;
import com.weiyi.lock.common.utils.TimeUtil;
import com.weiyi.lock.dao.entity.DeviceIn;
import com.weiyi.lock.dao.entity.DeviceOut;
import com.weiyi.lock.dao.entity.OrderSell;
import com.weiyi.lock.dao.mapper.DeviceInMapper;
import com.weiyi.lock.dao.request.QueryDeviceInListReq;
import com.weiyi.lock.service.api.DeviceInService;
import com.weiyi.lock.service.api.DeviceOutService;
import com.weiyi.lock.service.api.OrderSellService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class DeviceInServiceSpi implements DeviceInService
{
    private Logger logger = LoggerFactory.getLogger(DeviceInServiceSpi.class);

    @Autowired
    private DeviceInMapper deviceInMapper;

    @Autowired
    private DeviceOutService deviceOutService;

    @Autowired
    private OrderSellService orderSellService;

    public void addDevice(DeviceIn deviceIn)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter deviceOut insert() func,request:{}", deviceIn);
        }

        //首先判断设备是否已存在
        int count = queryCountByDeviceNum(deviceIn.getDeviceNum());
        LockAssert.isTrue(count <= 0,ErrorCode.DEVICE_EXIST,"device already exist.");

        deviceInMapper.addDevice(deviceIn);
    }

    public void deleteDevice(Long deviceNum) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter deleteDevice() func,deviceNum:{}", deviceNum);
        }

        deviceInMapper.deleteDevice(deviceNum);
    }

    public void updateDevice(DeviceIn deviceIn) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter deleteDevice() func,updateDevice:{}", deviceIn);
        }

        deviceInMapper.updateDevice(deviceIn);
    }

    public int queryCountByDeviceNum(Long deviceNum) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter deviceIn queryCountByDeviceNum() func,request:{}", deviceNum);
        }
        return deviceInMapper.queryCountByDeviceNum(deviceNum);
    }

    public List<DeviceIn> queryDeviceInList(QueryDeviceInListReq request) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter deviceIn queryDeviceInList() func,request:{}", request);
        }

        return deviceInMapper.queryDeviceInList(request);
    }

    public int queryDeviceInListCount(QueryDeviceInListReq request) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter deviceIn queryDeviceInListCount() func,request:{}", request);
        }
        return deviceInMapper.queryDeviceInListCount(request);
    }

    @Transactional
    public void deviceInOutOf(OrderSell request) {
        //查询是否存在该设备
        QueryDeviceInListReq queryDeviceInListReq = new QueryDeviceInListReq();
        queryDeviceInListReq.setCurrentPage(0);
        queryDeviceInListReq.setDeviceNum(request.getDeviceNum());

        List<DeviceIn> deviceIns = queryDeviceInList(queryDeviceInListReq);
        LockAssert.isTrue(deviceIns != null && deviceIns.size() > 0,ErrorCode.DEVICE_NOT_EXIST,"the device num is error");

        //将入库设备表的flag置1
        DeviceIn deviceIn = new DeviceIn();

        deviceIn.setDeviceNum(request.getDeviceNum());
        deviceIn.setFlag(1);
        deviceIn.setOutTime(TimeUtil.getCurrentTime());

        updateDevice(deviceIn);

        //插入一条记录到出库设备表
        DeviceOut deviceOut = new DeviceOut();

        deviceOut.setDeviceNum(deviceIns.get(0).getDeviceNum());
        deviceOut.setBluetoothMac(deviceIns.get(0).getBluetoothMac());
        deviceOut.setVersion(deviceIns.get(0).getVersion());
        deviceOut.setCreateTime(TimeUtil.getCurrentTime());
        deviceOut.setUpdateTime(TimeUtil.getCurrentTime());
        deviceOut.setStatus(0);

        deviceOutService.addDevice(deviceOut);

        //插入一条记录到售出订单表
        OrderSell orderSell = new OrderSell();

        CopyProperties.copy(orderSell,request);
        orderSell.setStatus(2);
        orderSell.setCreateTime(TimeUtil.getCurrentTime());
        orderSell.setUpdateTime(TimeUtil.getCurrentTime());

        orderSellService.addOrder(orderSell);
    }
}
