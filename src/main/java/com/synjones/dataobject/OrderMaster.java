package com.synjones.dataobject;

import com.synjones.enums.PayStatusEnum;
import com.synjones.enums.OrderStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/2 10:30
 */
@Entity
@Data
@DynamicUpdate
public class OrderMaster {

    @Id
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

    private Date createTime;

    private Date updateTime;
}
