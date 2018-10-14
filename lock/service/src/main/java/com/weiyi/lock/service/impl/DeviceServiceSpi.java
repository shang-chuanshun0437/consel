package com.weiyi.lock.service.impl;

import com.weiyi.lock.common.redis.RedisClient;
import com.weiyi.lock.dao.entity.Device;
import com.weiyi.lock.service.api.DeviceService;
import com.weiyi.lock.dao.mapper.DeviceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceServiceSpi implements DeviceService
{
    private Logger logger = LoggerFactory.getLogger(DeviceServiceSpi.class);

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private RedisClient redisClient;

    public void insert(Device device)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter device insert() func,request:{}",device);
        }
        redisClient.set("eee","eeexss");

        deviceMapper.insert(device);
    }
}
