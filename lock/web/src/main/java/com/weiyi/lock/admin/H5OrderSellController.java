package com.weiyi.lock.admin;

import com.weiyi.lock.common.Result;
import com.weiyi.lock.common.constant.Constant;
import com.weiyi.lock.common.utils.CopyProperties;
import com.weiyi.lock.common.utils.TimeUtil;
import com.weiyi.lock.dao.entity.OrderSell;
import com.weiyi.lock.dao.request.QueryOrderSellListReq;
import com.weiyi.lock.interceptor.SecurityAnnotation;
import com.weiyi.lock.request.*;
import com.weiyi.lock.response.*;
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
public class H5OrderSellController
{
    private Logger logger = LoggerFactory.getLogger(H5OrderSellController.class);

    @Autowired
    private OrderSellService orderSellService;

    /*
    *根据查询条件，查询订单列表
    */
    @RequestMapping(value = "/query/orderSell/list",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation(value = {"ADMIN"})
    public QueryOrderSellResponse queryOrderSell(@RequestBody QueryOrderSellRequest request)
    {
        QueryOrderSellResponse response = new QueryOrderSellResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryOrderSell() func ,the request:{}",request);
        }

        if(request.getCurrentPage() == null || request.getCurrentPage().intValue() <= 0)
        {
            request.setCurrentPage(1);
        }
        request.setCurrentPage((request.getCurrentPage() - 1) * Constant.PAGE_SIZE);

        QueryOrderSellListReq queryOrderSellListReq = new QueryOrderSellListReq();
        CopyProperties.copy(queryOrderSellListReq,request);

        int total = orderSellService.queryOrderSellListCount(queryOrderSellListReq);

        response.setCount(total);

        if (total > 0)
        {
            List<OrderSell> orderSells = orderSellService.queryOrderSellList(queryOrderSellListReq);
            response.setOrderSells(orderSells.toArray(new OrderSell[orderSells.size()]));
        }
        return response;
    }

    /*
     *更新订单
     */
    @RequestMapping(value = "/modify/orderSell",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation(value = {"ADMIN"})
    public ModifyOrderSellResponse modifyOrderSell(@RequestBody ModifyOrderSellRequest request)
    {
        ModifyOrderSellResponse response = new ModifyOrderSellResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter modifyOrderSell() func ,the request:{}",request);
        }
        OrderSell orderSell = new OrderSell();

        CopyProperties.copy(orderSell,request);
        orderSell.setUpdateTime(TimeUtil.getCurrentTime());

        orderSellService.modifyOrderSell(orderSell);
        return response;
    }

    /*
     *添加订单
     */
    @RequestMapping(value = "/add/orderSell",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation(value = {"ADMIN"})
    public AddOrderSellResponse addOrderSell(@RequestBody AddOrderSellRequest request)
    {
        AddOrderSellResponse response = new AddOrderSellResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter addOrderSell() func ,the request:{}",request);
        }
        OrderSell orderSell = new OrderSell();

        CopyProperties.copy(orderSell,request);
        orderSell.setCreateTime(TimeUtil.getCurrentTime());
        orderSell.setUpdateTime(orderSell.getCreateTime());

        orderSellService.addOrder(orderSell);
        return response;
    }
}
