package com.weiyi.lock.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BaseRequest
{
    @NotNull(message = "user phone can not be null")
    private Long userPhone;

    @NotBlank(message = "user token can not be null")
    private String token;

    public Long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(Long userPhone) {
        this.userPhone = userPhone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
