package com.weiyi.lock.response;

import com.weiyi.lock.dao.entity.User;

public class QueryAllUserResponse extends BaseResponse
{
    private int count;

    private User[] users;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }
}
