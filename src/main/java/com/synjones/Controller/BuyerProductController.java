package com.synjones.Controller;

import com.synjones.dataobject.ProductCategory;
import com.synjones.dataobject.ProductInfo;
import com.synjones.service.CategoryService;
import com.synjones.service.ProductInfoService;
import com.synjones.util.ResultVoUtil;
import com.synjones.vo.ProductCategoryVo;
import com.synjones.vo.ProductInfoVo;
import com.synjones.vo.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/6/29 17:15
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "/list")
    @Cacheable(cacheNames = "product",key = "123",unless = "#result.getCode()!=0")
    public ResultVo list(){
        //1.查询全部上架商品
        List<ProductInfo> productInfoList = productInfoService.findUpAll();
        //2.查询全部类目编号
        List<Integer> categoryTypeList = productInfoList.stream().map(e -> e.getCategoryItem()).collect(Collectors.toList());
        //3.查询类目
        List<ProductCategory> categories = categoryService.findByCategoryTypeIn(categoryTypeList);

        List<ProductCategoryVo> productCategoryVoList = new ArrayList<>();
        //遍历商品类目
        for(ProductCategory category : categories){
            ProductCategoryVo productCategoryVo = new ProductCategoryVo();
            productCategoryVo.setCategoryName(category.getCategoryName());
            productCategoryVo.setCategoryType(category.getCategoryType());
            List<ProductInfoVo> productInfoVoList = new ArrayList<>();
            //遍历商品信息
            for(ProductInfo productInfo: productInfoList){
                if(productInfo.getCategoryItem().equals(category.getCategoryType())){
                    ProductInfoVo productInfoVo = new ProductInfoVo();
                    BeanUtils.copyProperties(productInfo,productInfoVo);
                    productInfoVoList.add(productInfoVo);
                }
            }
            productCategoryVo.setProductInfoVoList(productInfoVoList);
            productCategoryVoList.add(productCategoryVo);
        }
        return ResultVoUtil.success(productCategoryVoList);
    }
}
