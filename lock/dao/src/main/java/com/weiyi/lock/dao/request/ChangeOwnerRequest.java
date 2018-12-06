package com.weiyi.lock.dao.request;


public class ChangeOwnerRequest
{
    private Long ownerPhone;

    private Long newOwnerPhone;

    public Long getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(Long ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public Long getNewOwnerPhone() {
        return newOwnerPhone;
    }

    public void setNewOwnerPhone(Long newOwnerPhone) {
        this.newOwnerPhone = newOwnerPhone;
    }
}
