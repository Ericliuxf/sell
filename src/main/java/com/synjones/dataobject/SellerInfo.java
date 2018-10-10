package com.synjones.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/18 09:04
 */
@Entity
@Data
@DynamicUpdate
public class SellerInfo {

    @Id
    private String sellerId;

    private String username;

    private String password;

    private String openid;

    private Date createTime;

    private Date updateTime;


}





