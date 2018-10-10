package com.synjones.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/2 09:05
 */
@Data
public class ResultVo<T> implements Serializable {

    private static final long serialVersionUID = -6688856462782378601L;

    private Integer code;
    private String msg;
    private T data;

}
