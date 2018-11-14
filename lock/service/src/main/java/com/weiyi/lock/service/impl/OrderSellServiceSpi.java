package com.weiyi.lock.service.impl;

import com.weiyi.lock.dao.entity.OrderSell;
import com.weiyi.lock.dao.mapper.OrderSellMapper;
import com.weiyi.lock.dao.request.QueryOrderSellListReq;
import com.weiyi.lock.service.api.OrderSellService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderSellServiceSpi implements OrderSellService
{
    private Logger logger = LoggerFactory.getLogger(OrderSellServiceSpi.class);

    @Autowired
    private OrderSellMapper orderSellMapper;

    public void addOrder(OrderSell orderSell)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter addOrder insert() func,request:{}", orderSell);
        }

        orderSellMapper.addOrder(orderSell);
    }

    public List<OrderSell> queryOrderSellList(QueryOrderSellListReq queryOrderSellListReq) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter addOrder insert() func,request:{}", queryOrderSellListReq);
        }

        List<OrderSell> orderSells = orderSellMapper.queryOrderSellList(queryOrderSellListReq);

        return orderSells;
    }

    public int queryOrderSellListCount(QueryOrderSellListReq queryOrderSellListReq) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter addOrder insert() func,request:{}", queryOrderSellListReq);
        }
        return orderSellMapper.queryOrderSellListCount(queryOrderSellListReq);
    }

    public void modifyOrderSell(OrderSell orderSell) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter addOrder insert() func,request:{}", orderSell);
        }
        orderSellMapper.modifyOrderSell(orderSell);
    }

}
