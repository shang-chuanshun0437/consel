package com.weiyi.lock.request;

public class CheckPasswordRequest extends BaseRequest
{
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
