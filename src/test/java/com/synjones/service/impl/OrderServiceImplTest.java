package com.synjones.service.impl;

import com.synjones.dataobject.OrderDetail;
import com.synjones.dto.OrderDTO;
import com.synjones.service.OrderService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/2 16:15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("秋香");
        orderDTO.setBuyerPhone("18258128624");
        orderDTO.setBuyerAddress("华府");
        orderDTO.setBuyerOpenid("183521");
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("73d4255b041945d28d47936374681b5c");
        orderDetail.setProductQuantity(2);
        orderDetailList.add(orderDetail);

        orderDTO.setOrderDetailList(orderDetailList);
        OrderDTO result = orderService.create(orderDTO);

        Assert.assertNotNull(result);
    }

    @Test
    public void findOne() {
        OrderDTO orderDTO = orderService.findOne("1530523212460938926");
        Assert.assertNotNull(orderDTO);
    }

    @Test
    public void findAll() {
        Pageable pageable = new PageRequest(0,2);
        Page<OrderDTO> orderDTOPage = orderService.findAll("183521", pageable);
        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO = orderService.findOne("1530523212460938926");
        OrderDTO result = orderService.cancel(orderDTO);
        Assert.assertEquals(new Integer(2),result.getOrderStatus());

    }

    @Test
    public void finished() {
        OrderDTO orderDTO = orderService.findOne("1530523212460938926");
        OrderDTO result = orderService.finished(orderDTO);
        Assert.assertEquals(new Integer(1),result.getOrderStatus());
    }

    @Test
    public void paid() {
        OrderDTO orderDTO = orderService.findOne("1530523212460938926");
        OrderDTO result = orderService.paid(orderDTO);
        Assert.assertEquals(new Integer(1),result.getPayStatus());
    }

    @Test
    public void findList(){
        PageRequest pageRequest = new PageRequest(0,10);
        Page<OrderDTO> result = orderService.findList(pageRequest);
        Assert.assertNotEquals(0,result.getTotalElements());
    }

}