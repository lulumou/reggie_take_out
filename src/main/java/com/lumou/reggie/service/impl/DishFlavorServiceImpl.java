package com.lumou.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lumou.reggie.entity.DishFlavor;
import com.lumou.reggie.mapper.DishFlavorMapper;
import com.lumou.reggie.service.DishFlavorService;
import com.lumou.reggie.service.DishService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
