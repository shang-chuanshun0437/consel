package com.weiyi.lock.response;

import com.weiyi.lock.dao.entity.User;

public class ChangeAccountResponse extends BaseResponse
{
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
