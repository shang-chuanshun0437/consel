package com.weiyi.lock.dao.entity;

import com.weiyi.lock.common.utils.TimeUtil;

public class VerifyCode
{
    private int id;

    private Long userPhone;

    private String content;

    private String createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(Long userPhone) {
        this.userPhone = userPhone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return TimeUtil.getDateTime(createTime);
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
