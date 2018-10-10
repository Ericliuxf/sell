package com.synjones.service;

import com.synjones.dto.OrderDTO;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/8/1 17:28
 */
public interface PublishMessageService {

    /**
     * 订单状态推送
     * @param orderDTO
     */
    public void orderStatus(OrderDTO orderDTO);

}
