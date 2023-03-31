package com.onetoo.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单
 */
@Data
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    //商品数量
    private String number;

    //订单状态 1商家准备中，2待派送，3已派送，4已完成，5已取消
    private Integer status;


    //下单用户id
    private Long userId;



    //下单时间
    private LocalDateTime orderTime;





    //支付方式 1微信，2支付宝
    private Integer payMethod;


    //实收金额
    private BigDecimal amount;

    private String address;
    //备注
    private String remark;

    //用户名
    private String username;

    //手机号
    private String phone;

    private String companyName;


}
