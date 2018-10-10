package com.synjones.enums;

import lombok.Data;
import lombok.Getter;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/2 14:03
 */
@Getter
public enum ResultEnum {
    PRODUCT_NOT_EXIT(100,"商品不存在"),
    PRODUCT_STOCK_ERROR(101,"商品库存不正确"),

    ORDER_NOT_EXIT(102,"订单信息不存在"),
    ORDERDETAIL_NOT_EXIT(103,"订单详情信息不存在"),
    ORDER_STATUS_ERROR(104,"订单状态错误"),
    UPDATE_ORDER_STATUS_ERROR(105,"修改订单状态失败"),
    PAY_STATUS_ERROR(106,"订单支付状态错误"),
    UPDATE_PAY_STATUS_ERROR(107,"修改支付状态失败"),
    CART_NOT_EMPTY(108,"购物车不能为空"),
    CHECK_OPENID_ERROR(109,"该订单不属于当前用户"),
    BESTPAY_ORDERAMOUT_VERIFY_ERROR(110,"订单金额不一致"),

    PARAM_NOT_EXIT(111,"参数不存在"),

    WECHAT_MP_ERROR(112,"微信公众账号错误"),
    SAVE_PRODUCT_ERROR(113,"保存商品失败"),
    PRODUCT_OFF_SALE_ERROR(114,"商品下架失败"),
    PRODUCT_ON_SALE_ERROR(115,"商品上架失败"),
    PRODUCT_NOT_ENOUGH_STOCK(116,"商品库存不足"),
    PRODUCT_STATUS_ERROR(117,"商品状态不正确"),

    SELLERINFO_NOT_EXIT(118,"卖家信息不存在"),
    ;

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
