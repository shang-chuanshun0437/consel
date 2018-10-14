package com.weiyi.lock.dao.mapper;

import com.weiyi.lock.dao.entity.Device;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Service
public interface DeviceMapper
{
    void insert(Device device);
}
