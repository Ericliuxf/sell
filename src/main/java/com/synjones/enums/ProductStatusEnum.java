package com.synjones.enums;

import lombok.Getter;

/**
 * @Author: eric
 * @Description: 商品状态
 * @Date: 2018/6/29 13:48
 */
@Getter
public enum ProductStatusEnum implements CodeEnum {
    UP(0,"在架"),
    DOWN(1,"下架");

    
    private Integer code;
    private String msg;

    ProductStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}



