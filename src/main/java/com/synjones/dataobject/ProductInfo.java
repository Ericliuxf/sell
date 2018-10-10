package com.synjones.dataobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.synjones.enums.ProductStatusEnum;
import com.synjones.util.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/6/29 09:54
 */
@Entity
@DynamicUpdate
@Data
public class ProductInfo implements Serializable {

    private static final long serialVersionUID = 9203941233901245657L;

    @Id
    private String productId;

    /** 商品名称 */
    private String productName;

    /** 单价 */
    private BigDecimal productPrice;

    /** 库存 */
    private Integer productStock;

    /** 商品描述*/
    private String productDescription;

    /** 小图 */
    private String productIcon;

    /** 商品状态 0上架，1下架*/
    private Integer productStatus = ProductStatusEnum.UP.getCode();

    /** 类目编号 */
    private Integer categoryItem;

    private Date createTime;

    private Date updateTime;

    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum(){
        return EnumUtil.getByCode(productStatus,ProductStatusEnum.class);
    }


    public ProductInfo() {
    }

    public ProductInfo(String productId, String productName, BigDecimal productPrice, Integer productStock, String productDescription, String productIcon, Integer productStatus, Integer categoryItem) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productDescription = productDescription;
        this.productIcon = productIcon;
        this.productStatus = productStatus;
        this.categoryItem = categoryItem;
    }
}

