package com.synjones.service;

import com.synjones.dto.OrderDTO;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/4 11:29
 */
public interface BuyerOrderService {

    OrderDTO findOrderOne(String openId, String orderId);

    void cancel(String openId,String OrderId);
}
