package com.synjones.repository;

import com.synjones.dataobject.ProductInfo;
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
 * @Date: 2018/6/29 10:27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    private  ProductInfoRepository repository;

    @Test
    public void save(){
        String uuid = UUID.randomUUID().toString().replace("-", "");
        ProductInfo productInfo = new ProductInfo(uuid,"炸酱面",BigDecimal.valueOf(11.7),5,"老北京特产","http://XXX.jpg",0,1);
        ProductInfo result = repository.save(productInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByProductStatus() {
        List<ProductInfo> productInfos = repository.findByProductStatus(0);
        Assert.assertNotEquals(0,productInfos.size());
    }
}