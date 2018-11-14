package com.weiyi.lock.dao.request;

import com.weiyi.lock.dao.entity.VerifyCode;

public class QueryVerifyCodeListReq extends VerifyCode
{
    private int currentPage;

    private String startTime;

    private String endTime;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
