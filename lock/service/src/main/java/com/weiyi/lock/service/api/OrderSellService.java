package com.weiyi.lock.service.api;

import com.weiyi.lock.dao.entity.OrderSell;
import com.weiyi.lock.dao.request.QueryOrderSellListReq;

import java.util.List;

public interface OrderSellService
{
    void addOrder(OrderSell orderSell);

    List<OrderSell> queryOrderSellList(QueryOrderSellListReq queryOrderSellListReq);

    int queryOrderSellListCount(QueryOrderSellListReq queryOrderSellListReq);

    void modifyOrderSell(OrderSell orderSell);
}
