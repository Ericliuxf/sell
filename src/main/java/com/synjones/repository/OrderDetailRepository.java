package com.synjones.repository;

import com.synjones.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/2 11:09
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {

    /** 查询全部订单*/
    List<OrderDetail> findByOrderId(String orderId);
}
