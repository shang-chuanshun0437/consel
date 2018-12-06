package com.weiyi.lock.response;

public class ForgetPasswordResponse extends BaseResponse
{
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
