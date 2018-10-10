package com.synjones.repository;

import com.synjones.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: eric
 * @Description: 类目
 * @Date: 2018/6/28 10:07
 */

public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {

    /** 查询全部 */
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> productCategoryList);

}
