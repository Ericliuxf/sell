package com.synjones.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Author: eric
 * @Description: 类目 实体类
 * @Date: 2018/6/28 09:58
 */
@Entity
@DynamicUpdate  //自动更新
@Data           //自动加载set，get方法，toString
public class ProductCategory {
    @Id
    @GeneratedValue    //主键生成策略，(strategy.GenerationType.Auto) IDENTITY ,table,sequence
    private Integer categoryId;

    /** 类目名称 */
    private String categoryName;

    /** 类目编号 */
    private Integer categoryType;

    /**  创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    public ProductCategory() {
    }

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
