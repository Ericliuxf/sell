package com.synjones.enums;

import lombok.Getter;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/2 10:46
 */
@Getter
public enum PayStatusEnum implements CodeEnum {
    WAIT(0,"等待支付"),
    FINISHED(1,"已支付")
    ;

    private Integer code;
    private String msg;

    PayStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
