package com.weiyi.lock.dao.mapper;

import com.weiyi.lock.dao.entity.InterfaceAccess;
import com.weiyi.lock.dao.request.GetInterfaceAccessRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InterfaceAccessMapper
{
    void addRecord(InterfaceAccess interfaceAccess);

    void deleteRecord(int id);

    void updateRecord(InterfaceAccess interfaceAccess);

    int queryListCount(GetInterfaceAccessRequest request);

    List<InterfaceAccess> queryList(GetInterfaceAccessRequest request);
}
