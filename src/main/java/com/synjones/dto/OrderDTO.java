package com.synjones.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.synjones.dataobject.OrderDetail;
import com.synjones.enums.OrderStatusEnum;
import com.synjones.enums.PayStatusEnum;
import com.synjones.util.EnumUtil;
import com.synjones.util.serializer.Date2StringSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/2 11:34
 */
@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)      //返回json如果是null，则不显示
public class OrderDTO {
    private String orderId;

    /** 买家名称 */
    private String buyerName;

    /** 买家电话 */
    private String buyerPhone;

    /** 买家地址 */
    private String buyerAddress;

    /** 买家微信 openid */
    private String buyerOpenid;

    /** 订单金额*/
    private BigDecimal orderAmount;

    /** 订单状态 0新订单 1已完结 2取消 */
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /** 支付状态 0等待支付 1已支付*/
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    @JsonSerialize(using = Date2StringSerializer.class)
    private Date createTime;

    //@JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss",timezone = "GMT+8")
    @JsonSerialize(using = Date2StringSerializer.class)
    private Date updateTime;

    private List<OrderDetail> orderDetailList;

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getByCode(orderStatus,OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return EnumUtil.getByCode(payStatus,PayStatusEnum.class);
    }
}
