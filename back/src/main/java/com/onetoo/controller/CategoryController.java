package com.onetoo.controller;

import com.onetoo.common.R;
import com.onetoo.entity.Category;
import com.onetoo.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/displaycategory")
    public R<List> displayCategory(){
        List<Category> list = categoryService.list();
        log.info(String.valueOf(list));
        return R.success(list);


    }
    @PostMapping("/save")
    public R<String> save(@RequestBody Category category){


      categoryService.save(category);

        return R.success("新增菜品成功");
    }
    @DeleteMapping("/delete")
    public R<String> deleteCategory(Long id){
        if(categoryService.removeById(id))
            return R.success("删除成功");
        else return R.error("删除失败");
    }
}
