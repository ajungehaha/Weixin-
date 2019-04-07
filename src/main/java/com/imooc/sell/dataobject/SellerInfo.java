package com.imooc.sell.dataobject;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.naming.Name;
import javax.persistence.*;
import java.util.UUID;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/20 15:15
 * @Version 1.0
 */
@Data
@Entity
@Table(name = "seller_info")
public class SellerInfo {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    /*卖家id*/
    private String sellerId;

    @Column(name = "username")
    /*卖家名称*/
    private String userName;
    @Column(name = "password")
    /*卖家密码*/
    private String password;
    @Column(name = "openid")
    /*卖家openid*/
    private String openid;

}
