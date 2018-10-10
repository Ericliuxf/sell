package com.synjones.mapper;

import com.synjones.dataobject.ProductCategory;
import com.synjones.service.WebSocket;
import org.apache.ibatis.annotations.Result;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/8/7 08:50
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryMapperTest {

    @Autowired
    private  ProductCategoryMapper mapper;

    @Test
    public void insertByMap() throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("categoryName","尊贵");
        map.put("categoryType",11);
        int result = mapper.insertByMap(map);
        Assert.assertEquals(1,result);
    }


    @Test
    public void insertByObj() throws  Exception{
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("不潮不花钱");
        productCategory.setCategoryType(12);
        int result = mapper.insertByObj(productCategory);
        Assert.assertEquals(1,result);
    }

    @Test
    public void deleteByCategoryType(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryType(11);
        mapper.deleteByProductCategory(productCategory);
        int result = mapper.deleteByCategoryType(12);
        Assert.assertEquals(1,result);
    }

    @Test
    public void updateCategoryName(){
        int result = mapper.updateCategoryName("潮", 4);
        Assert.assertEquals(1,result);
    }


    @Test
    public void selectProductCategory(){
        ProductCategory productCategory = mapper.selectProductCategory(4);
        Assert.assertNotNull(productCategory);
    }


    @Test
    public void selectByXml(){
        ProductCategory productCategory = mapper.selectByXml(4);
        assertNotEquals(null,productCategory);
    }
}