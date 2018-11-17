package com.weiyi.lock.request;

public class AddRoleUserRequest extends BaseRequest
{
    //待添加的用户账号
    private Long addPhone;

    //待添加的用户账号名字
    private String addName;

    //要添加的权限，如果是多个权限，有“；”分开
    private String userRole;

    public Long getAddPhone() {
        return addPhone;
    }

    public void setAddPhone(Long addPhone) {
        this.addPhone = addPhone;
    }

    public String getAddName() {
        return addName;
    }

    public void setAddName(String addName) {
        this.addName = addName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
