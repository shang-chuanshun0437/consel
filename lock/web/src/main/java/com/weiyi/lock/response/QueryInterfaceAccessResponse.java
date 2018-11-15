package com.weiyi.lock.response;

import com.weiyi.lock.dao.entity.InterfaceAccess;

public class QueryInterfaceAccessResponse extends BaseResponse
{
    private int count;

    private InterfaceAccess[] interfaceAccesses;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public InterfaceAccess[] getInterfaceAccesses() {
        return interfaceAccesses;
    }

    public void setInterfaceAccesses(InterfaceAccess[] interfaceAccesses) {
        this.interfaceAccesses = interfaceAccesses;
    }
}
