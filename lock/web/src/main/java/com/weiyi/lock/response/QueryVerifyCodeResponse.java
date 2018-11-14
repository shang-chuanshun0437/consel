package com.weiyi.lock.response;

import com.weiyi.lock.dao.entity.VerifyCode;

public class QueryVerifyCodeResponse extends BaseResponse
{
    private int count;

    private VerifyCode[] verifyCodes;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public VerifyCode[] getVerifyCodes() {
        return verifyCodes;
    }

    public void setVerifyCodes(VerifyCode[] verifyCodes) {
        this.verifyCodes = verifyCodes;
    }
}
