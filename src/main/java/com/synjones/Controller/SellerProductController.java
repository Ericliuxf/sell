package com.synjones.Controller;

import com.synjones.dataobject.ProductCategory;
import com.synjones.dataobject.ProductInfo;
import com.synjones.exception.SellException;
import com.synjones.service.CategoryService;
import com.synjones.service.ProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/18 09:09
 */
@Controller
@RequestMapping("/seller/product")
@Slf4j
public class SellerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private CategoryService categoryService;
    /**
     * 查询卖家商品列表
     */
    @GetMapping("/list")
    public ModelAndView findProductList(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                         @RequestParam(value = "size",defaultValue = "10")Integer size,
                                        Map<String,Object> map){

        PageRequest pageRequest = new PageRequest(page-1,size);
        Page<ProductInfo> productInfoPage = productInfoService.findAll(pageRequest);
        List<ProductCategory> productCategoryList = categoryService.findAll();
        map.put("productInfoPage",productInfoPage);
        map.put("productCategoryList",productCategoryList);
        map.put("currentPage",page);
        map.put("size",size);
        return new ModelAndView("/product/list",map);
    }

    /**
     * 修改商品跳转路径
     */
    @GetMapping("/update")
    public ModelAndView modify(@RequestParam("productId")String productId,
                               @RequestParam("page")Integer page,
                               @RequestParam("size") Integer size,
                               Map<String,Object> map){
        ProductInfo productInfo = productInfoService.findOne(productId);
        List<ProductCategory> productCategoryList = categoryService.findAll();

        map.put("productInfo",productInfo);
        map.put("productCategoryList",productCategoryList);
        map.put("page",page);
        map.put("size",size);

        return new ModelAndView("/product/index",map);
    }

    /**
     * 保存商品
     */
    @PostMapping("/save")
    @CacheEvict(cacheNames = "product" ,key = "123")
    public ModelAndView save(@RequestParam(value = "page",defaultValue = "1")Integer page,
                             @RequestParam(value = "size",defaultValue = "10") Integer size,
                             ProductInfo productInfo,
                             Map<String,Object> map){
        map.put("url","/sell/seller/product/list?page="+page+"&size="+size);

        try {
            productInfoService.save(productInfo);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            return  new ModelAndView("/common/error",map);
        }

        map.put("msg","保存商品成功");

        return new ModelAndView("/common/success",map);
    }

    /**
     * 添加商品跳转路径
     */
    @RequestMapping("/create")
    public ModelAndView create (Map<String,Object> map){
        List<ProductCategory> productCategoryList = categoryService.findAll();
        map.put("productCategoryList",productCategoryList);
        ProductInfo productInfo = new ProductInfo();
        productInfo.setCategoryItem(productCategoryList.get(0).getCategoryType());

        map.put("productInfo",productInfo);
        return new ModelAndView("/product/index",map);

    }

    /**
     * 商品上架
     */
    @RequestMapping("/on_sale")
    private ModelAndView onSale(@RequestParam("productId")String productId,
                                @RequestParam("page") Integer page,
                                @RequestParam("size") Integer size,
                                Map<String,Object> map){
        map.put("url","/sell/seller/product/list?page="+page+"&size="+size);

        try{
            productInfoService.onSale(productId);
        }catch (SellException e){
            map.put("msg",e.getMessage());

            return new ModelAndView("/common/error",map);
        }
        map.put("msg","商品上架成功");

        return new ModelAndView("/common/success",map);

    }

    /**
     * 商品下架
     */
    @GetMapping("/off_sale")
    private ModelAndView offSale(@RequestParam("productId") String productId,
                                 @RequestParam("page") Integer page,
                                 @RequestParam("size") Integer size,
                                 Map<String,Object> map){
        map.put("url","/sell/seller/product/list?page="+page+"&size="+size);

        try{
            productInfoService.offSale(productId);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            return new ModelAndView("/common/error",map);
        }
        map.put("msg","商品下架成功");

        return new ModelAndView("/common/success",map);

    }

}
