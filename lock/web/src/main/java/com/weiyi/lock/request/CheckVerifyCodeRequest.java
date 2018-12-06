package com.weiyi.lock.request;

public class CheckVerifyCodeRequest
{
    private Long userPhone;

    //验证码
    private int verificationCode;

    public Long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(Long userPhone) {
        this.userPhone = userPhone;
    }

    public int getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(int verificationCode) {
        this.verificationCode = verificationCode;
    }
}
