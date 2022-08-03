package com.lumou.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lumou.reggie.common.R;
import com.lumou.reggie.entity.Employee;
import com.lumou.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j//日志
@RestController//相当于@Controller+@ResponseBody两个注解的结合，返回json数据不需要在方法前面加@ResponseBody注解了，
// 但使用@RestController这个注解，就不能返回jsp,html页面，视图解析器无法解析jsp,html页面
@RequestMapping("/employee")//通过它来指定控制器可以处理哪些URL请求，相当于Servlet中在web.xml中配置
//人操作枪
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    //员工登录
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){//用@RequestBody接收josn格式,
        // request作用是登录成功后，将员工id存到seeion一份，并返回登陆成功，以便随时获取登录用户--request.getseeion

        /*
        * 1.将页面提交的密码password进行md5加密处理
        * 2.根据页面提交的用户名username查询数据库
        * 3.如果没有查询到则返回登录失败结果
        * 4.密码比对，如果不一致则返回登录失败
        * 5.查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        * 6.登录成功，将员工id存入session并返回登录成功结果
        * */

        //1.将页面提交的密码password进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //2.根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();//查询方法LambdaQueryWrapper
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);//唯一约束，去查出唯一的数据//谁怎么操作数据库

        //3.如果没有查询到则返回登录失败结果
        if(emp==null){
            return R.error("登陆失败");
        }

        //4.密码比对，如果不一致则返回登录失败
        if(!emp.getPassword().equals(password)){
            return R.error("登陆密码失败");
        }

        //5.查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if(emp.getStatus()==0){
            return R.error("账号已禁用");
        }

        //6.登录成功，将员工id存入session并返回登录成功结果
        request.getSession().setAttribute("employee",emp.getId());

        return R.success(emp);
    }
}
