package com.lumou.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lumou.reggie.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
