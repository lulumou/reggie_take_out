package com.lumou.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lumou.reggie.entity.SetmealDish;
import com.lumou.reggie.mapper.SetmealDishMapper;
import com.lumou.reggie.service.SetmealDishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SetmealDishImpl extends ServiceImpl<SetmealDishMapper,SetmealDish> implements SetmealDishService {
}
