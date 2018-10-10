package com.synjones.service;

import com.synjones.dto.CartDTO;
import com.synjones.dataobject.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author: eric
 * @Description: 商品
 * @Date: 2018/6/29 11:43
 */
public interface ProductInfoService {

    ProductInfo findOne(String productId);

    /** 查询所有上架商品 */
    List<ProductInfo> findUpAll();

    /** 分页查询全部商品 */
    Page<ProductInfo> findAll(Pageable pageable);

    /** 加库存 */
    void increaseStock(List<CartDTO> cartDTOList);

    /** 减库存 */
    void decreaseStock(List<CartDTO> cartDTOList);

    /** 商品上架 */
    void onSale(String productId);

    /** 商品下架 */
    void offSale(String productId);

    /** 添加商品 */
    ProductInfo save(ProductInfo productInfo);


}
