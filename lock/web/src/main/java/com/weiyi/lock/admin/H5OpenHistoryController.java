package com.weiyi.lock.admin;

import com.weiyi.lock.common.Result;
import com.weiyi.lock.common.constant.Constant;
import com.weiyi.lock.common.utils.CopyProperties;
import com.weiyi.lock.common.utils.TimeUtil;
import com.weiyi.lock.dao.entity.OpenDoorHistory;
import com.weiyi.lock.dao.entity.OrderSell;
import com.weiyi.lock.dao.request.GetOpenHistoryRequest;
import com.weiyi.lock.dao.request.QueryOrderSellListReq;
import com.weiyi.lock.interceptor.SecurityAnnotation;
import com.weiyi.lock.request.ModifyOrderSellRequest;
import com.weiyi.lock.request.QueryOpenHistoryRequest;
import com.weiyi.lock.request.QueryOrderSellRequest;
import com.weiyi.lock.response.ModifyOrderSellResponse;
import com.weiyi.lock.response.QueryOpenHistoryResponse;
import com.weiyi.lock.response.QueryOrderSellResponse;
import com.weiyi.lock.service.api.OpenHistoryService;
import com.weiyi.lock.service.api.OrderSellService;
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
@RequestMapping("/open/door/history")
public class H5OpenHistoryController
{
    private Logger logger = LoggerFactory.getLogger(H5OpenHistoryController.class);

    @Autowired
    private OpenHistoryService openHistoryService;

    /*
    *根据查询条件，查询开门记录
    */
    @RequestMapping(value = "/query/list",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation(value = {"ADMIN"})
    public QueryOpenHistoryResponse queryOpenHistory(@RequestBody QueryOpenHistoryRequest request)
    {
        QueryOpenHistoryResponse response = new QueryOpenHistoryResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryOpenHistory() func ,the request:{}",request);
        }

        if (request.getCurrentPage() == null || request.getCurrentPage().intValue() <= 0)
        {
            request.setCurrentPage(1);
        }
        if (request.getPageSize() == null || request.getPageSize().intValue() <= 0)
        {
            request.setPageSize(Constant.PAGE_SIZE);
        }
        GetOpenHistoryRequest getOpenHistoryRequest = new GetOpenHistoryRequest();

        CopyProperties.copy(getOpenHistoryRequest,request);
        getOpenHistoryRequest.setUserPhone(request.getNeedUser());
        getOpenHistoryRequest.setCurrentPage((request.getCurrentPage() - 1) * request.getPageSize());

        int total = openHistoryService.queryOpenHistoryListCount(getOpenHistoryRequest);
        response.setCount(total);

        List<OpenDoorHistory> openDoorHistories = openHistoryService.queryOpenHistoryList(getOpenHistoryRequest);
        if (openDoorHistories != null && openDoorHistories.size() > 0)
        {
            response.setOpenDoorHistories(openDoorHistories.toArray(new OpenDoorHistory[openDoorHistories.size()]));
        }
        return response;
    }
}
