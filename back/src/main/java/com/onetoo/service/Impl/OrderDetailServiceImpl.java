package com.onetoo.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onetoo.entity.OrderDetail;
import com.onetoo.mapper.OrderDetailMapper;
import com.onetoo.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
