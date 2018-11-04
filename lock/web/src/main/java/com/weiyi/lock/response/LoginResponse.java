package com.weiyi.lock.response;

public class LoginResponse extends BaseResponse
{
    private String token;

    private Long userPhone;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(Long userPhone) {
        this.userPhone = userPhone;
    }
}
