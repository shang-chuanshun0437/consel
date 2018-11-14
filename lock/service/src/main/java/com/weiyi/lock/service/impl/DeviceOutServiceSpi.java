package com.weiyi.lock.service.impl;

import com.weiyi.lock.common.constant.ErrorCode;
import com.weiyi.lock.common.exception.LockAssert;
import com.weiyi.lock.common.exception.LockException;
import com.weiyi.lock.common.utils.TimeUtil;
import com.weiyi.lock.dao.entity.UserAssociateDevice;
import com.weiyi.lock.dao.request.QueryManageDeviceOutReq;
import com.weiyi.lock.dao.request.QueryUnManageDeviceOutReq;
import com.weiyi.lock.dao.response.QueryUnManageDeviceOutRes;
import com.weiyi.lock.dao.entity.DeviceOut;
import com.weiyi.lock.service.api.DeviceOutService;
import com.weiyi.lock.dao.mapper.DeviceOutMapper;
import com.weiyi.lock.service.api.UserAssociateDeviceService;
import com.weiyi.lock.service.api.UserService;
import com.weiyi.lock.service.request.BindDevice4UserReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DeviceOutServiceSpi implements DeviceOutService
{
    private Logger logger = LoggerFactory.getLogger(DeviceOutServiceSpi.class);

    @Autowired
    private DeviceOutMapper deviceOutMapper;

    @Autowired
    private UserAssociateDeviceService userAssociateDeviceService;

    @Autowired
    private UserService userService;

    public void addDevice(DeviceOut request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter deviceOut insert() func,request:{}", request);
        }
        //首先判断设备是否已存在
        int count = queryCountByDeviceNum(request.getDeviceNum());
        LockAssert.isTrue(count <= 0,ErrorCode.DEVICE_EXIST,"device already exist.");

        deviceOutMapper.addDevice(request);
    }

    public int queryCountByDeviceNum(Long deviceNum)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryCountByDeviceNum() func,deviceNum:{}",deviceNum);
        }
        return deviceOutMapper.queryCountByDeviceNum(deviceNum);
    }

    public DeviceOut queryDeviceByDeviceNum(Long deviceNum)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryDeviceByDeviceNum() func,deviceNum:{}",deviceNum);
        }
        DeviceOut deviceOut = deviceOutMapper.queryDeviceByDeviceNum(deviceNum);

        return deviceOut;
    }

    /*
    *过滤出符合条件的设备列表
    */
    public List<DeviceOut> queryManageDevice(QueryManageDeviceOutReq queryManageDeviceOutReq)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryDeviceByDeviceNum() func,deviceNum:{}", queryManageDeviceOutReq.getDeviceNum());
        }

        List<DeviceOut> deviceOuts = deviceOutMapper.queryManageDevice(queryManageDeviceOutReq);

        return deviceOuts;
    }

    public int queryManageDeviceCount(QueryManageDeviceOutReq queryManageDeviceOutReq) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryDeviceListCount() func,deviceNum:{}", queryManageDeviceOutReq.getDeviceNum());
        }
        return deviceOutMapper.queryManageDeviceCount(queryManageDeviceOutReq);
    }

    public int queryUnManageDeviceCount(QueryUnManageDeviceOutReq request) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryUnManageDeviceCount() func,userPhone:{}", request.getUserPhone());
        }

        return deviceOutMapper.queryUnManageDeviceCount(request);
    }

    public List<QueryUnManageDeviceOutRes> queryUnManageDevice(QueryUnManageDeviceOutReq request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter userQueryDeviceList() func,user phone:{}",request.getUserPhone());
        }

        List<QueryUnManageDeviceOutRes> list = deviceOutMapper.queryUnManageDevice(request);

        return list;
    }

    public void updateDevice(DeviceOut deviceOut)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryCountByDeviceNum() func,deviceNum:{}", deviceOut.getDeviceNum());
        }

        deviceOutMapper.updateDevice(deviceOut);
    }

    public void bindDevice(Long deviceNum, String deviceName,Long userPhone) {
        //根据设备ID，获取设备
        DeviceOut dbDevice = queryDeviceByDeviceNum(deviceNum);

        //判断设备是否存在，以及是否存在管理员
        if (dbDevice.getDeviceNum() == null || dbDevice.getOwnerPhone() != null)
        {
            throw new LockException(ErrorCode.BIND_DEVICE_ERROR,"please input correct device num.");
        }

        dbDevice.setOwnerPhone(userPhone);
        dbDevice.setDeviceName(deviceName);
        dbDevice.setUpdateTime(TimeUtil.getCurrentTime());
        dbDevice.setUserCount(dbDevice.getUserCount() + 1);
        //更新设备表
        updateDevice(dbDevice);
    }

    @Transactional
    public void bindDevice4User(BindDevice4UserReq request) {
        //校验待绑定的用户是否注册
        int count = userService.countByPhone(request.getNeedBindPhone());
        LockAssert.isTrue(count >= 1,ErrorCode.USER_NOT_EXIST,"the need bind user not register.");

        //校验用户是否该设备的管理员
        DeviceOut deviceDb = queryDeviceByDeviceNum(request.getDeviceNum());
        if(deviceDb == null || deviceDb.getOwnerPhone() == null || !deviceDb.getOwnerPhone().equals(request.getUserPhone()))
        {
            throw new LockException(ErrorCode.BIND_DEVICE_ERROR,"the device num is error.");
        }

        //如果管理员绑定自己
        if(request.getUserPhone().equals(request.getNeedBindPhone()))
        {
            return;
        }

        //查询该用户是否已经绑定
        UserAssociateDevice userAssociateDevice = new UserAssociateDevice();
        userAssociateDevice.setDeviceNum(request.getDeviceNum());
        userAssociateDevice.setUserPhone(request.getNeedBindPhone());

        int total = userAssociateDeviceService.queryByNumAndPhone(userAssociateDevice);
        if (total <= 0)
        {
            //用户未绑定该设备
            //更新device_out表,该设备的用户数加1
            deviceDb.setUserCount(deviceDb.getUserCount() + 1);
            updateDevice(deviceDb);
        }

        //更新用户设备关联表
        UserAssociateDevice updateUserAssociateDevice = new UserAssociateDevice();

        updateUserAssociateDevice.setUserPhone(request.getNeedBindPhone());
        updateUserAssociateDevice.setDeviceNum(request.getDeviceNum());
        updateUserAssociateDevice.setCreateTime(TimeUtil.getCurrentTime());
        updateUserAssociateDevice.setUpdateTime(updateUserAssociateDevice.getCreateTime());
        updateUserAssociateDevice.setExpiryDate(request.getExpiryDate());

        userAssociateDeviceService.bindDevice(updateUserAssociateDevice);
    }

    public void unBindDevice(Long deviceNum, Long userPhone) {
        //根据设备ID，获取设备
        DeviceOut dbDevice = queryDeviceByDeviceNum(deviceNum);

        //判断设备是否存在，以及是否存在管理员
        if (dbDevice.getDeviceNum() == null || dbDevice.getOwnerPhone() == null)
        {
            throw new LockException(ErrorCode.UNBIND_DEVICE_ERROR,"please input correct device num.");
        }

        //校验自己是否是管理员、设备下是否有其他用户
        if (dbDevice.getOwnerPhone().equals(userPhone))
        {
            int count = dbDevice.getUserCount();
            LockAssert.isTrue(count <= 1,ErrorCode.OTHER_USERS_EXIST,"the device has other users.");

            //如果是管理员，则需要将设备表中的owner_phone字段设置为null
            dbDevice.setUserCount(0);
            dbDevice.setOwnerPhone(null);
            dbDevice.setUpdateTime(TimeUtil.getCurrentTime());

            updateDevice(dbDevice);
            return;
        }

        //如果是非管理员则删除用户设备关联表的记录
        dbDevice.setUserCount(dbDevice.getUserCount() - 1);
        updateDevice(dbDevice);

        userAssociateDeviceService.deleteByPhoneAndNum(userPhone,deviceNum);
    }

    public void unBindDevice4User(Long deviceNum, Long unBindUser, Long ownerPhone) {
        //校验用户是否该设备的管理员
        DeviceOut deviceDb = queryDeviceByDeviceNum(deviceNum);
        if(deviceDb == null || deviceDb.getOwnerPhone() == null || !deviceDb.getOwnerPhone().equals(ownerPhone))
        {
            throw new LockException(ErrorCode.BIND_DEVICE_ERROR,"the device num is error.");
        }

        //更新device表
        deviceDb.setUserCount(deviceDb.getUserCount() - 1);
        updateDevice(deviceDb);

        //更新用户设备关联表
        userAssociateDeviceService.deleteByPhoneAndNum(unBindUser,deviceNum);
    }

}
