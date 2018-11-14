package com.weiyi.lock.service.api;

import com.weiyi.lock.dao.entity.VerifyCode;
import com.weiyi.lock.dao.request.QueryVerifyCodeListReq;

import java.util.List;

public interface VerifyCodeService
{
    void addCode(VerifyCode verifyCode);

    int queryVerifyCodeInfoCount(QueryVerifyCodeListReq request);

    List<VerifyCode> queryVerifyCodeInfo(QueryVerifyCodeListReq request);
}
