package com.douban.servicebase.exceptionhandler;


import com.douban.commonutils.Result;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //统一异常类 添加规则

//    //全局异常
//    @ExceptionHandler(value = Exception.class)
//    @ResponseBody  //为了返回数据
//    public Result error(Exception e){
//        e.printStackTrace();
//        return Result.error().message("执行了全局异常处理");
//    }

//    //特定异常
//    @ExceptionHandler(value = ArithmeticException.class)
//    @ResponseBody  //为了返回数据
//    public Result error(ArithmeticException e){
//        e.printStackTrace();
//        return Result.error().message("执行了ArithmeticException异常处理");
//    }

    //自定义异常
    @ExceptionHandler(NormalException.class)
    @ResponseBody
    public Result error(NormalException e){
        e.printStackTrace();
        return Result.error().code(e.getCode()).message(e.getMessage());
    }


}

