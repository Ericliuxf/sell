package com.synjones.repository;

import com.synjones.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/26 10:51
 */
public interface SellerInfoRepository extends JpaRepository<SellerInfo,String> {

    /**
     * 根据openid查询卖家信息
     * @param openid
     * @return
     */
    public SellerInfo findByOpenid(String openid);
}
