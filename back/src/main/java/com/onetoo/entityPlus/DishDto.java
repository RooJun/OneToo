package com.onetoo.entityPlus;


import com.onetoo.entity.Dish;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    //菜品对应的口味数据

    private Long categoryId;

    private String categoryName;

    private Integer copies;
}
