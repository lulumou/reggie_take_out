package com.lumou.reggie.common;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/*
* 全局异常处理
* */
@ControllerAdvice(annotations = {RestController.class, Controller.class})//对controller进行拦截
@ResponseBody//需要结果封装成json数据返回
@Slf4j
public class GlobalExceptionHandler {
    /*
    * 进行异常处理方法
    * */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.error(ex.getMessage());

        if(ex.getMessage().contains("Duplicate entry")){
            String[] split = ex.getMessage().split(" ");
            String mes = split[2]+"已存在";
            return R.error(mes);
        }

        return R.error("未知错误");
    }
}
