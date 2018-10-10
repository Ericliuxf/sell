package com.synjones.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.synjones.dto.OrderDTO;
import com.synjones.enums.ResultEnum;
import com.synjones.exception.SellException;
import com.synjones.service.OrderService;
import com.synjones.service.PayService;
import com.synjones.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/5 14:05
 */
@Service
@Slf4j
public class PayServiceImpl implements PayService {

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private OrderService orderService;

    /**
     * 支付订单
     * @param orderDTO
     * @return
     */
    @Override
    public PayResponse create(OrderDTO orderDTO) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName("微信点餐订单");
        log.info("【微信支付订单】发起支付，request={}",payRequest);

        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付订单】发起支付，response={}",payResponse);

        return payResponse;
    }

    /**
     * 异步通知
     * @param notifyData
     * @return
     */
    @Override
    public PayResponse notify(String notifyData) {
        //1.验证签名,验证是不是微信发起的请求。
        //2.验证订单状态
        //3.验证订单金额
        //4.验证(下单人==支付人)


        //1.异步通知
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("【微信支付订单】 异步通知， response={}",payResponse);

        //查询订单是否存在
        OrderDTO orderDTO = orderService.findOne(payResponse.getOrderId());
        if(null==orderDTO){
            throw new SellException(ResultEnum.ORDER_NOT_EXIT);
        }
        //验证订单金额，如果订单金额不一致，继续发异步通知，很危险。
        if(!orderDTO.getOrderAmount().equals(BigDecimal.valueOf(payResponse.getOrderAmount()))){
            log.info("【微信支付订单】 异步通知，订单金额不一致，orderId={}，orderDTO={}，payResponse={}",
                    payResponse.getOrderId(),orderDTO.getOrderAmount(),payResponse.getOrderAmount());
            throw  new SellException(ResultEnum.BESTPAY_ORDERAMOUT_VERIFY_ERROR);
        }
        //修改订单状态，订单状态修改成功后，返回成功，否则，会一直发异步通知，导致后台异常信息。
        orderService.paid(orderDTO);

        return payResponse;
    }

    @Override
    public RefundResponse refund(OrderDTO orderDTO) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);

        log.info("【微信退款】 request={}",JsonUtil.toJson(refundRequest));

        RefundResponse refundResponse = bestPayService.refund(refundRequest);

        log.info("【微信退款】 response={}",JsonUtil.toJson(refundResponse));
        return refundResponse;
    }
}
