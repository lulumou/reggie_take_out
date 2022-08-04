package com.lumou.reggie.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lumou.reggie.entity.Employee;
import com.lumou.reggie.mapper.EmployeeMapper;
import com.lumou.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,Employee> implements EmployeeService {
    //枪装入子弹（实现操作数据库的方法，相当于怎么交互）
}
