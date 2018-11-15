package com.weiyi.lock.admin;

import com.weiyi.lock.common.Result;
import com.weiyi.lock.common.constant.Constant;
import com.weiyi.lock.common.utils.CopyProperties;
import com.weiyi.lock.dao.entity.VerifyCode;
import com.weiyi.lock.dao.request.QueryVerifyCodeListReq;
import com.weiyi.lock.interceptor.SecurityAnnotation;
import com.weiyi.lock.request.QueryVerifyCodeRequest;
import com.weiyi.lock.response.QueryVerifyCodeResponse;
import com.weiyi.lock.service.api.VerifyCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/verifyCode")
public class H5VerifyCodeController
{
    private Logger logger = LoggerFactory.getLogger(H5VerifyCodeController.class);

    @Autowired
    private VerifyCodeService verifyCodeService;

    @RequestMapping(value = "/query/list",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public QueryVerifyCodeResponse queryCode(@RequestBody QueryVerifyCodeRequest request)
    {
        QueryVerifyCodeResponse response = new QueryVerifyCodeResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter addDevice() func ,the request:{}",request);
        }

        if (request.getCurrentPage() == null || request.getCurrentPage().intValue() <= 0)
        {
            request.setCurrentPage(1);
        }

        //查询数量
        QueryVerifyCodeListReq queryVerifyCodeListReq = new QueryVerifyCodeListReq();
        CopyProperties.copy(queryVerifyCodeListReq,request);
        queryVerifyCodeListReq.setUserPhone(request.getNeedPhone());
        queryVerifyCodeListReq.setCurrentPage((request.getCurrentPage() - 1) * Constant.PAGE_SIZE);
        int total = verifyCodeService.queryVerifyCodeInfoCount(queryVerifyCodeListReq);

        response.setCount(total);

        if (total <= 0)
        {
            return response;
        }

        //查询详情
        List<VerifyCode> verifyCodes = verifyCodeService.queryVerifyCodeInfo(queryVerifyCodeListReq);
        if (verifyCodes != null && verifyCodes.size() > 0)
        {
            response.setVerifyCodes(verifyCodes.toArray(new VerifyCode[verifyCodes.size()]));
        }
        return response;
    }
}
