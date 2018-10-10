package com.synjones.service;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;
import com.synjones.dto.OrderDTO;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/5 14:04
 */
public interface PayService {
    /**
     * 支付订单
     * @param orderDTO
     * @return
     */
    PayResponse create(OrderDTO orderDTO);

    /**
     * 异步通知
     * @param notifyData
     * @return
     */
    PayResponse notify(String notifyData);

    /**
     * 退款
     * @param orderDTO
     */
    RefundResponse refund(OrderDTO orderDTO);

}
