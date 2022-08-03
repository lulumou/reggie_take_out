package com.lumou.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lumou.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper//在编译时生成动态代理类，与数据库进行交互
public interface EmployeeMapper extends BaseMapper<Employee> {//继承常见的增删改查方法：Mybatis提供的机制就是需要开发人员在mapper.xml中提供sql语句
//相当于子弹（与数据库交互）
}
