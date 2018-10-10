package com.synjones.service.impl;

import com.synjones.dataobject.OrderDetail;
import com.synjones.dataobject.OrderMaster;
import com.synjones.enums.OrderStatusEnum;
import com.synjones.enums.PayStatusEnum;
import com.synjones.repository.OrderDetailRepository;
import com.synjones.repository.OrderMasterRepository;
import com.synjones.converter.OrderMaster2OrderDTOConverter;
import com.synjones.dataobject.ProductInfo;
import com.synjones.dto.CartDTO;
import com.synjones.dto.OrderDTO;
import com.synjones.enums.ResultEnum;
import com.synjones.exception.SellException;
import com.synjones.service.*;
import com.synjones.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/2 11:40
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private PayService payService;
    @Autowired
    private PublishMessageService publishMessageService;
    @Autowired
    private WebSocket webSocket;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        //1.生成订单详情表(OrderDetail)
        String orderId = KeyUtil.getUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        for(OrderDetail orderDetail : orderDTO.getOrderDetailList()){
            ProductInfo productInfo = productInfoService.findOne(orderDetail.getProductId());
            if(null==productInfo){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
            }
            orderDetail.setDetailId(KeyUtil.getUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailRepository.save(orderDetail);
            //1.1 计算订单总额
            orderAmount = orderAmount.add(orderDetail.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())));
        }

        //2.生成订单主表
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        orderDTO.setOrderAmount(orderAmount);
        BeanUtils.copyProperties(orderDTO,orderMaster,"orderStatus","payStatus");

        orderMasterRepository.save(orderMaster);

        //3.减少库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());

        productInfoService.decreaseStock(cartDTOList);

        //4.webSocket推送消息
        webSocket.sendMessage("您有新订单");

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        //1.查询订单主表
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if(null==orderMaster){
            log.info("【查询订单详情】订单信息不存在");
            throw new SellException(ResultEnum.ORDER_NOT_EXIT);
        }
        //2.查询订单详情信息
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetailList)){
            log.info("【查询订单详情】订单详情信息不存在");
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIT);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findAll(String openId, Pageable pageable) {
        //1.只查询订单主表,不显示订单详情
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(openId, pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.converter(orderMasterPage.getContent());

        return new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        //1.判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.info("【取消订单】订单状态错误");
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //2.修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster result = orderMasterRepository.save(orderMaster);
        if(null==result){
            log.info("【取消订单】修改订单状态失败");
            throw new SellException(ResultEnum.UPDATE_ORDER_STATUS_ERROR);
        }
        //3.增加库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.increaseStock(cartDTOList);

        //3.判断支付状态，退款
        if(orderDTO.getPayStatus().equals(PayStatusEnum.FINISHED.getCode())){
            payService.refund(orderDTO);
        }

        return orderDTO;
    }

    @Override
    public OrderDTO finished(OrderDTO orderDTO) {
        //1.判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.info("【完结订单】 订单状态错误");
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //2.修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster result = orderMasterRepository.save(orderMaster);
        if(null==result) {
            if (null == result) {
                log.info("【完结订单】修改订单状态失败");
                throw new SellException(ResultEnum.UPDATE_ORDER_STATUS_ERROR);
            }
        }
        //推送模板消息
        publishMessageService.orderStatus(orderDTO);

        return orderDTO;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        //1.判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.info("【支付订单】 订单状态错误");
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //2.判断支付状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.info("【支付订单】 订单支付状态错误");
            throw new SellException(ResultEnum.PAY_STATUS_ERROR);
        }

        //3.修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster result = orderMasterRepository.save(orderMaster);
        if(null==result){
            log.info("【支付订单】 修改支付状态失败");
            throw new SellException(ResultEnum.UPDATE_PAY_STATUS_ERROR);
        }
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.converter(orderMasterPage.getContent());

        return new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());

    }
}
