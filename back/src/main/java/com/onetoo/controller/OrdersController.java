package com.onetoo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.onetoo.common.BaseContext;
import com.onetoo.common.R;
import com.onetoo.entity.Member;
import com.onetoo.entity.OrderDetail;
import com.onetoo.entity.Orders;
import com.onetoo.entity.ShoppingCart;
import com.onetoo.service.OrderDetailService;
import com.onetoo.service.OrdersService;
import com.onetoo.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping("/add")
    public R<String> Add(@RequestBody Orders order) {
        log.info(String.valueOf(order));
        order.setStatus(1);
        Long currentId = BaseContext.getCurrentId();
        order.setUserId(currentId);
        order.setOrderTime(LocalDateTime.now());
        log.info(String.valueOf(order));

        ordersService.save(order);

//        log.info(String.valueOf(order));
//        将购物车的数据保存进detail

        Long orderId = order.getId();

        List<ShoppingCart> shoppingCarts = shoppingCartService.list();

        for (ShoppingCart cart : shoppingCarts) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setName(cart.getName());
            orderDetail.setImage(cart.getImage());
            orderDetail.setDishId(cart.getDishId());
            orderDetail.setNumber(cart.getNumber());
            orderDetail.setAmount(cart.getPrice());
            orderDetailService.save(orderDetail);
        }

//清空购物车
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, order.getUserId());
        shoppingCartService.remove(queryWrapper);


        return R.success("支付成功");
    }
    @GetMapping("/select")
    public R<List<Orders>> Select(){
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Orders::getOrderTime);

        List<Orders> ordersList = ordersService.list(queryWrapper);

        return R.success(ordersList);
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        if (name == null)
            log.info("分页查询！");
        else
            log.info("按名字要求条件分页查询");


//        构造分页查询器
        Page pageInfo = new Page(page, pageSize);
//        构造条件构造器
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
//        按照name的值添加条件
        queryWrapper.like(StringUtils.isNotEmpty(name), Orders::getUsername, name);
//        添加排序条件
        queryWrapper.orderByDesc(Orders::getOrderTime);
//      这一步将结果已经返回到了pageInfo中
        ordersService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);

    }
    @PostMapping("/updateOrder")
    public R<String> UpdateOrder(@RequestBody Orders orders) {

        log.info(orders.toString());

        ordersService.updateById(orders);
        return R.success("信息修改成功");

    }
}
