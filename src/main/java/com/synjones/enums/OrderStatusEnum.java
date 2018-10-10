package com.synjones.enums;

import lombok.Data;
import lombok.Getter;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/2 10:43
 */
@Getter
public enum OrderStatusEnum implements CodeEnum{

    NEW(0,"新订单"),
    FINISHED(1,"完结"),
    CANCEL(2,"取消")
    ;

    private Integer code;
    private String msg;

    OrderStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}