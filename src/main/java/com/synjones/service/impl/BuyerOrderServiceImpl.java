package com.synjones.service.impl;

import com.synjones.dto.OrderDTO;
import com.synjones.enums.ResultEnum;
import com.synjones.exception.SellException;
import com.synjones.service.BuyerOrderService;
import com.synjones.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/4 11:32
 */
@Service
@Slf4j
public class BuyerOrderServiceImpl implements BuyerOrderService {
    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO findOrderOne(String openId, String orderId) {
        return checkOpenId(openId, orderId);

    }

    @Override
    public void cancel(String openId, String OrderId) {
        OrderDTO orderDTO = checkOpenId(openId, OrderId);
        if(null==orderDTO){
            log.info("【取消订单】订单不存在");
            throw new SellException(ResultEnum.ORDER_NOT_EXIT);
        }

        orderService.cancel(orderDTO);
    }

    public OrderDTO checkOpenId(String openId,String orderId){
        OrderDTO orderDTO = orderService.findOne(orderId);
        if(null==orderDTO){
            return null;
        }

        if(!orderDTO.getBuyerOpenid().equals(openId)){
            log.info("【验证openId】openId不一致，openId={},orderDTO={}",orderId,orderDTO);
            throw new SellException(ResultEnum.CHECK_OPENID_ERROR);
        }

        return orderDTO;
    }

}
