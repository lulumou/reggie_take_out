package com.lumou.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lumou.reggie.common.R;
import com.lumou.reggie.dto.DishDto;
import com.lumou.reggie.entity.Category;
import com.lumou.reggie.entity.Dish;
import com.lumou.reggie.service.CategoryService;
import com.lumou.reggie.service.DishFlavorService;
import com.lumou.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜品管理
 */
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增菜品
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());
        dishService.saveWithFlover(dishDto);
        return R.success("新增菜品成功！");
    }

    /**
     * 菜品信息分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        //构造分页构造器对象
        Page<Dish> pageInfo =new Page<>(page,pageSize);//有对应的值，没有需要的属性
        Page<DishDto> dishDtoPage =new Page<>(page,pageSize);//有需要的属性但没有值



        //条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(name!=null,Dish::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Dish::getUpdateTime);

        //执行分页查询
        dishService.page(pageInfo,queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");//并不需要全部拷贝，除records以外的全部属性
        //原本records的集合是页面展现的列表数据（并没有所需的分类名称categoryName的数据，所以需要加进去）

        List<Dish> records = pageInfo.getRecords();

        List<DishDto> list = records.stream().map((item)->{
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item,dishDto);//拷贝普通属性

            Long categoryId =item.getCategoryId();//拿到每个菜品对应的分类id
            //目的，拿着分类id去查我们的分类表得到分类名称
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);//分类对象
            String categoryName = category.getName();
            //给dto赋值
            dishDto.setCategoryName(categoryName);

            return dishDto;
        }).collect(Collectors.toList());//收集起来做成集合


        dishDtoPage.setRecords(list);


        return R.success(dishDtoPage);
    }
}
