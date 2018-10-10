package com.synjones.repository;

import com.synjones.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/2 11:11
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void save(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId(UUID.randomUUID().toString().replace("-",""));
        orderDetail.setOrderId("9c4527d00aaa420498b156b80cedb22b");
        orderDetail.setProductId("73d4255b041945d28d47936374681b5c");
        orderDetail.setProductName("炸酱面");
        orderDetail.setProductPrice(BigDecimal.valueOf(11.7));
        orderDetail.setProductQuantity(1);
        orderDetail.setProductIcon("http://xxx.jpg");

        OrderDetail result = repository.save(orderDetail);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOrderId() {
        List<OrderDetail> result = repository.findByOrderId("9c4527d00aaa420498b156b80cedb22b");

        Assert.assertNotEquals(0,result.size());
    }
}