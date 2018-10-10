package com.synjones.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/2 10:49
 */
@Entity
@Data
@DynamicUpdate
public class OrderDetail {
    @Id
    private String detailId;

    /** 订单id*/
    private String orderId;

    /** 商品id */
    private String productId;

    /** 商品名称*/
    private String productName;

    /** 单价 */
    private BigDecimal productPrice;

    /** 商品数量 */
    private Integer productQuantity;

    /** 商品小图 */
    private String productIcon;

    private Date createTime;

    private Date updateTime;


}
