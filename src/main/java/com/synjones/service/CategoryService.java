package com.synjones.service;

import com.synjones.dataobject.ProductCategory;

import java.util.List;

/**
 * @Author:eric
 * @Description:类目
 * @Date: 2018/6/29 09:04
 */
public interface CategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}
