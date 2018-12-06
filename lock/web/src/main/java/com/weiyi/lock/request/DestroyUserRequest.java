package com.weiyi.lock.request;

public class DestroyUserRequest extends BaseRequest
{
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
