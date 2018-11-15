package com.weiyi.lock.dao.entity;

import com.weiyi.lock.common.utils.TimeUtil;

public class Role
{
    private Long userPhone;

    private String UserName;

    private String userRole;

    private String createTime;

    private String updateTime;

    public Long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(Long userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getCreateTime() {
        return TimeUtil.getDateTime(createTime);
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUpdateTime() {
        return TimeUtil.getDateTime(updateTime);
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

}
