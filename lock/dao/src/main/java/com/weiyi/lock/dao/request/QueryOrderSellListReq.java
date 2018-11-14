package com.weiyi.lock.dao.request;

import com.weiyi.lock.dao.entity.OrderSell;

public class QueryOrderSellListReq extends OrderSell
{
    private int currentPage;

    private String beginTime;

    private String endTime;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
