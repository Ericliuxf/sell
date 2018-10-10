package com.synjones.service.impl;

import com.synjones.repository.ProductCategoryRepository;
import com.synjones.dataobject.ProductCategory;
import com.synjones.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/6/29 09:08
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryRepository repository;

    @Override
    public ProductCategory findOne(Integer categoryId) {
        return repository.findOne(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return repository.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        ProductCategory newProductCategory = null;
        if(StringUtils.isEmpty(productCategory.getCategoryId())){
            newProductCategory = new ProductCategory();
        }else{
            newProductCategory = repository.findOne(productCategory.getCategoryId());
        }
        BeanUtils.copyProperties(productCategory,newProductCategory);


        return repository.save(productCategory);
    }
}

