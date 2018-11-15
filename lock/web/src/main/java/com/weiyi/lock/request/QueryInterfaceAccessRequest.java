package com.weiyi.lock.request;

public class QueryInterfaceAccessRequest extends BaseRequest
{
    private String interfaceName;

    private Long needPhone;

    private Integer currentPage;

    private Integer status;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public Long getNeedPhone() {
        return needPhone;
    }

    public void setNeedPhone(Long needPhone) {
        this.needPhone = needPhone;
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
}
