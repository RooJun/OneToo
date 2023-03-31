package com.onetoo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.onetoo.common.R;
import com.onetoo.entity.Category;
import com.onetoo.entity.Company;
import com.onetoo.entity.Dish;
import com.onetoo.entity.Member;
import com.onetoo.entityPlus.DishDto;
import com.onetoo.service.CategoryService;
import com.onetoo.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("page")
    public R<Page> page(int page,int pageSize,String name){

        //构造分页构造器对象
        Page<Dish> pageInfo = new Page<>(page,pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        //条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(name != null,Dish::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Dish::getUpdateTime);

        //执行分页查询
        dishService.page(pageInfo,queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");

        List<Dish> records = pageInfo.getRecords();

        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item,dishDto);

            Long categoryId = item.getCategoryId();//分类id
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);

            if(category != null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);

    }
    @PostMapping("/save")
        public R<String> save(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());

        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getName,dishDto.getCategoryName());

        Category one = categoryService.getOne(queryWrapper);

        dishDto.setCategoryId(one.getId());

        dishService.save(dishDto);
        String key="dish_"+dishDto.getCategoryId()+"_1";
        redisTemplate.delete(key);

        return R.success("新增菜品成功");
    }
    @PostMapping("/updatedish")
    public R<String> updatedish(@RequestBody Dish dish) {

        log.info(dish.toString());

        dishService.updateById(dish);

        String key="dish_"+dish.getCategoryId()+"_1";
        redisTemplate.delete(key);
        return R.success("信息修改成功");

    }
    @DeleteMapping("/delete")
    public R<String> deletedish(Long id){
        log.info("id为：{}",id);
        Dish dish = dishService.getById(id);
        if(dishService.removeById(id)){
            String key="dish_"+dish.getCategoryId()+"_1";
            redisTemplate.delete(key);
            return R.success("删除成功");
        }

        else return R.error("删除失败");
    }
    @DeleteMapping("/deletes")
    public R<String> deletedishs(Long[] ids){
        log.info("id为：{}",ids);
        for (Long id : ids) {
            Dish dish = dishService.getById(id);
            dishService.removeById(id);
            String key="dish_"+dish.getCategoryId()+"_1";
            redisTemplate.delete(key);

        }
            return R.success("删除成功");
    }
    @PostMapping("/updatedishs")
    public R<String> updatedishs(@RequestBody Dish[] dishs) {

        for (Dish dish : dishs) {
            dishService.updateById(dish);
            String key="dish_"+dish.getCategoryId()+"_1";
            redisTemplate.delete(key);
        }


        return R.success("信息修改成功");
    }
    @GetMapping("/selectbycategoryid")
    public R<List<Dish>> Selectbycategoryid(Long id){
        List<Dish> dishes=null;
        String key="dish_" +id+"_1";

         dishes = (List<Dish>) redisTemplate.opsForValue().get(key);

         if(dishes!=null){
             return R.success(dishes);
         }

//        log.info(String.valueOf(id));
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dish::getCategoryId,id);
        queryWrapper.eq(Dish::getStatus,1);
        dishes = dishService.list(queryWrapper);

        redisTemplate.opsForValue().set(key,dishes,60, TimeUnit.MINUTES);

        return R.success(dishes);


    }


}
