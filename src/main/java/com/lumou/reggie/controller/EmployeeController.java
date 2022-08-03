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
    @PostMapping("/login")//响应请求    
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

    /*
    * 员工退出
    * */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        //1.清理Session中保存的当前登录员工的id
        request.getSession().removeAttribute("employee");
        //cookies基本原理
        //当一个浏览器访问某web服务器时，web服务器会调用HttpServletResponse的addCookie()方法，
        // 在响应头中添加一个名叫Set-Cookie的响应字段用于将Cookie返回给浏览器，
        // 当浏览器第二次访问该web服务器时会自动的将该cookie回传给服务器，来实现用户状态跟踪。

//        session基本原理
//        当用户发送一个请求到服务器端时，服务器会先检查请求中是否含有sessionid(存在cookie中或者在url中)，
//        》》如果不存在sessionid(说明是第一次请求)，就会为该请求用户创建一个session对象，并将该session对象的sessionid（放到响应头的set-cookie中，格式set-cookie:sessionid,下次再请求时cookie中就会有一个name为jsessionid的cookie，value就是sessionid）响应给客户端。
//        》》如果存在sessionid，就会在服务器端查找是否有该sessionid对应的session，如果有就使用，没有就创建一个。

      
        return R.success("退出成功");
    }

}
