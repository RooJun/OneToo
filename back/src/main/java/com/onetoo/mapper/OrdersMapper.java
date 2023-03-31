package com.onetoo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onetoo.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
}
