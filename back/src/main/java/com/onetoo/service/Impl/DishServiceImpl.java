package com.onetoo.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onetoo.entity.Dish;
import com.onetoo.mapper.DishMapper;
import com.onetoo.service.DishService;
import org.springframework.stereotype.Service;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
}
