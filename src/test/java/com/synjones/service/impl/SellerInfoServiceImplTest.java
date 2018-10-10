package com.synjones.service.impl;

import com.synjones.dataobject.SellerInfo;
import com.synjones.service.SellerInfoService;
import com.synjones.util.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

import static org.junit.Assert.*;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/27 09:49
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoServiceImplTest {

    @Autowired
    private SellerInfoService sellerInfoService;

    @Test
    public void save() {
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(KeyUtil.getUniqueKey());
        sellerInfo.setUsername("admin");
        sellerInfo.setPassword("888888");
        sellerInfo.setOpenid("123");
        SellerInfo result = sellerInfoService.save(sellerInfo);
        Assert.assertNotEquals(null,result);
    }


    @Test
    public void findByOpenid() {
        SellerInfo result = sellerInfoService.findByOpenid("123");
        Assert.assertEquals("123",result.getOpenid());
    }
}