package com.synjones.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/2 09:10
 */
@Data
public class  ProductCategoryVo implements Serializable {

    private static final long serialVersionUID = 847300647561820761L;

    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVo> productInfoVoList;
}
