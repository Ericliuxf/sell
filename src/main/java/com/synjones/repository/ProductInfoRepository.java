package com.synjones.repository;

import com.synjones.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: eric
 * @Description: 商品
 * @Date: 2018/6/29 10:23
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {

    /**
     * 根据商品上下架状态 查询商品
     * @param productStatus
     * @return
     */
    public List<ProductInfo> findByProductStatus(Integer productStatus);

}
