package com.weiyi.lock.request;

public class QueryOpenHistoryRequest extends BaseRequest
{
    //该用户下的开门记录
    private Long needUser;

    private Long deviceNum;

    private String deviceName;

    private Integer currentPage;

    private String startTime;

    private String endTime;

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

    public Long getNeedUser() {
        return needUser;
    }

    public void setNeedUser(Long needUser) {
        this.needUser = needUser;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}
