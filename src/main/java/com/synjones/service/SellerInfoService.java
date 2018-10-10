package com.synjones.service;

import com.synjones.dataobject.SellerInfo;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/27 09:43
 */
public interface SellerInfoService {

    /**
     * 保存卖家信息
     */
    SellerInfo save(SellerInfo sellerInfo);

    /**
     * 根据openid查询卖家信息
     */
    SellerInfo findByOpenid(String openid);
}
