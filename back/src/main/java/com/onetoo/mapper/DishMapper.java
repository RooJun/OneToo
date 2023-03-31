package com.onetoo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onetoo.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
