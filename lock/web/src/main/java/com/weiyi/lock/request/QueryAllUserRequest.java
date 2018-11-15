package com.weiyi.lock.request;

public class QueryAllUserRequest extends BaseRequest
{
    private Integer currentPage;

    private String beginTime;

    private String endTime;

    private Long needPhone;

    private String userName;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getNeedPhone() {
        return needPhone;
    }

    public void setNeedPhone(Long needPhone) {
        this.needPhone = needPhone;
    }
}
