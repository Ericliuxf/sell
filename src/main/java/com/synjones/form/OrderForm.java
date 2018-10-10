package com.synjones.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/3 12:33
 */
@Data
public class OrderForm {

    @NotEmpty(message = "名字不能为空")
    private String name;

    @NotEmpty(message = "手机号不能为空")
    private String phone;

    @NotEmpty(message = "地址不能为空")
    private String address;

    @NotEmpty(message = "openId不能为空")
    private String openId;

    @NotEmpty(message = "购物车不能为空")  //productId, productQuantity
    private String items;


}
