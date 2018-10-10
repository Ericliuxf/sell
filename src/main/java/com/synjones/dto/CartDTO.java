package com.synjones.dto;

import lombok.Data;

/**
 * @Author: eric
 * @Description: 购物车
 * @Date: 2018/7/2 15:48
 */
@Data
public class CartDTO {

    /** 商品id */
    private String productId;

    /** 商品数量 */
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
