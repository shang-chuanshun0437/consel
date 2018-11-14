package com.weiyi.lock.request;

public class QueryVerifyCodeRequest extends BaseRequest
{
    private Integer currentPage;
    //待查询的用户账号
    private Long needPhone;

    private String startTime;

    private String endTime;

    public Long getNeedPhone() {
        return needPhone;
    }

    public void setNeedPhone(Long needPhone) {
        this.needPhone = needPhone;
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

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}
