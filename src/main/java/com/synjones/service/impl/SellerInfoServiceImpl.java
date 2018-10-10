package com.synjones.service.impl;

import com.synjones.dataobject.SellerInfo;
import com.synjones.enums.ResultEnum;
import com.synjones.exception.SellException;
import com.synjones.repository.SellerInfoRepository;
import com.synjones.service.SellerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/27 09:45
 */
@Service
public class SellerInfoServiceImpl implements SellerInfoService {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Override
    public SellerInfo save(SellerInfo sellerInfo) {
        return sellerInfoRepository.save(sellerInfo);
    }

    @Override
    public SellerInfo findByOpenid(String openid) {
        SellerInfo sellerInfo =  sellerInfoRepository.findByOpenid(openid);
        if(null==sellerInfo){
            throw  new SellException(ResultEnum.SELLERINFO_NOT_EXIT);
        }

        return  sellerInfo;
    }
}
