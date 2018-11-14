package com.weiyi.lock.response;

import com.weiyi.lock.dao.entity.OrderSell;

public class QueryOrderSellResponse extends BaseResponse
{
    private int count;

    private OrderSell[] orderSells;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public OrderSell[] getOrderSells() {
        return orderSells;
    }

    public void setOrderSells(OrderSell[] orderSells) {
        this.orderSells = orderSells;
    }
}
