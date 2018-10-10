package com.synjones.service.impl;

import com.synjones.dataobject.ProductInfo;
import com.synjones.service.ProductInfoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/6/29 11:50
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {

    @Autowired
    private ProductInfoService productInfoService;

    @Test
    public void findOne() throws Exception{
        ProductInfo productInfo = productInfoService.findOne("73d4255b041945d28d47936374681b5c");
        Assert.assertNotNull(productInfo);
    }

    @Test
    public void findUpAll() throws Exception {
        List<ProductInfo> result = productInfoService.findUpAll();
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    public void findAll() throws Exception{
        PageRequest request = new PageRequest(1,1);
        Page<ProductInfo> productInfoPage = productInfoService.findAll(request);
        System.out.println(productInfoPage.getTotalElements());
    }

    @Test
    public void save() throws Exception{
        String uuid = UUID.randomUUID().toString().replace("-","");
        ProductInfo productInfo = new ProductInfo(uuid,"望京小腰",BigDecimal.valueOf(15),2,"撸串","http://xxx.jpg",0,1);
        ProductInfo result = productInfoService.save(productInfo);
        Assert.assertNotNull(result);
    }
}