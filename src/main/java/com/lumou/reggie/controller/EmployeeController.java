package com.lumou.reggie.controller;

import com.lumou.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j//日志
@RestController//相当于@Controller+@ResponseBody两个注解的结合，返回json数据不需要在方法前面加@ResponseBody注解了，
// 但使用@RestController这个注解，就不能返回jsp,html页面，视图解析器无法解析jsp,html页面
@RequestMapping("/employee")//通过它来指定控制器可以处理哪些URL请求，相当于Servlet中在web.xml中配置
//人操作枪
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
}
