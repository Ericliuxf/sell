package com.synjones.service.impl;

import com.synjones.dto.CartDTO;
import com.synjones.enums.ResultEnum;
import com.synjones.exception.SellException;
import com.synjones.dataobject.ProductInfo;
import com.synjones.enums.ProductStatusEnum;
import com.synjones.repository.ProductInfoRepository;
import com.synjones.service.ProductInfoService;
import com.synjones.util.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/6/29 11:48
 */
@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoRepository repository;


    @Override
    @Cacheable(cacheNames = "sellerproduct" ,key = "123")
    public ProductInfo findOne(String productId){
        return repository.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @CachePut(cacheNames = "sellerproduct", key = "123")
    public ProductInfo save(ProductInfo productInfo) {
        ProductInfo newProductInfo = null;
        if(StringUtils.isEmpty(productInfo.getProductId())){
            newProductInfo = new ProductInfo();
            productInfo.setProductId(KeyUtil.getUniqueKey());

        }else{
            newProductInfo = repository.findOne(productInfo.getProductId());
            if(null==newProductInfo){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
            }
        }

        BeanUtils.copyProperties(productInfo,newProductInfo);

        ProductInfo result = repository.save(newProductInfo);
        if(null==result){
            throw new SellException(ResultEnum.SAVE_PRODUCT_ERROR);
        }
        return result;
    }

    /**
     * 增加库存
     * @param cartDTOList
     */
    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO : cartDTOList){
            ProductInfo productInfo = repository.findOne(cartDTO.getProductId());

            if(null==productInfo){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
            }

            Integer result = productInfo.getProductStock()+cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    /**
     * 减少库存
     * @param cartDTOList
     */
    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO : cartDTOList){
            ProductInfo productInfo = repository.findOne(cartDTO.getProductId());
            //查询商品不存在
            if(null==productInfo){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
            }
            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            //库存不正确
            if(result<0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    /**
     * 商品上架
     * @param productId
     */
    @Override
    public void onSale(String productId) {
        //1.查询商品
        ProductInfo productInfo = repository.findOne(productId);
        if(null==productInfo){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
        }
        if(productInfo.getProductStatus()==ProductStatusEnum.UP.getCode()){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
       //2.更改商品状态
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());

        ProductInfo result = repository.save(productInfo);

        if(null==result){
            throw new SellException(ResultEnum.PRODUCT_ON_SALE_ERROR);
        }

    }

    /**
     * 商品下架
     * @param productId
     */
    @Override
    public void offSale(String productId) {
        ProductInfo productInfo = repository.findOne(productId);
        if(null==productInfo){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
        }
        if(productInfo.getProductStatus()==ProductStatusEnum.DOWN.getCode()){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());

        ProductInfo result = repository.save(productInfo);
        if(null==result){
            throw new SellException(ResultEnum.PRODUCT_OFF_SALE_ERROR);
        }

    }

}
