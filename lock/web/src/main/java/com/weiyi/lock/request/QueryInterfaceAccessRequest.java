package com.weiyi.lock.request;

public class QueryInterfaceAccessRequest extends BaseRequest
{
    private String interfaceName;

    private Integer currentPage;

    private Integer status;

    private String startTime;

    private String endTime;

    //访问次数
    private Long interfaceCount;

    //平均耗时
    private Long averageTime;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Long getInterfaceCount() {
        return interfaceCount;
    }

    public void setInterfaceCount(Long interfaceCount) {
        this.interfaceCount = interfaceCount;
    }

    public Long getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(Long averageTime) {
        this.averageTime = averageTime;
    }
}
