package com.onetoo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.onetoo.common.R;
import com.onetoo.entity.OrderDetail;
import com.onetoo.entity.Orders;
import com.onetoo.service.OrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/orderDetail")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/select")
    public R<List<OrderDetail>> Select(Long[] ids){


        List<OrderDetail> orderDetailList = new ArrayList<>();

        for (Long id :ids){
            LambdaQueryWrapper<OrderDetail> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(OrderDetail::getOrderId,id);
            List<OrderDetail> list = orderDetailService.list(queryWrapper);
            orderDetailList.addAll(list);
        }




        return R.success(orderDetailList);
    }
}
