package com.weiyi.lock.request;

public class QueryOpenHistoryRequest extends BaseRequest
{
    //该用户下的开门记录
    private Long queryUser;

    private Long deviceNum;

    private Integer pageIndex;

    private String startTime;

    private String endTime;

    private String version;

    public Long getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(Long deviceNum) {
        this.deviceNum = deviceNum;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getQueryUser() {
        return queryUser;
    }

    public void setQueryUser(Long queryUser) {
        this.queryUser = queryUser;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }
}
