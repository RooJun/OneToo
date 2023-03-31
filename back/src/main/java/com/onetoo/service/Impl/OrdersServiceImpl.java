package com.onetoo.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onetoo.entity.Orders;
import com.onetoo.mapper.OrdersMapper;
import com.onetoo.service.OrdersService;
import org.springframework.stereotype.Service;

@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
}
