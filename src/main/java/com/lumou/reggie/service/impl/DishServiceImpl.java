package com.lumou.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lumou.reggie.dto.DishDto;
import com.lumou.reggie.entity.Dish;
import com.lumou.reggie.entity.DishFlavor;
import com.lumou.reggie.mapper.DishMapper;
import com.lumou.reggie.service.DishFlavorService;
import com.lumou.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;
    /**
     * 新增菜品同时保存对应的口味数据
     * @param dishDto
     */
    @Transactional//因为操作涉及多张表，故加入事务
    public void saveWithFlover(DishDto dishDto) {
        //保存菜品的基本信息到菜品表dish
        this.save(dishDto);

        Long dishId = dishDto.getId();//菜品id

        //菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors();

        flavors.stream().map((item) ->{//遍历赋对应的值
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());//遍历完再成为list集合

        //保存菜品口味数据到菜品口味表dish_flavor
        dishFlavorService.saveBatch(flavors);//批量保存，保存集合
    }
}
