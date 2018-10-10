package com.synjones.Controller;

import com.synjones.dataobject.ProductCategory;
import com.synjones.exception.SellException;
import com.synjones.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import java.util.List;
import java.util.Map;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/19 18:02
 */
@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 查询类目列表
     * @param map
     * @return
     */
    @GetMapping("/list")
    private ModelAndView findCategoryList(Map<String,Object> map){
        List<ProductCategory> categoryList = categoryService.findAll();

        map.put("categoryList",categoryList);

        return new ModelAndView("/category/list",map);
    }

    /**
     * 修改类目跳转路径
     */
    @GetMapping("/update")
    private ModelAndView update(@RequestParam("categoryId")Integer categoryId,
                                Map<String,Object> map){

        ProductCategory category = categoryService.findOne(categoryId);
        map.put("category",category);

        return new ModelAndView("/category/index",map);
    }

    /**
     * 保存类目
     */
    @PostMapping("/save")
    private  ModelAndView save(ProductCategory productCategory,
                               Map<String ,Object> map){

        map.put("url","/sell/seller/category/list");
        try{
            categoryService.save(productCategory);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            return new ModelAndView("/common/error",map);
        }

        map.put("msg","类目保存成功");
        return new ModelAndView("/common/success",map);
    }

    /**
     * 添加类目跳转路径
     */
    @GetMapping("/create")
    private ModelAndView create(){

        return new ModelAndView("/category/index");
    }


}
