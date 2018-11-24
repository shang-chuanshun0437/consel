package com.weiyi.lock.dao.mapper;

import com.weiyi.lock.dao.entity.OrderSell;
import com.weiyi.lock.dao.request.QueryOrderSellListReq;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderSellMapper
{
    void addOrder(OrderSell orderSell);

    List<OrderSell> queryOrderSellList(QueryOrderSellListReq queryDeviceInListReq);

    int queryOrderSellListCount(QueryOrderSellListReq queryDeviceInListReq);

    List<OrderSell> queryReplaceOrderList(QueryOrderSellListReq queryDeviceInListReq);

    int queryReplaceOrderListCount(QueryOrderSellListReq queryDeviceInListReq);

    void modifyOrderSell(OrderSell orderSell);
}
