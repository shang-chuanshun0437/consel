package com.weiyi.lock.request;

public class DeleteRoleUserRequest extends BaseRequest
{
    private Long deletePhone;

    public Long getDeletePhone() {
        return deletePhone;
    }

    public void setDeletePhone(Long deletePhone) {
        this.deletePhone = deletePhone;
    }
}
