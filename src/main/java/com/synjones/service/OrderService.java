package com.synjones.service;

import com.synjones.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/2 11:25
 */
public interface OrderService {

    /** 新建订单*/
    OrderDTO create(OrderDTO orderDTO);

    /** 查询单个订单 */
    OrderDTO findOne(String orderId);

    /** 查询订单列表 */
    Page<OrderDTO> findAll(String openId, Pageable pageable);

    /** 取消订单 */
    OrderDTO cancel(OrderDTO orderDTO);

    /** 完结订单 */
    OrderDTO finished(OrderDTO orderDTO);

    /** 支付订单 */
    OrderDTO paid(OrderDTO orderDTO);

    /** 卖家端查询订单*/
    Page<OrderDTO> findList(Pageable pageable);
}
