package com.lumou.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lumou.reggie.dto.SetmealDto;
import com.lumou.reggie.entity.Setmeal;

/**
 * 新增套餐，同时保存套餐和菜品的关联关系
 */
public interface SetmealService extends IService<Setmeal> {
    public void saveWithDish(SetmealDto setmealDto);
}
