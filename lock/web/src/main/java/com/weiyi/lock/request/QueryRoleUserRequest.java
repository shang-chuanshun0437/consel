package com.weiyi.lock.request;

public class QueryRoleUserRequest extends BaseRequest
{
    private Integer currentPage;

    private Long needPhone;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Long getNeedPhone() {
        return needPhone;
    }

    public void setNeedPhone(Long needPhone) {
        this.needPhone = needPhone;
    }
}
