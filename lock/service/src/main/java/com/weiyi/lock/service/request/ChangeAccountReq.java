package com.weiyi.lock.service.request;

public class ChangeAccountReq {

    private Long userPhone;

    private String password;

    private Long newPhone;

    private String newPassword;

    private Integer verificationCode;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getNewPhone() {
        return newPhone;
    }

    public void setNewPhone(Long newPhone) {
        this.newPhone = newPhone;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public Integer getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(Integer verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(Long userPhone) {
        this.userPhone = userPhone;
    }
}
