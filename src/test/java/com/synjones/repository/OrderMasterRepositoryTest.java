package com.synjones.repository;

import com.synjones.dataobject.OrderMaster;
import com.synjones.util.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/2 10:57
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Test
    public void save(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId(KeyUtil.getUniqueKey());
        orderMaster.setBuyerName("李文祥");
        orderMaster.setBuyerPhone("15713840152");
        orderMaster.setBuyerAddress("安徽太湖");
        orderMaster.setBuyerOpenid("123456");
        orderMaster.setOrderAmount(BigDecimal.valueOf(232.25));
        OrderMaster result = orderMasterRepository.save(orderMaster);
        Assert.assertNotNull(result);

    }

    @Test
    public void findByBuyerOpenid() {

        PageRequest request = new PageRequest(0, 1);
        Page<OrderMaster> orderMasters = orderMasterRepository.findByBuyerOpenid("123456",request);
        Assert.assertNotEquals(0,orderMasters.getTotalElements());
    }
}