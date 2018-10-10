package com.synjones.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/2 09:15
 */
@Data
public class ProductInfoVo implements Serializable {

    private static final long serialVersionUID = 6138043763157889016L;

    @JsonProperty("id")
    private String productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("price")
    private BigDecimal productPrice;

    @JsonProperty("description")
    private String productDescription;

    @JsonProperty("icon")
    private String productIcon;
}
