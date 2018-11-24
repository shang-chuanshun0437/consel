package com.weiyi.lock.admin;

import com.weiyi.lock.common.Result;
import com.weiyi.lock.common.constant.Constant;
import com.weiyi.lock.common.utils.CopyProperties;
import com.weiyi.lock.common.utils.TimeUtil;
import com.weiyi.lock.dao.entity.OrderSell;
import com.weiyi.lock.dao.request.QueryOrderSellListReq;
import com.weiyi.lock.interceptor.SecurityAnnotation;
import com.weiyi.lock.request.ModifyOrderSellRequest;
import com.weiyi.lock.request.QueryOrderSellRequest;
import com.weiyi.lock.response.ModifyOrderSellResponse;
import com.weiyi.lock.response.QueryOrderSellResponse;
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
@RequestMapping("/order")
public class H5ReplaceOrderController
{
    private Logger logger = LoggerFactory.getLogger(H5ReplaceOrderController.class);

    @Autowired
    private OrderSellService orderSellService;

    /*
    *根据查询条件，查询退/换货订单列表
    */
    @RequestMapping(value = "/query/replace/list",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public QueryOrderSellResponse queryReplaceOrder(@RequestBody QueryOrderSellRequest request)
    {
        QueryOrderSellResponse response = new QueryOrderSellResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryReplaceOrder() func ,the request:{}",request);
        }

        if(request.getCurrentPage() == null || request.getCurrentPage().intValue() <= 0)
        {
            request.setCurrentPage(1);
        }
        request.setCurrentPage((request.getCurrentPage() - 1) * Constant.PAGE_SIZE);

        QueryOrderSellListReq queryOrderSellListReq = new QueryOrderSellListReq();
        CopyProperties.copy(queryOrderSellListReq,request);

        int total = orderSellService.queryReplaceOrderListCount(queryOrderSellListReq);

        response.setCount(total);

        if (total > 0)
        {
            List<OrderSell> orderSells = orderSellService.queryReplaceOrderList(queryOrderSellListReq);
            response.setOrderSells(orderSells.toArray(new OrderSell[orderSells.size()]));
        }
        return response;
    }

}
