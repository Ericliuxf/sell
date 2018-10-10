package com.synjones.exception;

import com.synjones.enums.ResultEnum;
import lombok.Getter;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/2 14:01
 */
@Getter
public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public SellException(Integer code,String message) {
        super(message);
        this.code = code;
    }
}
