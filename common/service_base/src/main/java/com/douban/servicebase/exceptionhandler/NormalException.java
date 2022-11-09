package com.douban.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor //生成有参构造
@NoArgsConstructor  //生成无参构造
public class NormalException extends Exception{
    //异常信息
    private String message;
    //状态码
    private Integer code;



}
