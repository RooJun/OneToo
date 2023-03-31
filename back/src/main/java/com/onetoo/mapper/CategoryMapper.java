package com.onetoo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onetoo.entity.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
